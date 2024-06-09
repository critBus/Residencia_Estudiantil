/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuarto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.AspectEvalcuartoEvalcuarto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class AspectEvalcuartoJpaController implements Serializable {

    public AspectEvalcuartoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AspectEvalcuarto aspectEvalcuarto) {
        if (aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList() == null) {
            aspectEvalcuarto.setAspectEvalcuartoEvalcuartoList(new ArrayList<AspectEvalcuartoEvalcuarto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AspectEvalcuartoEvalcuarto> attachedAspectEvalcuartoEvalcuartoList = new ArrayList<AspectEvalcuartoEvalcuarto>();
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach : aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList()) {
                aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach = em.getReference(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach.getClass(), aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach.getAspectEvalcuartoEvalcuartoPK());
                attachedAspectEvalcuartoEvalcuartoList.add(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach);
            }
            aspectEvalcuarto.setAspectEvalcuartoEvalcuartoList(attachedAspectEvalcuartoEvalcuartoList);
            em.persist(aspectEvalcuarto);
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto : aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList()) {
                AspectEvalcuarto oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto = aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto.getAspectEvalcuarto();
                aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto.setAspectEvalcuarto(aspectEvalcuarto);
                aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto = em.merge(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto);
                if (oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto != null) {
                    oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto);
                    oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto = em.merge(oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AspectEvalcuarto aspectEvalcuarto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AspectEvalcuarto persistentAspectEvalcuarto = em.find(AspectEvalcuarto.class, aspectEvalcuarto.getId());
            List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoListOld = persistentAspectEvalcuarto.getAspectEvalcuartoEvalcuartoList();
            List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoListNew = aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList();
            List<String> illegalOrphanMessages = null;
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListOldAspectEvalcuartoEvalcuarto : aspectEvalcuartoEvalcuartoListOld) {
                if (!aspectEvalcuartoEvalcuartoListNew.contains(aspectEvalcuartoEvalcuartoListOldAspectEvalcuartoEvalcuarto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AspectEvalcuartoEvalcuarto " + aspectEvalcuartoEvalcuartoListOldAspectEvalcuartoEvalcuarto + " since its aspectEvalcuarto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AspectEvalcuartoEvalcuarto> attachedAspectEvalcuartoEvalcuartoListNew = new ArrayList<AspectEvalcuartoEvalcuarto>();
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach : aspectEvalcuartoEvalcuartoListNew) {
                aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach = em.getReference(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach.getClass(), aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach.getAspectEvalcuartoEvalcuartoPK());
                attachedAspectEvalcuartoEvalcuartoListNew.add(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach);
            }
            aspectEvalcuartoEvalcuartoListNew = attachedAspectEvalcuartoEvalcuartoListNew;
            aspectEvalcuarto.setAspectEvalcuartoEvalcuartoList(aspectEvalcuartoEvalcuartoListNew);
            aspectEvalcuarto = em.merge(aspectEvalcuarto);
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto : aspectEvalcuartoEvalcuartoListNew) {
                if (!aspectEvalcuartoEvalcuartoListOld.contains(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto)) {
                    AspectEvalcuarto oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto = aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.getAspectEvalcuarto();
                    aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.setAspectEvalcuarto(aspectEvalcuarto);
                    aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto = em.merge(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto);
                    if (oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto != null && !oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.equals(aspectEvalcuarto)) {
                        oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto);
                        oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto = em.merge(oldAspectEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aspectEvalcuarto.getId();
                if (findAspectEvalcuarto(id) == null) {
                    throw new NonexistentEntityException("The aspectEvalcuarto with id " + id + " no longer exists.");
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
            AspectEvalcuarto aspectEvalcuarto;
            try {
                aspectEvalcuarto = em.getReference(AspectEvalcuarto.class, id);
                aspectEvalcuarto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aspectEvalcuarto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoListOrphanCheck = aspectEvalcuarto.getAspectEvalcuartoEvalcuartoList();
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListOrphanCheckAspectEvalcuartoEvalcuarto : aspectEvalcuartoEvalcuartoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AspectEvalcuarto (" + aspectEvalcuarto + ") cannot be destroyed since the AspectEvalcuartoEvalcuarto " + aspectEvalcuartoEvalcuartoListOrphanCheckAspectEvalcuartoEvalcuarto + " in its aspectEvalcuartoEvalcuartoList field has a non-nullable aspectEvalcuarto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(aspectEvalcuarto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AspectEvalcuarto> findAspectEvalcuartoEntities() {
        return findAspectEvalcuartoEntities(true, -1, -1);
    }

    public List<AspectEvalcuarto> findAspectEvalcuartoEntities(int maxResults, int firstResult) {
        return findAspectEvalcuartoEntities(false, maxResults, firstResult);
    }

    private List<AspectEvalcuarto> findAspectEvalcuartoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AspectEvalcuarto.class));
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

    public AspectEvalcuarto findAspectEvalcuarto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AspectEvalcuarto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAspectEvalcuartoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AspectEvalcuarto> rt = cq.from(AspectEvalcuarto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
