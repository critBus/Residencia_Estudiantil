/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entities.Becado;
import entities.BecadoMedicamentos;
import entities.BecadoMedicamentosPK;
import entities.Medicamentos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Euclides
 */
public class BecadoMedicamentoJpaController implements Serializable{
    
    public BecadoMedicamentoJpaController (EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BecadoMedicamentos becadoMedicamentos) throws PreexistingEntityException, Exception {
        if (becadoMedicamentos.getBecadoMedicamentosPK()== null) {
            becadoMedicamentos.setBecadoMedicamentosPK(new BecadoMedicamentosPK());
        }
        becadoMedicamentos.getBecadoMedicamentosPK().setMedicamid(becadoMedicamentos.getMedicamentos().getId());
        becadoMedicamentos.getBecadoMedicamentosPK().setBecadoci(becadoMedicamentos.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamentos medicamentos = becadoMedicamentos.getMedicamentos();
            if (medicamentos != null) {
                medicamentos = em.getReference(medicamentos.getClass(), medicamentos.getId());
                becadoMedicamentos.setMedicamentos(medicamentos);
            }
            Becado becado = becadoMedicamentos.getBecado();
            if (becado != null) {
                becado = em.getReference(becado.getClass(), becado.getCi());
                becadoMedicamentos.setBecado(becado);
            }
            em.persist(becadoMedicamentos);
            if (medicamentos != null) {
                medicamentos.getBecadoMedicamentosList().add(becadoMedicamentos);
                medicamentos = em.merge(medicamentos);
            }
            if (becado != null) {
                becado.getBecadoMedicamentosList().add(becadoMedicamentos);
                becado = em.merge(becado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBecadoMedicamentos(becadoMedicamentos.getBecadoMedicamentosPK()) != null) {
                throw new PreexistingEntityException("BecadoEnfermedades " + becadoMedicamentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BecadoMedicamentos becadoMedicamentos) throws NonexistentEntityException, Exception {
        becadoMedicamentos.getBecadoMedicamentosPK().setMedicamid(becadoMedicamentos.getMedicamentos().getId());
        becadoMedicamentos.getBecadoMedicamentosPK().setBecadoci(becadoMedicamentos.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BecadoMedicamentos persistentBecadoMedicamentos = em.find(BecadoMedicamentos.class, becadoMedicamentos.getBecadoMedicamentosPK());
            Medicamentos medicamentosOld = persistentBecadoMedicamentos.getMedicamentos();
            Medicamentos medicamentosNew = becadoMedicamentos.getMedicamentos();
            Becado becadoOld = persistentBecadoMedicamentos.getBecado();
            Becado becadoNew = becadoMedicamentos.getBecado();
            if (medicamentosNew != null) {
                medicamentosNew = em.getReference(medicamentosNew.getClass(), medicamentosNew.getId());
                becadoMedicamentos.setMedicamentos(medicamentosNew);
            }
            if (becadoNew != null) {
                becadoNew = em.getReference(becadoNew.getClass(), becadoNew.getCi());
                becadoMedicamentos.setBecado(becadoNew);
            }
            becadoMedicamentos = em.merge(becadoMedicamentos);
            if (medicamentosOld != null && !medicamentosOld.equals(medicamentosNew)) {
                medicamentosOld.getBecadoMedicamentosList().remove(becadoMedicamentos);
                medicamentosOld = em.merge(medicamentosOld);
            }
            if (medicamentosNew != null && !medicamentosNew.equals(medicamentosOld)) {
                medicamentosNew.getBecadoMedicamentosList().add(becadoMedicamentos);
                medicamentosNew = em.merge(medicamentosNew);
            }
            if (becadoOld != null && !becadoOld.equals(becadoNew)) {
                becadoOld.getBecadoMedicamentosList().remove(becadoMedicamentos);
                becadoOld = em.merge(becadoOld);
            }
            if (becadoNew != null && !becadoNew.equals(becadoOld)) {
                becadoNew.getBecadoMedicamentosList().add(becadoMedicamentos);
                becadoNew = em.merge(becadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BecadoMedicamentosPK id = becadoMedicamentos.getBecadoMedicamentosPK();
                if (findBecadoMedicamentos(id) == null) {
                    throw new NonexistentEntityException("The becadoMedicamentos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BecadoMedicamentosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BecadoMedicamentos becadoMedicamentos;
            try {
                becadoMedicamentos = em.getReference(BecadoMedicamentos.class, id);
                becadoMedicamentos.getBecadoMedicamentosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The becadoMedicamentos with id " + id + " no longer exists.", enfe);
            }
            Medicamentos medicamentos = becadoMedicamentos.getMedicamentos();
            if (medicamentos != null) {
                medicamentos.getBecadoMedicamentosList().remove(becadoMedicamentos);
                medicamentos = em.merge(medicamentos);
            }
            Becado becado = becadoMedicamentos.getBecado();
            if (becado != null) {
                becado.getBecadoMedicamentosList().remove(becadoMedicamentos);
                becado = em.merge(becado);
            }
            em.remove(becadoMedicamentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BecadoMedicamentos> findBecadoMedicamentosEntities() {
        return findBecadoMedicamentosEntities(true, -1, -1);
    }

    public List<BecadoMedicamentos> findBecadoMedicamentosEntities(int maxResults, int firstResult) {
        return findBecadoMedicamentosEntities(false, maxResults, firstResult);
    }

    private List<BecadoMedicamentos> findBecadoMedicamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BecadoMedicamentos.class));
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

    public BecadoMedicamentos findBecadoMedicamentos(BecadoMedicamentosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BecadoMedicamentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getBecadoMedicamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BecadoMedicamentos> rt = cq.from(BecadoMedicamentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
