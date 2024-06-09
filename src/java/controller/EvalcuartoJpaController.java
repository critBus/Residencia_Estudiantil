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
import entities.Cuarto;
import entities.Trabajador;
import entities.AspectEvalcuartoEvalcuarto;
import entities.Evalcuarto;
import entities.EvalcuartoPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class EvalcuartoJpaController implements Serializable {

    public EvalcuartoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evalcuarto evalcuarto) throws PreexistingEntityException, Exception {
        if (evalcuarto.getEvalcuartoPK() == null) {
            evalcuarto.setEvalcuartoPK(new EvalcuartoPK());
        }
        if (evalcuarto.getAspectEvalcuartoEvalcuartoList() == null) {
            evalcuarto.setAspectEvalcuartoEvalcuartoList(new ArrayList<AspectEvalcuartoEvalcuarto>());
        }
        evalcuarto.getEvalcuartoPK().setCuartoid(evalcuarto.getCuarto().getCuartoPK().getId());
        evalcuarto.getEvalcuartoPK().setPisoid(evalcuarto.getCuarto().getCuartoPK().getPisoid());
        evalcuarto.getEvalcuartoPK().setEdificioid(evalcuarto.getCuarto().getCuartoPK().getEdificioid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuarto cuarto = evalcuarto.getCuarto();
            if (cuarto != null) {
                cuarto = em.getReference(cuarto.getClass(), cuarto.getCuartoPK());
                evalcuarto.setCuarto(cuarto);
            }
            Trabajador trabajadorci = evalcuarto.getTrabajadorci();
            if (trabajadorci != null) {
                trabajadorci = em.getReference(trabajadorci.getClass(), trabajadorci.getCi());
                evalcuarto.setTrabajadorci(trabajadorci);
            }
            List<AspectEvalcuartoEvalcuarto> attachedAspectEvalcuartoEvalcuartoList = new ArrayList<AspectEvalcuartoEvalcuarto>();
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach : evalcuarto.getAspectEvalcuartoEvalcuartoList()) {
                aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach = em.getReference(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach.getClass(), aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach.getAspectEvalcuartoEvalcuartoPK());
                attachedAspectEvalcuartoEvalcuartoList.add(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuartoToAttach);
            }
            evalcuarto.setAspectEvalcuartoEvalcuartoList(attachedAspectEvalcuartoEvalcuartoList);
            em.persist(evalcuarto);
            if (cuarto != null) {
                cuarto.getEvalcuartoList().add(evalcuarto);
                cuarto = em.merge(cuarto);
            }
            if (trabajadorci != null) {
                trabajadorci.getEvalcuartoList().add(evalcuarto);
                trabajadorci = em.merge(trabajadorci);
            }
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto : evalcuarto.getAspectEvalcuartoEvalcuartoList()) {
                Evalcuarto oldEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto = aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto.getEvalcuarto();
                aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto.setEvalcuarto(evalcuarto);
                aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto = em.merge(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto);
                if (oldEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto != null) {
                    oldEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto);
                    oldEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto = em.merge(oldEvalcuartoOfAspectEvalcuartoEvalcuartoListAspectEvalcuartoEvalcuarto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvalcuarto(evalcuarto.getEvalcuartoPK()) != null) {
                throw new PreexistingEntityException("Evalcuarto " + evalcuarto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evalcuarto evalcuarto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        evalcuarto.getEvalcuartoPK().setCuartoid(evalcuarto.getCuarto().getCuartoPK().getId());
        evalcuarto.getEvalcuartoPK().setPisoid(evalcuarto.getCuarto().getCuartoPK().getPisoid());
        evalcuarto.getEvalcuartoPK().setEdificioid(evalcuarto.getCuarto().getCuartoPK().getEdificioid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalcuarto persistentEvalcuarto = em.find(Evalcuarto.class, evalcuarto.getEvalcuartoPK());
            Cuarto cuartoOld = persistentEvalcuarto.getCuarto();
            Cuarto cuartoNew = evalcuarto.getCuarto();
            Trabajador trabajadorciOld = persistentEvalcuarto.getTrabajadorci();
            Trabajador trabajadorciNew = evalcuarto.getTrabajadorci();
            List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoListOld = persistentEvalcuarto.getAspectEvalcuartoEvalcuartoList();
            List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoListNew = evalcuarto.getAspectEvalcuartoEvalcuartoList();
            List<String> illegalOrphanMessages = null;
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListOldAspectEvalcuartoEvalcuarto : aspectEvalcuartoEvalcuartoListOld) {
                if (!aspectEvalcuartoEvalcuartoListNew.contains(aspectEvalcuartoEvalcuartoListOldAspectEvalcuartoEvalcuarto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AspectEvalcuartoEvalcuarto " + aspectEvalcuartoEvalcuartoListOldAspectEvalcuartoEvalcuarto + " since its evalcuarto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuartoNew != null) {
                cuartoNew = em.getReference(cuartoNew.getClass(), cuartoNew.getCuartoPK());
                evalcuarto.setCuarto(cuartoNew);
            }
            if (trabajadorciNew != null) {
                trabajadorciNew = em.getReference(trabajadorciNew.getClass(), trabajadorciNew.getCi());
                evalcuarto.setTrabajadorci(trabajadorciNew);
            }
            List<AspectEvalcuartoEvalcuarto> attachedAspectEvalcuartoEvalcuartoListNew = new ArrayList<AspectEvalcuartoEvalcuarto>();
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach : aspectEvalcuartoEvalcuartoListNew) {
                aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach = em.getReference(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach.getClass(), aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach.getAspectEvalcuartoEvalcuartoPK());
                attachedAspectEvalcuartoEvalcuartoListNew.add(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuartoToAttach);
            }
            aspectEvalcuartoEvalcuartoListNew = attachedAspectEvalcuartoEvalcuartoListNew;
            evalcuarto.setAspectEvalcuartoEvalcuartoList(aspectEvalcuartoEvalcuartoListNew);
            evalcuarto = em.merge(evalcuarto);
            if (cuartoOld != null && !cuartoOld.equals(cuartoNew)) {
                cuartoOld.getEvalcuartoList().remove(evalcuarto);
                cuartoOld = em.merge(cuartoOld);
            }
            if (cuartoNew != null && !cuartoNew.equals(cuartoOld)) {
                cuartoNew.getEvalcuartoList().add(evalcuarto);
                cuartoNew = em.merge(cuartoNew);
            }
            if (trabajadorciOld != null && !trabajadorciOld.equals(trabajadorciNew)) {
                trabajadorciOld.getEvalcuartoList().remove(evalcuarto);
                trabajadorciOld = em.merge(trabajadorciOld);
            }
            if (trabajadorciNew != null && !trabajadorciNew.equals(trabajadorciOld)) {
                trabajadorciNew.getEvalcuartoList().add(evalcuarto);
                trabajadorciNew = em.merge(trabajadorciNew);
            }
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto : aspectEvalcuartoEvalcuartoListNew) {
                if (!aspectEvalcuartoEvalcuartoListOld.contains(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto)) {
                    Evalcuarto oldEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto = aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.getEvalcuarto();
                    aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.setEvalcuarto(evalcuarto);
                    aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto = em.merge(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto);
                    if (oldEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto != null && !oldEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.equals(evalcuarto)) {
                        oldEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoList().remove(aspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto);
                        oldEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto = em.merge(oldEvalcuartoOfAspectEvalcuartoEvalcuartoListNewAspectEvalcuartoEvalcuarto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EvalcuartoPK id = evalcuarto.getEvalcuartoPK();
                if (findEvalcuarto(id) == null) {
                    throw new NonexistentEntityException("The evalcuarto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EvalcuartoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalcuarto evalcuarto;
            try {
                evalcuarto = em.getReference(Evalcuarto.class, id);
                evalcuarto.getEvalcuartoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evalcuarto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoListOrphanCheck = evalcuarto.getAspectEvalcuartoEvalcuartoList();
            for (AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuartoListOrphanCheckAspectEvalcuartoEvalcuarto : aspectEvalcuartoEvalcuartoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evalcuarto (" + evalcuarto + ") cannot be destroyed since the AspectEvalcuartoEvalcuarto " + aspectEvalcuartoEvalcuartoListOrphanCheckAspectEvalcuartoEvalcuarto + " in its aspectEvalcuartoEvalcuartoList field has a non-nullable evalcuarto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cuarto cuarto = evalcuarto.getCuarto();
            if (cuarto != null) {
                cuarto.getEvalcuartoList().remove(evalcuarto);
                cuarto = em.merge(cuarto);
            }
            Trabajador trabajadorci = evalcuarto.getTrabajadorci();
            if (trabajadorci != null) {
                trabajadorci.getEvalcuartoList().remove(evalcuarto);
                trabajadorci = em.merge(trabajadorci);
            }
            em.remove(evalcuarto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evalcuarto> findEvalcuartoEntities() {
        return findEvalcuartoEntities(true, -1, -1);
    }

    public List<Evalcuarto> findEvalcuartoEntities(int maxResults, int firstResult) {
        return findEvalcuartoEntities(false, maxResults, firstResult);
    }

    private List<Evalcuarto> findEvalcuartoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evalcuarto.class));
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

    public Evalcuarto findEvalcuarto(EvalcuartoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evalcuarto.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvalcuartoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evalcuarto> rt = cq.from(Evalcuarto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
