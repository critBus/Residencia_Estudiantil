/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Becado;
import entities.Evalbecado;
import entities.EvalbecadoPK;
import entities.Rangos;
import entities.Tipoevalbecado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class EvalbecadoJpaController implements Serializable {

    public EvalbecadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evalbecado evalbecado) throws PreexistingEntityException, Exception {
        if (evalbecado.getEvalbecadoPK() == null) {
            evalbecado.setEvalbecadoPK(new EvalbecadoPK());
        }
        evalbecado.getEvalbecadoPK().setBecadoci(evalbecado.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado becado = evalbecado.getBecado();
            if (becado != null) {
                becado = em.getReference(becado.getClass(), becado.getCi());
                evalbecado.setBecado(becado);
            }
            Rangos cualitativa = evalbecado.getCualitativa();
            if (cualitativa != null) {
                cualitativa = em.getReference(cualitativa.getClass(), cualitativa.getNombre());
                evalbecado.setCualitativa(cualitativa);
            }
            Tipoevalbecado tipoevalbecadoid = evalbecado.getTipoevalbecadoid();
            if (tipoevalbecadoid != null) {
                tipoevalbecadoid = em.getReference(tipoevalbecadoid.getClass(), tipoevalbecadoid.getId());
                evalbecado.setTipoevalbecadoid(tipoevalbecadoid);
            }
            em.persist(evalbecado);
            if (becado != null) {
                becado.getEvalbecadoList().add(evalbecado);
                becado = em.merge(becado);
            }
            if (cualitativa != null) {
                cualitativa.getEvalbecadoList().add(evalbecado);
                cualitativa = em.merge(cualitativa);
            }
            if (tipoevalbecadoid != null) {
                tipoevalbecadoid.getEvalbecadoList().add(evalbecado);
                tipoevalbecadoid = em.merge(tipoevalbecadoid);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvalbecado(evalbecado.getEvalbecadoPK()) != null) {
                throw new PreexistingEntityException("Evalbecado " + evalbecado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evalbecado evalbecado) throws NonexistentEntityException, Exception {
        evalbecado.getEvalbecadoPK().setBecadoci(evalbecado.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalbecado persistentEvalbecado = em.find(Evalbecado.class, evalbecado.getEvalbecadoPK());
            Becado becadoOld = persistentEvalbecado.getBecado();
            Becado becadoNew = evalbecado.getBecado();
            Rangos cualitativaOld = persistentEvalbecado.getCualitativa();
            Rangos cualitativaNew = evalbecado.getCualitativa();
            Tipoevalbecado tipoevalbecadoidOld = persistentEvalbecado.getTipoevalbecadoid();
            Tipoevalbecado tipoevalbecadoidNew = evalbecado.getTipoevalbecadoid();
            if (becadoNew != null) {
                becadoNew = em.getReference(becadoNew.getClass(), becadoNew.getCi());
                evalbecado.setBecado(becadoNew);
            }
            if (cualitativaNew != null) {
                cualitativaNew = em.getReference(cualitativaNew.getClass(), cualitativaNew.getNombre());
                evalbecado.setCualitativa(cualitativaNew);
            }
            if (tipoevalbecadoidNew != null) {
                tipoevalbecadoidNew = em.getReference(tipoevalbecadoidNew.getClass(), tipoevalbecadoidNew.getId());
                evalbecado.setTipoevalbecadoid(tipoevalbecadoidNew);
            }
            evalbecado = em.merge(evalbecado);
            if (becadoOld != null && !becadoOld.equals(becadoNew)) {
                becadoOld.getEvalbecadoList().remove(evalbecado);
                becadoOld = em.merge(becadoOld);
            }
            if (becadoNew != null && !becadoNew.equals(becadoOld)) {
                becadoNew.getEvalbecadoList().add(evalbecado);
                becadoNew = em.merge(becadoNew);
            }
            if (cualitativaOld != null && !cualitativaOld.equals(cualitativaNew)) {
                cualitativaOld.getEvalbecadoList().remove(evalbecado);
                cualitativaOld = em.merge(cualitativaOld);
            }
            if (cualitativaNew != null && !cualitativaNew.equals(cualitativaOld)) {
                cualitativaNew.getEvalbecadoList().add(evalbecado);
                cualitativaNew = em.merge(cualitativaNew);
            }
            if (tipoevalbecadoidOld != null && !tipoevalbecadoidOld.equals(tipoevalbecadoidNew)) {
                tipoevalbecadoidOld.getEvalbecadoList().remove(evalbecado);
                tipoevalbecadoidOld = em.merge(tipoevalbecadoidOld);
            }
            if (tipoevalbecadoidNew != null && !tipoevalbecadoidNew.equals(tipoevalbecadoidOld)) {
                tipoevalbecadoidNew.getEvalbecadoList().add(evalbecado);
                tipoevalbecadoidNew = em.merge(tipoevalbecadoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EvalbecadoPK id = evalbecado.getEvalbecadoPK();
                if (findEvalbecado(id) == null) {
                    throw new NonexistentEntityException("The evalbecado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EvalbecadoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalbecado evalbecado;
            try {
                evalbecado = em.getReference(Evalbecado.class, id);
                evalbecado.getEvalbecadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evalbecado with id " + id + " no longer exists.", enfe);
            }
            Becado becado = evalbecado.getBecado();
            if (becado != null) {
                becado.getEvalbecadoList().remove(evalbecado);
                becado = em.merge(becado);
            }
            Rangos cualitativa = evalbecado.getCualitativa();
            if (cualitativa != null) {
                cualitativa.getEvalbecadoList().remove(evalbecado);
                cualitativa = em.merge(cualitativa);
            }
            Tipoevalbecado tipoevalbecadoid = evalbecado.getTipoevalbecadoid();
            if (tipoevalbecadoid != null) {
                tipoevalbecadoid.getEvalbecadoList().remove(evalbecado);
                tipoevalbecadoid = em.merge(tipoevalbecadoid);
            }
            em.remove(evalbecado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evalbecado> findEvalbecadoEntities() {
        return findEvalbecadoEntities(true, -1, -1);
    }

    public List<Evalbecado> findEvalbecadoEntities(int maxResults, int firstResult) {
        return findEvalbecadoEntities(false, maxResults, firstResult);
    }

    private List<Evalbecado> findEvalbecadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evalbecado.class));
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

    public Evalbecado findEvalbecado(EvalbecadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evalbecado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvalbecadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evalbecado> rt = cq.from(Evalbecado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
