/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Evalbecado;
import entities.Rangos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class RangosJpaController implements Serializable {

    public RangosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rangos rangos) throws PreexistingEntityException, Exception {
        if (rangos.getEvalbecadoList() == null) {
            rangos.setEvalbecadoList(new ArrayList<Evalbecado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evalbecado> attachedEvalbecadoList = new ArrayList<Evalbecado>();
            for (Evalbecado evalbecadoListEvalbecadoToAttach : rangos.getEvalbecadoList()) {
                evalbecadoListEvalbecadoToAttach = em.getReference(evalbecadoListEvalbecadoToAttach.getClass(), evalbecadoListEvalbecadoToAttach.getEvalbecadoPK());
                attachedEvalbecadoList.add(evalbecadoListEvalbecadoToAttach);
            }
            rangos.setEvalbecadoList(attachedEvalbecadoList);
            em.persist(rangos);
            for (Evalbecado evalbecadoListEvalbecado : rangos.getEvalbecadoList()) {
                Rangos oldCualitativaOfEvalbecadoListEvalbecado = evalbecadoListEvalbecado.getCualitativa();
                evalbecadoListEvalbecado.setCualitativa(rangos);
                evalbecadoListEvalbecado = em.merge(evalbecadoListEvalbecado);
                if (oldCualitativaOfEvalbecadoListEvalbecado != null) {
                    oldCualitativaOfEvalbecadoListEvalbecado.getEvalbecadoList().remove(evalbecadoListEvalbecado);
                    oldCualitativaOfEvalbecadoListEvalbecado = em.merge(oldCualitativaOfEvalbecadoListEvalbecado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRangos(rangos.getNombre()) != null) {
                throw new PreexistingEntityException("Rangos " + rangos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rangos rangos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rangos persistentRangos = em.find(Rangos.class, rangos.getNombre());
            List<Evalbecado> evalbecadoListOld = persistentRangos.getEvalbecadoList();
            List<Evalbecado> evalbecadoListNew = rangos.getEvalbecadoList();
            List<String> illegalOrphanMessages = null;
            for (Evalbecado evalbecadoListOldEvalbecado : evalbecadoListOld) {
                if (!evalbecadoListNew.contains(evalbecadoListOldEvalbecado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalbecado " + evalbecadoListOldEvalbecado + " since its cualitativa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evalbecado> attachedEvalbecadoListNew = new ArrayList<Evalbecado>();
            for (Evalbecado evalbecadoListNewEvalbecadoToAttach : evalbecadoListNew) {
                evalbecadoListNewEvalbecadoToAttach = em.getReference(evalbecadoListNewEvalbecadoToAttach.getClass(), evalbecadoListNewEvalbecadoToAttach.getEvalbecadoPK());
                attachedEvalbecadoListNew.add(evalbecadoListNewEvalbecadoToAttach);
            }
            evalbecadoListNew = attachedEvalbecadoListNew;
            rangos.setEvalbecadoList(evalbecadoListNew);
            rangos = em.merge(rangos);
            for (Evalbecado evalbecadoListNewEvalbecado : evalbecadoListNew) {
                if (!evalbecadoListOld.contains(evalbecadoListNewEvalbecado)) {
                    Rangos oldCualitativaOfEvalbecadoListNewEvalbecado = evalbecadoListNewEvalbecado.getCualitativa();
                    evalbecadoListNewEvalbecado.setCualitativa(rangos);
                    evalbecadoListNewEvalbecado = em.merge(evalbecadoListNewEvalbecado);
                    if (oldCualitativaOfEvalbecadoListNewEvalbecado != null && !oldCualitativaOfEvalbecadoListNewEvalbecado.equals(rangos)) {
                        oldCualitativaOfEvalbecadoListNewEvalbecado.getEvalbecadoList().remove(evalbecadoListNewEvalbecado);
                        oldCualitativaOfEvalbecadoListNewEvalbecado = em.merge(oldCualitativaOfEvalbecadoListNewEvalbecado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = rangos.getNombre();
                if (findRangos(id) == null) {
                    throw new NonexistentEntityException("The rangos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rangos rangos;
            try {
                rangos = em.getReference(Rangos.class, id);
                rangos.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rangos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evalbecado> evalbecadoListOrphanCheck = rangos.getEvalbecadoList();
            for (Evalbecado evalbecadoListOrphanCheckEvalbecado : evalbecadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rangos (" + rangos + ") cannot be destroyed since the Evalbecado " + evalbecadoListOrphanCheckEvalbecado + " in its evalbecadoList field has a non-nullable cualitativa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rangos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rangos> findRangosEntities() {
        return findRangosEntities(true, -1, -1);
    }

    public List<Rangos> findRangosEntities(int maxResults, int firstResult) {
        return findRangosEntities(false, maxResults, firstResult);
    }

    private List<Rangos> findRangosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rangos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rangos findRangos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rangos.class, id);
        } finally {
            em.close();
        }
    }

    public int getRangosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rangos> rt = cq.from(Rangos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
