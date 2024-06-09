/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuartel;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.EvalcuarteleriaAspectevalcuartel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class AspectEvalcuartelJpaController implements Serializable {

    public AspectEvalcuartelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AspectEvalcuartel aspectEvalcuartel) {
        if (aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList() == null) {
            aspectEvalcuartel.setEvalcuarteleriaAspectevalcuartelList(new ArrayList<EvalcuarteleriaAspectevalcuartel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EvalcuarteleriaAspectevalcuartel> attachedEvalcuarteleriaAspectevalcuartelList = new ArrayList<EvalcuarteleriaAspectevalcuartel>();
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach : aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList()) {
                evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach = em.getReference(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach.getClass(), evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach.getEvalcuarteleriaAspectevalcuartelPK());
                attachedEvalcuarteleriaAspectevalcuartelList.add(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach);
            }
            aspectEvalcuartel.setEvalcuarteleriaAspectevalcuartelList(attachedEvalcuarteleriaAspectevalcuartelList);
            em.persist(aspectEvalcuartel);
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel : aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList()) {
                AspectEvalcuartel oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel = evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel.getAspectEvalcuartel();
                evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel.setAspectEvalcuartel(aspectEvalcuartel);
                evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel = em.merge(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel);
                if (oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel != null) {
                    oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel);
                    oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel = em.merge(oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AspectEvalcuartel aspectEvalcuartel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuartel persistentAspectEvalcuartel = em.find(AspectEvalcuartel.class, aspectEvalcuartel.getId());
            List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelListOld = persistentAspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList();
            List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelListNew = aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList();
            List<String> illegalOrphanMessages = null;
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListOldEvalcuarteleriaAspectevalcuartel : evalcuarteleriaAspectevalcuartelListOld) {
                if (!evalcuarteleriaAspectevalcuartelListNew.contains(evalcuarteleriaAspectevalcuartelListOldEvalcuarteleriaAspectevalcuartel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvalcuarteleriaAspectevalcuartel " + evalcuarteleriaAspectevalcuartelListOldEvalcuarteleriaAspectevalcuartel + " since its aspectEvalcuartel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EvalcuarteleriaAspectevalcuartel> attachedEvalcuarteleriaAspectevalcuartelListNew = new ArrayList<EvalcuarteleriaAspectevalcuartel>();
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach : evalcuarteleriaAspectevalcuartelListNew) {
                evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach = em.getReference(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach.getClass(), evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach.getEvalcuarteleriaAspectevalcuartelPK());
                attachedEvalcuarteleriaAspectevalcuartelListNew.add(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach);
            }
            evalcuarteleriaAspectevalcuartelListNew = attachedEvalcuarteleriaAspectevalcuartelListNew;
            aspectEvalcuartel.setEvalcuarteleriaAspectevalcuartelList(evalcuarteleriaAspectevalcuartelListNew);
            aspectEvalcuartel = em.merge(aspectEvalcuartel);
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel : evalcuarteleriaAspectevalcuartelListNew) {
                if (!evalcuarteleriaAspectevalcuartelListOld.contains(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel)) {
                    AspectEvalcuartel oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel = evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.getAspectEvalcuartel();
                    evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.setAspectEvalcuartel(aspectEvalcuartel);
                    evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel = em.merge(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel);
                    if (oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel != null && !oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.equals(aspectEvalcuartel)) {
                        oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel);
                        oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel = em.merge(oldAspectEvalcuartelOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aspectEvalcuartel.getId();
                if (findAspectEvalcuartel(id) == null) {
                    throw new NonexistentEntityException("The aspectEvalcuartel with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuartel aspectEvalcuartel;
            try {
                aspectEvalcuartel = em.getReference(AspectEvalcuartel.class, id);
                aspectEvalcuartel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aspectEvalcuartel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelListOrphanCheck = aspectEvalcuartel.getEvalcuarteleriaAspectevalcuartelList();
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListOrphanCheckEvalcuarteleriaAspectevalcuartel : evalcuarteleriaAspectevalcuartelListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AspectEvalcuartel (" + aspectEvalcuartel + ") cannot be destroyed since the EvalcuarteleriaAspectevalcuartel " + evalcuarteleriaAspectevalcuartelListOrphanCheckEvalcuarteleriaAspectevalcuartel + " in its evalcuarteleriaAspectevalcuartelList field has a non-nullable aspectEvalcuartel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(aspectEvalcuartel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AspectEvalcuartel> findAspectEvalcuartelEntities() {
        return findAspectEvalcuartelEntities(true, -1, -1);
    }

    public List<AspectEvalcuartel> findAspectEvalcuartelEntities(int maxResults, int firstResult) {
        return findAspectEvalcuartelEntities(false, maxResults, firstResult);
    }

    private List<AspectEvalcuartel> findAspectEvalcuartelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AspectEvalcuartel.class));
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

    public AspectEvalcuartel findAspectEvalcuartel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AspectEvalcuartel.class, id);
        } finally {
            em.close();
        }
    }

    public int getAspectEvalcuartelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AspectEvalcuartel> rt = cq.from(AspectEvalcuartel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
