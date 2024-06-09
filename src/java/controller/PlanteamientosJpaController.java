
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Becado;
import entities.Planteamientos;
import entities.Trabajador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class PlanteamientosJpaController implements Serializable {

    public PlanteamientosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Planteamientos planteamientos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado becadoci = planteamientos.getBecadoci();
            if (becadoci != null) {
                becadoci = em.getReference(becadoci.getClass(), becadoci.getCi());
                planteamientos.setBecadoci(becadoci);
            }
            Trabajador trabajadorci = planteamientos.getTrabajadorci();
            if (trabajadorci != null) {
                trabajadorci = em.getReference(trabajadorci.getClass(), trabajadorci.getCi());
                planteamientos.setTrabajadorci(trabajadorci);
            }
            em.persist(planteamientos);
            if (becadoci != null) {
                becadoci.getPlanteamientosList().add(planteamientos);
                becadoci = em.merge(becadoci);
            }
            if (trabajadorci != null) {
                trabajadorci.getPlanteamientosList().add(planteamientos);
                trabajadorci = em.merge(trabajadorci);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanteamientos(planteamientos.getId()) != null) {
                throw new PreexistingEntityException("Planteamientos " + planteamientos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Planteamientos planteamientos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planteamientos persistentPlanteamientos = em.find(Planteamientos.class, planteamientos.getId());
            Becado becadociOld = persistentPlanteamientos.getBecadoci();
            Becado becadociNew = planteamientos.getBecadoci();
            Trabajador trabajadorciOld = persistentPlanteamientos.getTrabajadorci();
            Trabajador trabajadorciNew = planteamientos.getTrabajadorci();
            if (becadociNew != null) {
                becadociNew = em.getReference(becadociNew.getClass(), becadociNew.getCi());
                planteamientos.setBecadoci(becadociNew);
            }
            if (trabajadorciNew != null) {
                trabajadorciNew = em.getReference(trabajadorciNew.getClass(), trabajadorciNew.getCi());
                planteamientos.setTrabajadorci(trabajadorciNew);
            }
            planteamientos = em.merge(planteamientos);
            if (becadociOld != null && !becadociOld.equals(becadociNew)) {
                becadociOld.getPlanteamientosList().remove(planteamientos);
                becadociOld = em.merge(becadociOld);
            }
            if (becadociNew != null && !becadociNew.equals(becadociOld)) {
                becadociNew.getPlanteamientosList().add(planteamientos);
                becadociNew = em.merge(becadociNew);
            }
            if (trabajadorciOld != null && !trabajadorciOld.equals(trabajadorciNew)) {
                trabajadorciOld.getPlanteamientosList().remove(planteamientos);
                trabajadorciOld = em.merge(trabajadorciOld);
            }
            if (trabajadorciNew != null && !trabajadorciNew.equals(trabajadorciOld)) {
                trabajadorciNew.getPlanteamientosList().add(planteamientos);
                trabajadorciNew = em.merge(trabajadorciNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planteamientos.getId();
                if (findPlanteamientos(id) == null) {
                    throw new NonexistentEntityException("The planteamientos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planteamientos planteamientos;
            try {
                planteamientos = em.getReference(Planteamientos.class, id);
                planteamientos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planteamientos with id " + id + " no longer exists.", enfe);
            }
            Becado becadoci = planteamientos.getBecadoci();
            if (becadoci != null) {
                becadoci.getPlanteamientosList().remove(planteamientos);
                becadoci = em.merge(becadoci);
            }
            Trabajador trabajadorci = planteamientos.getTrabajadorci();
            if (trabajadorci != null) {
                trabajadorci.getPlanteamientosList().remove(planteamientos);
                trabajadorci = em.merge(trabajadorci);
            }
            em.remove(planteamientos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Planteamientos> findPlanteamientosEntities() {
        return findPlanteamientosEntities(true, -1, -1);
    }

    public List<Planteamientos> findPlanteamientosEntities(int maxResults, int firstResult) {
        return findPlanteamientosEntities(false, maxResults, firstResult);
    }

    private List<Planteamientos> findPlanteamientosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Planteamientos.class));
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

    public Planteamientos findPlanteamientos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Planteamientos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanteamientosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Planteamientos> rt = cq.from(Planteamientos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
