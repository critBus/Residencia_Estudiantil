/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Becado;
import entities.Medicamentos;
import java.util.ArrayList;
import java.util.List;
import entities.PacatendMedicamentos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class MedicamentosJpaController implements Serializable {

    public MedicamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicamentos medicamentos) {
        if (medicamentos.getBecadoList() == null) {
            medicamentos.setBecadoList(new ArrayList<Becado>());
        }
        if (medicamentos.getPacatendMedicamentosList() == null) {
            medicamentos.setPacatendMedicamentosList(new ArrayList<PacatendMedicamentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Becado> attachedBecadoList = new ArrayList<Becado>();
            for (Becado becadoListBecadoToAttach : medicamentos.getBecadoList()) {
                becadoListBecadoToAttach = em.getReference(becadoListBecadoToAttach.getClass(), becadoListBecadoToAttach.getCi());
                attachedBecadoList.add(becadoListBecadoToAttach);
            }
            medicamentos.setBecadoList(attachedBecadoList);
            List<PacatendMedicamentos> attachedPacatendMedicamentosList = new ArrayList<PacatendMedicamentos>();
            for (PacatendMedicamentos pacatendMedicamentosListPacatendMedicamentosToAttach : medicamentos.getPacatendMedicamentosList()) {
                pacatendMedicamentosListPacatendMedicamentosToAttach = em.getReference(pacatendMedicamentosListPacatendMedicamentosToAttach.getClass(), pacatendMedicamentosListPacatendMedicamentosToAttach.getPacatendMedicamentosPK());
                attachedPacatendMedicamentosList.add(pacatendMedicamentosListPacatendMedicamentosToAttach);
            }
            medicamentos.setPacatendMedicamentosList(attachedPacatendMedicamentosList);
            em.persist(medicamentos);
            for (Becado becadoListBecado : medicamentos.getBecadoList()) {
                becadoListBecado.getMedicamentosList().add(medicamentos);
                becadoListBecado = em.merge(becadoListBecado);
            }
            for (PacatendMedicamentos pacatendMedicamentosListPacatendMedicamentos : medicamentos.getPacatendMedicamentosList()) {
                Medicamentos oldMedicamentosOfPacatendMedicamentosListPacatendMedicamentos = pacatendMedicamentosListPacatendMedicamentos.getMedicamentos();
                pacatendMedicamentosListPacatendMedicamentos.setMedicamentos(medicamentos);
                pacatendMedicamentosListPacatendMedicamentos = em.merge(pacatendMedicamentosListPacatendMedicamentos);
                if (oldMedicamentosOfPacatendMedicamentosListPacatendMedicamentos != null) {
                    oldMedicamentosOfPacatendMedicamentosListPacatendMedicamentos.getPacatendMedicamentosList().remove(pacatendMedicamentosListPacatendMedicamentos);
                    oldMedicamentosOfPacatendMedicamentosListPacatendMedicamentos = em.merge(oldMedicamentosOfPacatendMedicamentosListPacatendMedicamentos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicamentos medicamentos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamentos persistentMedicamentos = em.find(Medicamentos.class, medicamentos.getId());
            List<Becado> becadoListOld = persistentMedicamentos.getBecadoList();
            List<Becado> becadoListNew = medicamentos.getBecadoList();
            List<PacatendMedicamentos> pacatendMedicamentosListOld = persistentMedicamentos.getPacatendMedicamentosList();
            List<PacatendMedicamentos> pacatendMedicamentosListNew = medicamentos.getPacatendMedicamentosList();
            List<String> illegalOrphanMessages = null;
            for (PacatendMedicamentos pacatendMedicamentosListOldPacatendMedicamentos : pacatendMedicamentosListOld) {
                if (!pacatendMedicamentosListNew.contains(pacatendMedicamentosListOldPacatendMedicamentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PacatendMedicamentos " + pacatendMedicamentosListOldPacatendMedicamentos + " since its medicamentos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Becado> attachedBecadoListNew = new ArrayList<Becado>();
            for (Becado becadoListNewBecadoToAttach : becadoListNew) {
                becadoListNewBecadoToAttach = em.getReference(becadoListNewBecadoToAttach.getClass(), becadoListNewBecadoToAttach.getCi());
                attachedBecadoListNew.add(becadoListNewBecadoToAttach);
            }
            becadoListNew = attachedBecadoListNew;
            medicamentos.setBecadoList(becadoListNew);
            List<PacatendMedicamentos> attachedPacatendMedicamentosListNew = new ArrayList<PacatendMedicamentos>();
            for (PacatendMedicamentos pacatendMedicamentosListNewPacatendMedicamentosToAttach : pacatendMedicamentosListNew) {
                pacatendMedicamentosListNewPacatendMedicamentosToAttach = em.getReference(pacatendMedicamentosListNewPacatendMedicamentosToAttach.getClass(), pacatendMedicamentosListNewPacatendMedicamentosToAttach.getPacatendMedicamentosPK());
                attachedPacatendMedicamentosListNew.add(pacatendMedicamentosListNewPacatendMedicamentosToAttach);
            }
            pacatendMedicamentosListNew = attachedPacatendMedicamentosListNew;
            medicamentos.setPacatendMedicamentosList(pacatendMedicamentosListNew);
            medicamentos = em.merge(medicamentos);
            for (Becado becadoListOldBecado : becadoListOld) {
                if (!becadoListNew.contains(becadoListOldBecado)) {
                    becadoListOldBecado.getMedicamentosList().remove(medicamentos);
                    becadoListOldBecado = em.merge(becadoListOldBecado);
                }
            }
            for (Becado becadoListNewBecado : becadoListNew) {
                if (!becadoListOld.contains(becadoListNewBecado)) {
                    becadoListNewBecado.getMedicamentosList().add(medicamentos);
                    becadoListNewBecado = em.merge(becadoListNewBecado);
                }
            }
            for (PacatendMedicamentos pacatendMedicamentosListNewPacatendMedicamentos : pacatendMedicamentosListNew) {
                if (!pacatendMedicamentosListOld.contains(pacatendMedicamentosListNewPacatendMedicamentos)) {
                    Medicamentos oldMedicamentosOfPacatendMedicamentosListNewPacatendMedicamentos = pacatendMedicamentosListNewPacatendMedicamentos.getMedicamentos();
                    pacatendMedicamentosListNewPacatendMedicamentos.setMedicamentos(medicamentos);
                    pacatendMedicamentosListNewPacatendMedicamentos = em.merge(pacatendMedicamentosListNewPacatendMedicamentos);
                    if (oldMedicamentosOfPacatendMedicamentosListNewPacatendMedicamentos != null && !oldMedicamentosOfPacatendMedicamentosListNewPacatendMedicamentos.equals(medicamentos)) {
                        oldMedicamentosOfPacatendMedicamentosListNewPacatendMedicamentos.getPacatendMedicamentosList().remove(pacatendMedicamentosListNewPacatendMedicamentos);
                        oldMedicamentosOfPacatendMedicamentosListNewPacatendMedicamentos = em.merge(oldMedicamentosOfPacatendMedicamentosListNewPacatendMedicamentos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = medicamentos.getId();
                if (findMedicamentos(id) == null) {
                    throw new NonexistentEntityException("The medicamentos with id " + id + " no longer exists.");
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
            Medicamentos medicamentos;
            try {
                medicamentos = em.getReference(Medicamentos.class, id);
                medicamentos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicamentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PacatendMedicamentos> pacatendMedicamentosListOrphanCheck = medicamentos.getPacatendMedicamentosList();
            for (PacatendMedicamentos pacatendMedicamentosListOrphanCheckPacatendMedicamentos : pacatendMedicamentosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicamentos (" + medicamentos + ") cannot be destroyed since the PacatendMedicamentos " + pacatendMedicamentosListOrphanCheckPacatendMedicamentos + " in its pacatendMedicamentosList field has a non-nullable medicamentos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Becado> becadoList = medicamentos.getBecadoList();
            for (Becado becadoListBecado : becadoList) {
                becadoListBecado.getMedicamentosList().remove(medicamentos);
                becadoListBecado = em.merge(becadoListBecado);
            }
            em.remove(medicamentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medicamentos> findMedicamentosEntities() {
        return findMedicamentosEntities(true, -1, -1);
    }

    public List<Medicamentos> findMedicamentosEntities(int maxResults, int firstResult) {
        return findMedicamentosEntities(false, maxResults, firstResult);
    }

    private List<Medicamentos> findMedicamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicamentos.class));
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

    public Medicamentos findMedicamentos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicamentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicamentos> rt = cq.from(Medicamentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
