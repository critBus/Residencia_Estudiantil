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
import entities.AspectEvalcuarto;
import entities.AspectEvalcuartoEvalcuarto;
import entities.AspectEvalcuartoEvalcuartoPK;
import entities.Evalcuarto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AspectEvalcuartoEvalcuartoJpaController implements Serializable {

    public AspectEvalcuartoEvalcuartoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuarto) throws PreexistingEntityException, Exception {
        if (aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK() == null) {
            aspectEvalcuartoEvalcuarto.setAspectEvalcuartoEvalcuartoPK(new AspectEvalcuartoEvalcuartoPK());
        }
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setAspevalcuartoid(aspectEvalcuartoEvalcuarto.getAspectEvalcuarto().getId());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setFecha(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getFecha());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setEdificioid(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getEdificioid());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setCuartoid(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getCuartoid());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setPisoid(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getPisoid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuarto aspectEvalcuarto = aspectEvalcuartoEvalcuarto.getAspectEvalcuarto();
            if (aspectEvalcuarto != null) {
                aspectEvalcuarto = em.getReference(aspectEvalcuarto.getClass(), aspectEvalcuarto.getId());
                aspectEvalcuartoEvalcuarto.setAspectEvalcuarto(aspectEvalcuarto);
            }
            Evalcuarto evalcuarto = aspectEvalcuartoEvalcuarto.getEvalcuarto();
            if (evalcuarto != null) {
                evalcuarto = em.getReference(evalcuarto.getClass(), evalcuarto.getEvalcuartoPK());
                aspectEvalcuartoEvalcuarto.setEvalcuarto(evalcuarto);
            }
            em.persist(aspectEvalcuartoEvalcuarto);
            if (aspectEvalcuarto != null) {
                aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList().add(aspectEvalcuartoEvalcuarto);
                aspectEvalcuarto = em.merge(aspectEvalcuarto);
            }
            if (evalcuarto != null) {
                evalcuarto.getAspectEvalcuartoEvalcuartoList().add(aspectEvalcuartoEvalcuarto);
                evalcuarto = em.merge(evalcuarto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAspectEvalcuartoEvalcuarto(aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK()) != null) {
                throw new PreexistingEntityException("AspectEvalcuartoEvalcuarto " + aspectEvalcuartoEvalcuarto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuarto) throws NonexistentEntityException, Exception {
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setAspevalcuartoid(aspectEvalcuartoEvalcuarto.getAspectEvalcuarto().getId());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setFecha(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getFecha());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setEdificioid(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getEdificioid());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setCuartoid(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getCuartoid());
        aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK().setPisoid(aspectEvalcuartoEvalcuarto.getEvalcuarto().getEvalcuartoPK().getPisoid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuartoEvalcuarto persistentAspectEvalcuartoEvalcuarto = em.find(AspectEvalcuartoEvalcuarto.class, aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK());
            AspectEvalcuarto aspectEvalcuartoOld = persistentAspectEvalcuartoEvalcuarto.getAspectEvalcuarto();
            AspectEvalcuarto aspectEvalcuartoNew = aspectEvalcuartoEvalcuarto.getAspectEvalcuarto();
            Evalcuarto evalcuartoOld = persistentAspectEvalcuartoEvalcuarto.getEvalcuarto();
            Evalcuarto evalcuartoNew = aspectEvalcuartoEvalcuarto.getEvalcuarto();
            if (aspectEvalcuartoNew != null) {
                aspectEvalcuartoNew = em.getReference(aspectEvalcuartoNew.getClass(), aspectEvalcuartoNew.getId());
                aspectEvalcuartoEvalcuarto.setAspectEvalcuarto(aspectEvalcuartoNew);
            }
            if (evalcuartoNew != null) {
                evalcuartoNew = em.getReference(evalcuartoNew.getClass(), evalcuartoNew.getEvalcuartoPK());
                aspectEvalcuartoEvalcuarto.setEvalcuarto(evalcuartoNew);
            }
            aspectEvalcuartoEvalcuarto = em.merge(aspectEvalcuartoEvalcuarto);
            if (aspectEvalcuartoOld != null && !aspectEvalcuartoOld.equals(aspectEvalcuartoNew)) {
                aspectEvalcuartoOld.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuarto);
                aspectEvalcuartoOld = em.merge(aspectEvalcuartoOld);
            }
            if (aspectEvalcuartoNew != null && !aspectEvalcuartoNew.equals(aspectEvalcuartoOld)) {
                aspectEvalcuartoNew.getAspectEvalcuartoEvalcuartoList().add(aspectEvalcuartoEvalcuarto);
                aspectEvalcuartoNew = em.merge(aspectEvalcuartoNew);
            }
            if (evalcuartoOld != null && !evalcuartoOld.equals(evalcuartoNew)) {
                evalcuartoOld.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuarto);
                evalcuartoOld = em.merge(evalcuartoOld);
            }
            if (evalcuartoNew != null && !evalcuartoNew.equals(evalcuartoOld)) {
                evalcuartoNew.getAspectEvalcuartoEvalcuartoList().add(aspectEvalcuartoEvalcuarto);
                evalcuartoNew = em.merge(evalcuartoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AspectEvalcuartoEvalcuartoPK id = aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK();
                if (findAspectEvalcuartoEvalcuarto(id) == null) {
                    throw new NonexistentEntityException("The aspectEvalcuartoEvalcuarto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AspectEvalcuartoEvalcuartoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuarto;
            try {
                aspectEvalcuartoEvalcuarto = em.getReference(AspectEvalcuartoEvalcuarto.class, id);
                aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aspectEvalcuartoEvalcuarto with id " + id + " no longer exists.", enfe);
            }
            AspectEvalcuarto aspectEvalcuarto = aspectEvalcuartoEvalcuarto.getAspectEvalcuarto();
            if (aspectEvalcuarto != null) {
                aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuarto);
                aspectEvalcuarto = em.merge(aspectEvalcuarto);
            }
            Evalcuarto evalcuarto = aspectEvalcuartoEvalcuarto.getEvalcuarto();
            if (evalcuarto != null) {
                evalcuarto.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuarto);
                evalcuarto = em.merge(evalcuarto);
            }
            em.remove(aspectEvalcuartoEvalcuarto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AspectEvalcuartoEvalcuarto> findAspectEvalcuartoEvalcuartoEntities() {
        return findAspectEvalcuartoEvalcuartoEntities(true, -1, -1);
    }

    public List<AspectEvalcuartoEvalcuarto> findAspectEvalcuartoEvalcuartoEntities(int maxResults, int firstResult) {
        return findAspectEvalcuartoEvalcuartoEntities(false, maxResults, firstResult);
    }

    private List<AspectEvalcuartoEvalcuarto> findAspectEvalcuartoEvalcuartoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AspectEvalcuartoEvalcuarto.class));
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

    public AspectEvalcuartoEvalcuarto findAspectEvalcuartoEvalcuarto(AspectEvalcuartoEvalcuartoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AspectEvalcuartoEvalcuarto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAspectEvalcuartoEvalcuartoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AspectEvalcuartoEvalcuarto> rt = cq.from(AspectEvalcuartoEvalcuarto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
