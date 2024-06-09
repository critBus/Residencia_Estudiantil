
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Medico;
import entities.Pacientesatendidos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MedicoJpaController implements Serializable{

    public MedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(Medico medico) throws PreexistingEntityException, Exception {
        if (medico.getPacientesatendidosList()== null) {
            medico.setPacientesatendidosList(new ArrayList<Pacientesatendidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pacientesatendidos> attachedPacientesatendidosList = new ArrayList<Pacientesatendidos>();
            for (Pacientesatendidos pacientesatendidosListPacientesatendidosToAttach : medico.getPacientesatendidosList()) {
                pacientesatendidosListPacientesatendidosToAttach = em.getReference(pacientesatendidosListPacientesatendidosToAttach.getClass(), pacientesatendidosListPacientesatendidosToAttach.getId());
                attachedPacientesatendidosList.add(pacientesatendidosListPacientesatendidosToAttach);
            }
            medico.setPacientesatendidosList(attachedPacientesatendidosList);
            em.persist(medico);
            for (Pacientesatendidos pacientesatendidosListPacientesatendidos : medico.getPacientesatendidosList()) {
                Medico oldMedicoOfPacientesatendidosListPacientesatendidos = pacientesatendidosListPacientesatendidos.getMedicociMedico();
                pacientesatendidosListPacientesatendidos.setMedicociMedico(medico);
                pacientesatendidosListPacientesatendidos = em.merge(pacientesatendidosListPacientesatendidos);
                if (oldMedicoOfPacientesatendidosListPacientesatendidos != null) {
                    oldMedicoOfPacientesatendidosListPacientesatendidos.getPacientesatendidosList().remove(pacientesatendidosListPacientesatendidos);
                    oldMedicoOfPacientesatendidosListPacientesatendidos = em.merge(oldMedicoOfPacientesatendidosListPacientesatendidos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedico(medico.getCiMedico()) != null) {
                throw new PreexistingEntityException("Medico " + medico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medico medico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medico persistentMedico = em.find(Medico.class, medico.getCiMedico());
            List<Pacientesatendidos> pacientesatendidosListOld = persistentMedico.getPacientesatendidosList();
            List<Pacientesatendidos> pacientesatendidosListNew = medico.getPacientesatendidosList();
            List<String> illegalOrphanMessages = null;
            for (Pacientesatendidos pacientesatendidosListOldPacientesatendidos : pacientesatendidosListOld) {
                if (!pacientesatendidosListNew.contains(pacientesatendidosListOldPacientesatendidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articuloreglam " + pacientesatendidosListOldPacientesatendidos + " since its capituloreglam field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pacientesatendidos> attachedPacientesatendidosListNew = new ArrayList<Pacientesatendidos>();
            for (Pacientesatendidos pacientesatendidosListNewPacientesatendidosToAttach : pacientesatendidosListNew) {
                pacientesatendidosListNewPacientesatendidosToAttach = em.getReference(pacientesatendidosListNewPacientesatendidosToAttach.getClass(), pacientesatendidosListNewPacientesatendidosToAttach.getId());
                attachedPacientesatendidosListNew.add(pacientesatendidosListNewPacientesatendidosToAttach);
            }
            pacientesatendidosListNew = attachedPacientesatendidosListNew;
            medico.setPacientesatendidosList(pacientesatendidosListNew);
            medico = em.merge(medico);
            for (Pacientesatendidos pacientesatendidosListNewPacientesatendidos : pacientesatendidosListNew) {
                if (!pacientesatendidosListOld.contains(pacientesatendidosListNewPacientesatendidos)) {
                    Medico oldMedicoOfPacientesatendidosListNewPacientesatendidos = pacientesatendidosListNewPacientesatendidos.getMedicociMedico();
                    pacientesatendidosListNewPacientesatendidos.setMedicociMedico(medico);
                    pacientesatendidosListNewPacientesatendidos = em.merge(pacientesatendidosListNewPacientesatendidos);
                    if (oldMedicoOfPacientesatendidosListNewPacientesatendidos != null && !oldMedicoOfPacientesatendidosListNewPacientesatendidos.equals(medico)) {
                        oldMedicoOfPacientesatendidosListNewPacientesatendidos.getPacientesatendidosList().remove(pacientesatendidosListNewPacientesatendidos);
                        oldMedicoOfPacientesatendidosListNewPacientesatendidos = em.merge(oldMedicoOfPacientesatendidosListNewPacientesatendidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = medico.getCiMedico();
                if (findMedico(id) == null) {
                    throw new NonexistentEntityException("The medico with id " + id + " no longer exists.");
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
            Medico medico;
            try {
                medico = em.getReference(Medico.class, id);
                medico.getCiMedico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capituloreglam with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pacientesatendidos> pacientesatendidosListOrphanCheck = medico.getPacientesatendidosList();
            for (Pacientesatendidos pacientesatendidosListOrphanCheckPacientesatendidos : pacientesatendidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capituloreglam (" + medico + ") cannot be destroyed since the Articuloreglam " + pacientesatendidosListOrphanCheckPacientesatendidos + " in its articuloreglamList field has a non-nullable capituloreglam field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(medico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medico> findMedicoEntities() {
        return findMedicoEntities(true, -1, -1);
    }

    public List<Medico> findMedicoEntities(int maxResults, int firstResult) {
        return findMedicoEntities(false, maxResults, firstResult);
    }

    private List<Medico> findMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medico.class));
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

    public Medico findMedico(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medico.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medico> rt = cq.from(Medico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
