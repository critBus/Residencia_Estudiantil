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
import entities.Evalguardia;
import entities.EvalguardiaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class EvalguardiaJpaController implements Serializable {

    public EvalguardiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evalguardia evalguardia) throws PreexistingEntityException, Exception {
        if (evalguardia.getEvalguardiaPK() == null) {
            evalguardia.setEvalguardiaPK(new EvalguardiaPK());
        }
        evalguardia.getEvalguardiaPK().setBecadoci(evalguardia.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado becado = evalguardia.getBecado();
            if (becado != null) {
                becado = em.getReference(becado.getClass(), becado.getCi());
                evalguardia.setBecado(becado);
            }
            em.persist(evalguardia);
            if (becado != null) {
                becado.getEvalguardiaList().add(evalguardia);
                becado = em.merge(becado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvalguardia(evalguardia.getEvalguardiaPK()) != null) {
                throw new PreexistingEntityException("Evalguardia " + evalguardia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evalguardia evalguardia) throws NonexistentEntityException, Exception {
        evalguardia.getEvalguardiaPK().setBecadoci(evalguardia.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalguardia persistentEvalguardia = em.find(Evalguardia.class, evalguardia.getEvalguardiaPK());
            Becado becadoOld = persistentEvalguardia.getBecado();
            Becado becadoNew = evalguardia.getBecado();
            if (becadoNew != null) {
                becadoNew = em.getReference(becadoNew.getClass(), becadoNew.getCi());
                evalguardia.setBecado(becadoNew);
            }
            evalguardia = em.merge(evalguardia);
            if (becadoOld != null && !becadoOld.equals(becadoNew)) {
                becadoOld.getEvalguardiaList().remove(evalguardia);
                becadoOld = em.merge(becadoOld);
            }
            if (becadoNew != null && !becadoNew.equals(becadoOld)) {
                becadoNew.getEvalguardiaList().add(evalguardia);
                becadoNew = em.merge(becadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EvalguardiaPK id = evalguardia.getEvalguardiaPK();
                if (findEvalguardia(id) == null) {
                    throw new NonexistentEntityException("The evalguardia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EvalguardiaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalguardia evalguardia;
            try {
                evalguardia = em.getReference(Evalguardia.class, id);
                evalguardia.getEvalguardiaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evalguardia with id " + id + " no longer exists.", enfe);
            }
            Becado becado = evalguardia.getBecado();
            if (becado != null) {
                becado.getEvalguardiaList().remove(evalguardia);
                becado = em.merge(becado);
            }
            em.remove(evalguardia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evalguardia> findEvalguardiaEntities() {
        return findEvalguardiaEntities(true, -1, -1);
    }

    public List<Evalguardia> findEvalguardiaEntities(int maxResults, int firstResult) {
        return findEvalguardiaEntities(false, maxResults, firstResult);
    }

    private List<Evalguardia> findEvalguardiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evalguardia.class));
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

    public Evalguardia findEvalguardia(EvalguardiaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evalguardia.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvalguardiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evalguardia> rt = cq.from(Evalguardia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
