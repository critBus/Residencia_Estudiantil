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
import entities.Tipoevalbecado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class TipoevalbecadoJpaController implements Serializable {

    public TipoevalbecadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoevalbecado tipoevalbecado) throws PreexistingEntityException, Exception {
        if (tipoevalbecado.getEvalbecadoList() == null) {
            tipoevalbecado.setEvalbecadoList(new ArrayList<Evalbecado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evalbecado> attachedEvalbecadoList = new ArrayList<Evalbecado>();
            for (Evalbecado evalbecadoListEvalbecadoToAttach : tipoevalbecado.getEvalbecadoList()) {
                evalbecadoListEvalbecadoToAttach = em.getReference(evalbecadoListEvalbecadoToAttach.getClass(), evalbecadoListEvalbecadoToAttach.getEvalbecadoPK());
                attachedEvalbecadoList.add(evalbecadoListEvalbecadoToAttach);
            }
            tipoevalbecado.setEvalbecadoList(attachedEvalbecadoList);
            em.persist(tipoevalbecado);
            for (Evalbecado evalbecadoListEvalbecado : tipoevalbecado.getEvalbecadoList()) {
                Tipoevalbecado oldTipoevalbecadoidOfEvalbecadoListEvalbecado = evalbecadoListEvalbecado.getTipoevalbecadoid();
                evalbecadoListEvalbecado.setTipoevalbecadoid(tipoevalbecado);
                evalbecadoListEvalbecado = em.merge(evalbecadoListEvalbecado);
                if (oldTipoevalbecadoidOfEvalbecadoListEvalbecado != null) {
                    oldTipoevalbecadoidOfEvalbecadoListEvalbecado.getEvalbecadoList().remove(evalbecadoListEvalbecado);
                    oldTipoevalbecadoidOfEvalbecadoListEvalbecado = em.merge(oldTipoevalbecadoidOfEvalbecadoListEvalbecado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoevalbecado(tipoevalbecado.getId()) != null) {
                throw new PreexistingEntityException("Tipoevalbecado " + tipoevalbecado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoevalbecado tipoevalbecado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoevalbecado persistentTipoevalbecado = em.find(Tipoevalbecado.class, tipoevalbecado.getId());
            List<Evalbecado> evalbecadoListOld = persistentTipoevalbecado.getEvalbecadoList();
            List<Evalbecado> evalbecadoListNew = tipoevalbecado.getEvalbecadoList();
            List<String> illegalOrphanMessages = null;
            for (Evalbecado evalbecadoListOldEvalbecado : evalbecadoListOld) {
                if (!evalbecadoListNew.contains(evalbecadoListOldEvalbecado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalbecado " + evalbecadoListOldEvalbecado + " since its tipoevalbecadoid field is not nullable.");
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
            tipoevalbecado.setEvalbecadoList(evalbecadoListNew);
            tipoevalbecado = em.merge(tipoevalbecado);
            for (Evalbecado evalbecadoListNewEvalbecado : evalbecadoListNew) {
                if (!evalbecadoListOld.contains(evalbecadoListNewEvalbecado)) {
                    Tipoevalbecado oldTipoevalbecadoidOfEvalbecadoListNewEvalbecado = evalbecadoListNewEvalbecado.getTipoevalbecadoid();
                    evalbecadoListNewEvalbecado.setTipoevalbecadoid(tipoevalbecado);
                    evalbecadoListNewEvalbecado = em.merge(evalbecadoListNewEvalbecado);
                    if (oldTipoevalbecadoidOfEvalbecadoListNewEvalbecado != null && !oldTipoevalbecadoidOfEvalbecadoListNewEvalbecado.equals(tipoevalbecado)) {
                        oldTipoevalbecadoidOfEvalbecadoListNewEvalbecado.getEvalbecadoList().remove(evalbecadoListNewEvalbecado);
                        oldTipoevalbecadoidOfEvalbecadoListNewEvalbecado = em.merge(oldTipoevalbecadoidOfEvalbecadoListNewEvalbecado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoevalbecado.getId();
                if (findTipoevalbecado(id) == null) {
                    throw new NonexistentEntityException("The tipoevalbecado with id " + id + " no longer exists.");
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
            Tipoevalbecado tipoevalbecado;
            try {
                tipoevalbecado = em.getReference(Tipoevalbecado.class, id);
                tipoevalbecado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoevalbecado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evalbecado> evalbecadoListOrphanCheck = tipoevalbecado.getEvalbecadoList();
            for (Evalbecado evalbecadoListOrphanCheckEvalbecado : evalbecadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoevalbecado (" + tipoevalbecado + ") cannot be destroyed since the Evalbecado " + evalbecadoListOrphanCheckEvalbecado + " in its evalbecadoList field has a non-nullable tipoevalbecadoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoevalbecado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoevalbecado> findTipoevalbecadoEntities() {
        return findTipoevalbecadoEntities(true, -1, -1);
    }

    public List<Tipoevalbecado> findTipoevalbecadoEntities(int maxResults, int firstResult) {
        return findTipoevalbecadoEntities(false, maxResults, firstResult);
    }

    private List<Tipoevalbecado> findTipoevalbecadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoevalbecado.class));
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

    public Tipoevalbecado findTipoevalbecado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoevalbecado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoevalbecadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoevalbecado> rt = cq.from(Tipoevalbecado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
