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
import entities.Medicamentos;
import entities.PacatendMedicamentos;
import entities.PacatendMedicamentosPK;
import entities.Pacientesatendidos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class PacatendMedicamentosJpaController implements Serializable {

    public PacatendMedicamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PacatendMedicamentos pacatendMedicamentos) throws PreexistingEntityException, Exception {
        if (pacatendMedicamentos.getPacatendMedicamentosPK() == null) {
            pacatendMedicamentos.setPacatendMedicamentosPK(new PacatendMedicamentosPK());
        }
        pacatendMedicamentos.getPacatendMedicamentosPK().setMedicamid(pacatendMedicamentos.getMedicamentos().getId());
        pacatendMedicamentos.getPacatendMedicamentosPK().setPacatendid(pacatendMedicamentos.getPacientesatendidos().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamentos medicamentos = pacatendMedicamentos.getMedicamentos();
            if (medicamentos != null) {
                medicamentos = em.getReference(medicamentos.getClass(), medicamentos.getId());
                pacatendMedicamentos.setMedicamentos(medicamentos);
            }
            Pacientesatendidos pacientesatendidos = pacatendMedicamentos.getPacientesatendidos();
            if (pacientesatendidos != null) {
                pacientesatendidos = em.getReference(pacientesatendidos.getClass(), pacientesatendidos.getId());
                pacatendMedicamentos.setPacientesatendidos(pacientesatendidos);
            }
            em.persist(pacatendMedicamentos);
            if (medicamentos != null) {
                medicamentos.getPacatendMedicamentosList().add(pacatendMedicamentos);
                medicamentos = em.merge(medicamentos);
            }
            if (pacientesatendidos != null) {
                pacientesatendidos.getPacatendMedicamentosList().add(pacatendMedicamentos);
                pacientesatendidos = em.merge(pacientesatendidos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPacatendMedicamentos(pacatendMedicamentos.getPacatendMedicamentosPK()) != null) {
                throw new PreexistingEntityException("PacatendMedicamentos " + pacatendMedicamentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PacatendMedicamentos pacatendMedicamentos) throws NonexistentEntityException, Exception {
        pacatendMedicamentos.getPacatendMedicamentosPK().setMedicamid(pacatendMedicamentos.getMedicamentos().getId());
        pacatendMedicamentos.getPacatendMedicamentosPK().setPacatendid(pacatendMedicamentos.getPacientesatendidos().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PacatendMedicamentos persistentPacatendMedicamentos = em.find(PacatendMedicamentos.class, pacatendMedicamentos.getPacatendMedicamentosPK());
            Medicamentos medicamentosOld = persistentPacatendMedicamentos.getMedicamentos();
            Medicamentos medicamentosNew = pacatendMedicamentos.getMedicamentos();
            Pacientesatendidos pacientesatendidosOld = persistentPacatendMedicamentos.getPacientesatendidos();
            Pacientesatendidos pacientesatendidosNew = pacatendMedicamentos.getPacientesatendidos();
            if (medicamentosNew != null) {
                medicamentosNew = em.getReference(medicamentosNew.getClass(), medicamentosNew.getId());
                pacatendMedicamentos.setMedicamentos(medicamentosNew);
            }
            if (pacientesatendidosNew != null) {
                pacientesatendidosNew = em.getReference(pacientesatendidosNew.getClass(), pacientesatendidosNew.getId());
                pacatendMedicamentos.setPacientesatendidos(pacientesatendidosNew);
            }
            pacatendMedicamentos = em.merge(pacatendMedicamentos);
            if (medicamentosOld != null && !medicamentosOld.equals(medicamentosNew)) {
                medicamentosOld.getPacatendMedicamentosList().remove(pacatendMedicamentos);
                medicamentosOld = em.merge(medicamentosOld);
            }
            if (medicamentosNew != null && !medicamentosNew.equals(medicamentosOld)) {
                medicamentosNew.getPacatendMedicamentosList().add(pacatendMedicamentos);
                medicamentosNew = em.merge(medicamentosNew);
            }
            if (pacientesatendidosOld != null && !pacientesatendidosOld.equals(pacientesatendidosNew)) {
                pacientesatendidosOld.getPacatendMedicamentosList().remove(pacatendMedicamentos);
                pacientesatendidosOld = em.merge(pacientesatendidosOld);
            }
            if (pacientesatendidosNew != null && !pacientesatendidosNew.equals(pacientesatendidosOld)) {
                pacientesatendidosNew.getPacatendMedicamentosList().add(pacatendMedicamentos);
                pacientesatendidosNew = em.merge(pacientesatendidosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PacatendMedicamentosPK id = pacatendMedicamentos.getPacatendMedicamentosPK();
                if (findPacatendMedicamentos(id) == null) {
                    throw new NonexistentEntityException("The pacatendMedicamentos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PacatendMedicamentosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PacatendMedicamentos pacatendMedicamentos;
            try {
                pacatendMedicamentos = em.getReference(PacatendMedicamentos.class, id);
                pacatendMedicamentos.getPacatendMedicamentosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pacatendMedicamentos with id " + id + " no longer exists.", enfe);
            }
            Medicamentos medicamentos = pacatendMedicamentos.getMedicamentos();
            if (medicamentos != null) {
                medicamentos.getPacatendMedicamentosList().remove(pacatendMedicamentos);
                medicamentos = em.merge(medicamentos);
            }
            Pacientesatendidos pacientesatendidos = pacatendMedicamentos.getPacientesatendidos();
            if (pacientesatendidos != null) {
                pacientesatendidos.getPacatendMedicamentosList().remove(pacatendMedicamentos);
                pacientesatendidos = em.merge(pacientesatendidos);
            }
            em.remove(pacatendMedicamentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PacatendMedicamentos> findPacatendMedicamentosEntities() {
        return findPacatendMedicamentosEntities(true, -1, -1);
    }

    public List<PacatendMedicamentos> findPacatendMedicamentosEntities(int maxResults, int firstResult) {
        return findPacatendMedicamentosEntities(false, maxResults, firstResult);
    }

    private List<PacatendMedicamentos> findPacatendMedicamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PacatendMedicamentos.class));
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

    public PacatendMedicamentos findPacatendMedicamentos(PacatendMedicamentosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PacatendMedicamentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacatendMedicamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PacatendMedicamentos> rt = cq.from(PacatendMedicamentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
