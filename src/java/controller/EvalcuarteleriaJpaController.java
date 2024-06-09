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
import entities.Becado;
import entities.Evalcuarteleria;
import entities.Trabajador;
import entities.EvalcuarteleriaAspectevalcuartel;
import entities.EvalcuarteleriaPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class EvalcuarteleriaJpaController implements Serializable {

    public EvalcuarteleriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evalcuarteleria evalcuarteleria) throws PreexistingEntityException, Exception {
        if (evalcuarteleria.getEvalcuarteleriaPK() == null) {
            evalcuarteleria.setEvalcuarteleriaPK(new EvalcuarteleriaPK());
        }
        if (evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList() == null) {
            evalcuarteleria.setEvalcuarteleriaAspectevalcuartelList(new ArrayList<EvalcuarteleriaAspectevalcuartel>());
        }
        evalcuarteleria.getEvalcuarteleriaPK().setBecadoci(evalcuarteleria.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado becado = evalcuarteleria.getBecado();
            if (becado != null) {
                becado = em.getReference(becado.getClass(), becado.getCi());
                evalcuarteleria.setBecado(becado);
            }
            Trabajador inspeccionaci = evalcuarteleria.getInspeccionaci();
            if (inspeccionaci != null) {
                inspeccionaci = em.getReference(inspeccionaci.getClass(), inspeccionaci.getCi());
                evalcuarteleria.setInspeccionaci(inspeccionaci);
            }
            List<EvalcuarteleriaAspectevalcuartel> attachedEvalcuarteleriaAspectevalcuartelList = new ArrayList<EvalcuarteleriaAspectevalcuartel>();
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach : evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList()) {
                evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach = em.getReference(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach.getClass(), evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach.getEvalcuarteleriaAspectevalcuartelPK());
                attachedEvalcuarteleriaAspectevalcuartelList.add(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartelToAttach);
            }
            evalcuarteleria.setEvalcuarteleriaAspectevalcuartelList(attachedEvalcuarteleriaAspectevalcuartelList);
            em.persist(evalcuarteleria);
            if (becado != null) {
                becado.getEvalcuarteleriaList().add(evalcuarteleria);
                becado = em.merge(becado);
            }
            if (inspeccionaci != null) {
                inspeccionaci.getEvalcuarteleriaList().add(evalcuarteleria);
                inspeccionaci = em.merge(inspeccionaci);
            }
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel : evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList()) {
                Evalcuarteleria oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel = evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel.getEvalcuarteleria();
                evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel.setEvalcuarteleria(evalcuarteleria);
                evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel = em.merge(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel);
                if (oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel != null) {
                    oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel);
                    oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel = em.merge(oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListEvalcuarteleriaAspectevalcuartel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvalcuarteleria(evalcuarteleria.getEvalcuarteleriaPK()) != null) {
                throw new PreexistingEntityException("Evalcuarteleria " + evalcuarteleria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evalcuarteleria evalcuarteleria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        evalcuarteleria.getEvalcuarteleriaPK().setBecadoci(evalcuarteleria.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalcuarteleria persistentEvalcuarteleria = em.find(Evalcuarteleria.class, evalcuarteleria.getEvalcuarteleriaPK());
            Becado becadoOld = persistentEvalcuarteleria.getBecado();
            Becado becadoNew = evalcuarteleria.getBecado();
            Trabajador inspeccionaciOld = persistentEvalcuarteleria.getInspeccionaci();
            Trabajador inspeccionaciNew = evalcuarteleria.getInspeccionaci();
            List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelListOld = persistentEvalcuarteleria.getEvalcuarteleriaAspectevalcuartelList();
            List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelListNew = evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList();
            List<String> illegalOrphanMessages = null;
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListOldEvalcuarteleriaAspectevalcuartel : evalcuarteleriaAspectevalcuartelListOld) {
                if (!evalcuarteleriaAspectevalcuartelListNew.contains(evalcuarteleriaAspectevalcuartelListOldEvalcuarteleriaAspectevalcuartel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvalcuarteleriaAspectevalcuartel " + evalcuarteleriaAspectevalcuartelListOldEvalcuarteleriaAspectevalcuartel + " since its evalcuarteleria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (becadoNew != null) {
                becadoNew = em.getReference(becadoNew.getClass(), becadoNew.getCi());
                evalcuarteleria.setBecado(becadoNew);
            }
            if (inspeccionaciNew != null) {
                inspeccionaciNew = em.getReference(inspeccionaciNew.getClass(), inspeccionaciNew.getCi());
                evalcuarteleria.setInspeccionaci(inspeccionaciNew);
            }
            List<EvalcuarteleriaAspectevalcuartel> attachedEvalcuarteleriaAspectevalcuartelListNew = new ArrayList<EvalcuarteleriaAspectevalcuartel>();
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach : evalcuarteleriaAspectevalcuartelListNew) {
                evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach = em.getReference(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach.getClass(), evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach.getEvalcuarteleriaAspectevalcuartelPK());
                attachedEvalcuarteleriaAspectevalcuartelListNew.add(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartelToAttach);
            }
            evalcuarteleriaAspectevalcuartelListNew = attachedEvalcuarteleriaAspectevalcuartelListNew;
            evalcuarteleria.setEvalcuarteleriaAspectevalcuartelList(evalcuarteleriaAspectevalcuartelListNew);
            evalcuarteleria = em.merge(evalcuarteleria);
            if (becadoOld != null && !becadoOld.equals(becadoNew)) {
                becadoOld.getEvalcuarteleriaList().remove(evalcuarteleria);
                becadoOld = em.merge(becadoOld);
            }
            if (becadoNew != null && !becadoNew.equals(becadoOld)) {
                becadoNew.getEvalcuarteleriaList().add(evalcuarteleria);
                becadoNew = em.merge(becadoNew);
            }
            if (inspeccionaciOld != null && !inspeccionaciOld.equals(inspeccionaciNew)) {
                inspeccionaciOld.getEvalcuarteleriaList().remove(evalcuarteleria);
                inspeccionaciOld = em.merge(inspeccionaciOld);
            }
            if (inspeccionaciNew != null && !inspeccionaciNew.equals(inspeccionaciOld)) {
                inspeccionaciNew.getEvalcuarteleriaList().add(evalcuarteleria);
                inspeccionaciNew = em.merge(inspeccionaciNew);
            }
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel : evalcuarteleriaAspectevalcuartelListNew) {
                if (!evalcuarteleriaAspectevalcuartelListOld.contains(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel)) {
                    Evalcuarteleria oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel = evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.getEvalcuarteleria();
                    evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.setEvalcuarteleria(evalcuarteleria);
                    evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel = em.merge(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel);
                    if (oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel != null && !oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.equals(evalcuarteleria)) {
                        oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel.getEvalcuarteleriaAspectevalcuartelList().remove(evalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel);
                        oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel = em.merge(oldEvalcuarteleriaOfEvalcuarteleriaAspectevalcuartelListNewEvalcuarteleriaAspectevalcuartel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EvalcuarteleriaPK id = evalcuarteleria.getEvalcuarteleriaPK();
                if (findEvalcuarteleria(id) == null) {
                    throw new NonexistentEntityException("The evalcuarteleria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EvalcuarteleriaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evalcuarteleria evalcuarteleria;
            try {
                evalcuarteleria = em.getReference(Evalcuarteleria.class, id);
                evalcuarteleria.getEvalcuarteleriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evalcuarteleria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelListOrphanCheck = evalcuarteleria.getEvalcuarteleriaAspectevalcuartelList();
            for (EvalcuarteleriaAspectevalcuartel evalcuarteleriaAspectevalcuartelListOrphanCheckEvalcuarteleriaAspectevalcuartel : evalcuarteleriaAspectevalcuartelListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evalcuarteleria (" + evalcuarteleria + ") cannot be destroyed since the EvalcuarteleriaAspectevalcuartel " + evalcuarteleriaAspectevalcuartelListOrphanCheckEvalcuarteleriaAspectevalcuartel + " in its evalcuarteleriaAspectevalcuartelList field has a non-nullable evalcuarteleria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Becado becado = evalcuarteleria.getBecado();
            if (becado != null) {
                becado.getEvalcuarteleriaList().remove(evalcuarteleria);
                becado = em.merge(becado);
            }
            Trabajador inspeccionaci = evalcuarteleria.getInspeccionaci();
            if (inspeccionaci != null) {
                inspeccionaci.getEvalcuarteleriaList().remove(evalcuarteleria);
                inspeccionaci = em.merge(inspeccionaci);
            }
            em.remove(evalcuarteleria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evalcuarteleria> findEvalcuarteleriaEntities() {
        return findEvalcuarteleriaEntities(true, -1, -1);
    }

    public List<Evalcuarteleria> findEvalcuarteleriaEntities(int maxResults, int firstResult) {
        return findEvalcuarteleriaEntities(false, maxResults, firstResult);
    }

    private List<Evalcuarteleria> findEvalcuarteleriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evalcuarteleria.class));
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

    public Evalcuarteleria findEvalcuarteleria(EvalcuarteleriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evalcuarteleria.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvalcuarteleriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evalcuarteleria> rt = cq.from(Evalcuarteleria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
