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
import entities.AspectEvalcuartel;
import entities.Evalcuarteleria;
import entities.EvalcuarteleriaAspectevalcuartel;
import entities.EvalcuarteleriaAspectevalcuartelPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class EvalcuarteleriaAspectevalcuartelJpaController implements Serializable {

    public EvalcuarteleriaAspectevalcuartelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartel) throws PreexistingEntityException, Exception {
        if (evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK() == null) {
            evalcuarteleriaAspectevalcuartel.setEvalcuarteleriaAspectevalcuartelPK(new EvalcuarteleriaAspectevalcuartelPK());
        }
        evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK().setFecha(evalcuarteleriaAspectevalcuartel.getEvalcuarteleria().getEvalcuarteleriaPK().getFecha());
        evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK().setBecadoci(evalcuarteleriaAspectevalcuartel.getEvalcuarteleria().getEvalcuarteleriaPK().getBecadoci());
        evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK().setAspectEvalcuartelid(evalcuarteleriaAspectevalcuartel.getAspectEvalcuartel().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuartel aspectEvalcuartel = evalcuarteleriaAspectevalcuartel.getAspectEvalcuartel();
            if (aspectEvalcuartel != null) {
                aspectEvalcuartel = em.getReference(aspectEvalcuartel.getClass(), aspectEvalcuartel.getId());
                evalcuarteleriaAspectevalcuartel.setAspectEvalcuartel(aspectEvalcuartel);
            }
            Evalcuarteleria evalcuarteleria = evalcuarteleriaAspectevalcuartel.getEvalcuarteleria();
            if (evalcuarteleria != null) {
                evalcuarteleria = em.getReference(evalcuarteleria.getClass(), evalcuarteleria.getEvalcuarteleriaPK());
                evalcuarteleriaAspectevalcuartel.setEvalcuarteleria(evalcuarteleria);
            }
            em.persist(evalcuarteleriaAspectevalcuartel);
            if (aspectEvalcuartel != null) {
                aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList().add(evalcuarteleriaAspectevalcuartel);
                aspectEvalcuartel = em.merge(aspectEvalcuartel);
            }
            if (evalcuarteleria != null) {
                evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList().add(evalcuarteleriaAspectevalcuartel);
                evalcuarteleria = em.merge(evalcuarteleria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvalcuarteleriaAspectevalcuartel(evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK()) != null) {
                throw new PreexistingEntityException("EvalcuarteleriaAspectevalcuartel " + evalcuarteleriaAspectevalcuartel + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartel) throws NonexistentEntityException, Exception {
        evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK().setFecha(evalcuarteleriaAspectevalcuartel.getEvalcuarteleria().getEvalcuarteleriaPK().getFecha());
        evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK().setBecadoci(evalcuarteleriaAspectevalcuartel.getEvalcuarteleria().getEvalcuarteleriaPK().getBecadoci());
        evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK().setAspectEvalcuartelid(evalcuarteleriaAspectevalcuartel.getAspectEvalcuartel().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvalcuarteleriaAspectevalcuartel persistentEvalcuarteleriaAspectevalcuartel = em.find(EvalcuarteleriaAspectevalcuartel.class, evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK());
            AspectEvalcuartel aspectEvalcuartelOld = persistentEvalcuarteleriaAspectevalcuartel.getAspectEvalcuartel();
            AspectEvalcuartel aspectEvalcuartelNew = evalcuarteleriaAspectevalcuartel.getAspectEvalcuartel();
            Evalcuarteleria evalcuarteleriaOld = persistentEvalcuarteleriaAspectevalcuartel.getEvalcuarteleria();
            Evalcuarteleria evalcuarteleriaNew = evalcuarteleriaAspectevalcuartel.getEvalcuarteleria();
            if (aspectEvalcuartelNew != null) {
                aspectEvalcuartelNew = em.getReference(aspectEvalcuartelNew.getClass(), aspectEvalcuartelNew.getId());
                evalcuarteleriaAspectevalcuartel.setAspectEvalcuartel(aspectEvalcuartelNew);
            }
            if (evalcuarteleriaNew != null) {
                evalcuarteleriaNew = em.getReference(evalcuarteleriaNew.getClass(), evalcuarteleriaNew.getEvalcuarteleriaPK());
                evalcuarteleriaAspectevalcuartel.setEvalcuarteleria(evalcuarteleriaNew);
            }
            evalcuarteleriaAspectevalcuartel = em.merge(evalcuarteleriaAspectevalcuartel);
            if (aspectEvalcuartelOld != null && !aspectEvalcuartelOld.equals(aspectEvalcuartelNew)) {
                aspectEvalcuartelOld.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartel);
                aspectEvalcuartelOld = em.merge(aspectEvalcuartelOld);
            }
            if (aspectEvalcuartelNew != null && !aspectEvalcuartelNew.equals(aspectEvalcuartelOld)) {
                aspectEvalcuartelNew.getEvalcuarteleriaAspectevalcuartelList().add(evalcuarteleriaAspectevalcuartel);
                aspectEvalcuartelNew = em.merge(aspectEvalcuartelNew);
            }
            if (evalcuarteleriaOld != null && !evalcuarteleriaOld.equals(evalcuarteleriaNew)) {
                evalcuarteleriaOld.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartel);
                evalcuarteleriaOld = em.merge(evalcuarteleriaOld);
            }
            if (evalcuarteleriaNew != null && !evalcuarteleriaNew.equals(evalcuarteleriaOld)) {
                evalcuarteleriaNew.getEvalcuarteleriaAspectevalcuartelList().add(evalcuarteleriaAspectevalcuartel);
                evalcuarteleriaNew = em.merge(evalcuarteleriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EvalcuarteleriaAspectevalcuartelPK id = evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK();
                if (findEvalcuarteleriaAspectevalcuartel(id) == null) {
                    throw new NonexistentEntityException("The evalcuarteleriaAspectevalcuartel with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EvalcuarteleriaAspectevalcuartelPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartel;
            try {
                evalcuarteleriaAspectevalcuartel = em.getReference(EvalcuarteleriaAspectevalcuartel.class, id);
                evalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evalcuarteleriaAspectevalcuartel with id " + id + " no longer exists.", enfe);
            }
            AspectEvalcuartel aspectEvalcuartel = evalcuarteleriaAspectevalcuartel.getAspectEvalcuartel();
            if (aspectEvalcuartel != null) {
                aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartel);
                aspectEvalcuartel = em.merge(aspectEvalcuartel);
            }
            Evalcuarteleria evalcuarteleria = evalcuarteleriaAspectevalcuartel.getEvalcuarteleria();
            if (evalcuarteleria != null) {
                evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartel);
                evalcuarteleria = em.merge(evalcuarteleria);
            }
            em.remove(evalcuarteleriaAspectevalcuartel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvalcuarteleriaAspectevalcuartel> findEvalcuarteleriaAspectevalcuartelEntities() {
        return findEvalcuarteleriaAspectevalcuartelEntities(true, -1, -1);
    }

    public List<EvalcuarteleriaAspectevalcuartel> findEvalcuarteleriaAspectevalcuartelEntities(int maxResults, int firstResult) {
        return findEvalcuarteleriaAspectevalcuartelEntities(false, maxResults, firstResult);
    }

    private List<EvalcuarteleriaAspectevalcuartel> findEvalcuarteleriaAspectevalcuartelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvalcuarteleriaAspectevalcuartel.class));
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

    public EvalcuarteleriaAspectevalcuartel findEvalcuarteleriaAspectevalcuartel(EvalcuarteleriaAspectevalcuartelPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvalcuarteleriaAspectevalcuartel.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvalcuarteleriaAspectevalcuartelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvalcuarteleriaAspectevalcuartel> rt = cq.from(EvalcuarteleriaAspectevalcuartel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
