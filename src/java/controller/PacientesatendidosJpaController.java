
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
import entities.PacatendMedicamentos;
import entities.Pacientesatendidos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class PacientesatendidosJpaController implements Serializable {

    public PacientesatendidosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pacientesatendidos pacientesatendidos) throws PreexistingEntityException, Exception {
        if (pacientesatendidos.getPacatendMedicamentosList() == null) {
            pacientesatendidos.setPacatendMedicamentosList(new ArrayList<PacatendMedicamentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado becadoci = pacientesatendidos.getBecadoci();
            if (becadoci != null) {
                becadoci = em.getReference(becadoci.getClass(), becadoci.getCi());
                pacientesatendidos.setBecadoci(becadoci);
            }
            List<PacatendMedicamentos> attachedPacatendMedicamentosList = new ArrayList<PacatendMedicamentos>();
            for (PacatendMedicamentos pacatendMedicamentosListPacatendMedicamentosToAttach : pacientesatendidos.getPacatendMedicamentosList()) {
                pacatendMedicamentosListPacatendMedicamentosToAttach = em.getReference(pacatendMedicamentosListPacatendMedicamentosToAttach.getClass(), pacatendMedicamentosListPacatendMedicamentosToAttach.getPacatendMedicamentosPK());
                attachedPacatendMedicamentosList.add(pacatendMedicamentosListPacatendMedicamentosToAttach);
            }
            pacientesatendidos.setPacatendMedicamentosList(attachedPacatendMedicamentosList);
            em.persist(pacientesatendidos);
            if (becadoci != null) {
                becadoci.getPacientesatendidosList().add(pacientesatendidos);
                becadoci = em.merge(becadoci);
            }
            for (PacatendMedicamentos pacatendMedicamentosListPacatendMedicamentos : pacientesatendidos.getPacatendMedicamentosList()) {
                Pacientesatendidos oldPacientesatendidosOfPacatendMedicamentosListPacatendMedicamentos = pacatendMedicamentosListPacatendMedicamentos.getPacientesatendidos();
                pacatendMedicamentosListPacatendMedicamentos.setPacientesatendidos(pacientesatendidos);
                pacatendMedicamentosListPacatendMedicamentos = em.merge(pacatendMedicamentosListPacatendMedicamentos);
                if (oldPacientesatendidosOfPacatendMedicamentosListPacatendMedicamentos != null) {
                    oldPacientesatendidosOfPacatendMedicamentosListPacatendMedicamentos.getPacatendMedicamentosList().remove(pacatendMedicamentosListPacatendMedicamentos);
                    oldPacientesatendidosOfPacatendMedicamentosListPacatendMedicamentos = em.merge(oldPacientesatendidosOfPacatendMedicamentosListPacatendMedicamentos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPacientesatendidos(pacientesatendidos.getId()) != null) {
                throw new PreexistingEntityException("Pacientesatendidos " + pacientesatendidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pacientesatendidos pacientesatendidos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacientesatendidos persistentPacientesatendidos = em.find(Pacientesatendidos.class, pacientesatendidos.getId());
            Becado becadociOld = persistentPacientesatendidos.getBecadoci();
            Becado becadociNew = pacientesatendidos.getBecadoci();
            List<PacatendMedicamentos> pacatendMedicamentosListOld = persistentPacientesatendidos.getPacatendMedicamentosList();
            List<PacatendMedicamentos> pacatendMedicamentosListNew = pacientesatendidos.getPacatendMedicamentosList();
            List<String> illegalOrphanMessages = null;
            for (PacatendMedicamentos pacatendMedicamentosListOldPacatendMedicamentos : pacatendMedicamentosListOld) {
                if (!pacatendMedicamentosListNew.contains(pacatendMedicamentosListOldPacatendMedicamentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PacatendMedicamentos " + pacatendMedicamentosListOldPacatendMedicamentos + " since its pacientesatendidos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (becadociNew != null) {
                becadociNew = em.getReference(becadociNew.getClass(), becadociNew.getCi());
                pacientesatendidos.setBecadoci(becadociNew);
            }
            List<PacatendMedicamentos> attachedPacatendMedicamentosListNew = new ArrayList<PacatendMedicamentos>();
            for (PacatendMedicamentos pacatendMedicamentosListNewPacatendMedicamentosToAttach : pacatendMedicamentosListNew) {
                pacatendMedicamentosListNewPacatendMedicamentosToAttach = em.getReference(pacatendMedicamentosListNewPacatendMedicamentosToAttach.getClass(), pacatendMedicamentosListNewPacatendMedicamentosToAttach.getPacatendMedicamentosPK());
                attachedPacatendMedicamentosListNew.add(pacatendMedicamentosListNewPacatendMedicamentosToAttach);
            }
            pacatendMedicamentosListNew = attachedPacatendMedicamentosListNew;
            pacientesatendidos.setPacatendMedicamentosList(pacatendMedicamentosListNew);
            pacientesatendidos = em.merge(pacientesatendidos);
            if (becadociOld != null && !becadociOld.equals(becadociNew)) {
                becadociOld.getPacientesatendidosList().remove(pacientesatendidos);
                becadociOld = em.merge(becadociOld);
            }
            if (becadociNew != null && !becadociNew.equals(becadociOld)) {
                becadociNew.getPacientesatendidosList().add(pacientesatendidos);
                becadociNew = em.merge(becadociNew);
            }
            for (PacatendMedicamentos pacatendMedicamentosListNewPacatendMedicamentos : pacatendMedicamentosListNew) {
                if (!pacatendMedicamentosListOld.contains(pacatendMedicamentosListNewPacatendMedicamentos)) {
                    Pacientesatendidos oldPacientesatendidosOfPacatendMedicamentosListNewPacatendMedicamentos = pacatendMedicamentosListNewPacatendMedicamentos.getPacientesatendidos();
                    pacatendMedicamentosListNewPacatendMedicamentos.setPacientesatendidos(pacientesatendidos);
                    pacatendMedicamentosListNewPacatendMedicamentos = em.merge(pacatendMedicamentosListNewPacatendMedicamentos);
                    if (oldPacientesatendidosOfPacatendMedicamentosListNewPacatendMedicamentos != null && !oldPacientesatendidosOfPacatendMedicamentosListNewPacatendMedicamentos.equals(pacientesatendidos)) {
                        oldPacientesatendidosOfPacatendMedicamentosListNewPacatendMedicamentos.getPacatendMedicamentosList().remove(pacatendMedicamentosListNewPacatendMedicamentos);
                        oldPacientesatendidosOfPacatendMedicamentosListNewPacatendMedicamentos = em.merge(oldPacientesatendidosOfPacatendMedicamentosListNewPacatendMedicamentos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pacientesatendidos.getId();
                if (findPacientesatendidos(id) == null) {
                    throw new NonexistentEntityException("The pacientesatendidos with id " + id + " no longer exists.");
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
            Pacientesatendidos pacientesatendidos;
            try {
                pacientesatendidos = em.getReference(Pacientesatendidos.class, id);
                pacientesatendidos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pacientesatendidos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PacatendMedicamentos> pacatendMedicamentosListOrphanCheck = pacientesatendidos.getPacatendMedicamentosList();
            for (PacatendMedicamentos pacatendMedicamentosListOrphanCheckPacatendMedicamentos : pacatendMedicamentosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pacientesatendidos (" + pacientesatendidos + ") cannot be destroyed since the PacatendMedicamentos " + pacatendMedicamentosListOrphanCheckPacatendMedicamentos + " in its pacatendMedicamentosList field has a non-nullable pacientesatendidos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Becado becadoci = pacientesatendidos.getBecadoci();
            if (becadoci != null) {
                becadoci.getPacientesatendidosList().remove(pacientesatendidos);
                becadoci = em.merge(becadoci);
            }
            em.remove(pacientesatendidos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pacientesatendidos> findPacientesatendidosEntities() {
        return findPacientesatendidosEntities(true, -1, -1);
    }

    public List<Pacientesatendidos> findPacientesatendidosEntities(int maxResults, int firstResult) {
        return findPacientesatendidosEntities(false, maxResults, firstResult);
    }

    private List<Pacientesatendidos> findPacientesatendidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pacientesatendidos.class));
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

    public Pacientesatendidos findPacientesatendidos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pacientesatendidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacientesatendidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pacientesatendidos> rt = cq.from(Pacientesatendidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
