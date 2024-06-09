/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Becado;
import entities.Enfermedades;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EnfermedadesJpaController implements Serializable {

    public EnfermedadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enfermedades enfermedades) {
        if (enfermedades.getBecadoList() == null) {
            enfermedades.setBecadoList(new ArrayList<Becado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Becado> attachedBecadoList = new ArrayList<Becado>();
            for (Becado becadoListBecadoToAttach : enfermedades.getBecadoList()) {
                becadoListBecadoToAttach = em.getReference(becadoListBecadoToAttach.getClass(), becadoListBecadoToAttach.getCi());
                attachedBecadoList.add(becadoListBecadoToAttach);
            }
            enfermedades.setBecadoList(attachedBecadoList);
            em.persist(enfermedades);
            for (Becado becadoListBecado : enfermedades.getBecadoList()) {
                becadoListBecado.getEnfermedadesList().add(enfermedades);
                becadoListBecado = em.merge(becadoListBecado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enfermedades enfermedades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermedades persistentEnfermedades = em.find(Enfermedades.class, enfermedades.getId());
            List<Becado> becadoListOld = persistentEnfermedades.getBecadoList();
            List<Becado> becadoListNew = enfermedades.getBecadoList();
            List<Becado> attachedBecadoListNew = new ArrayList<Becado>();
            for (Becado becadoListNewBecadoToAttach : becadoListNew) {
                becadoListNewBecadoToAttach = em.getReference(becadoListNewBecadoToAttach.getClass(), becadoListNewBecadoToAttach.getCi());
                attachedBecadoListNew.add(becadoListNewBecadoToAttach);
            }
            becadoListNew = attachedBecadoListNew;
            enfermedades.setBecadoList(becadoListNew);
            enfermedades = em.merge(enfermedades);
            for (Becado becadoListOldBecado : becadoListOld) {
                if (!becadoListNew.contains(becadoListOldBecado)) {
                    becadoListOldBecado.getEnfermedadesList().remove(enfermedades);
                    becadoListOldBecado = em.merge(becadoListOldBecado);
                }
            }
            for (Becado becadoListNewBecado : becadoListNew) {
                if (!becadoListOld.contains(becadoListNewBecado)) {
                    becadoListNewBecado.getEnfermedadesList().add(enfermedades);
                    becadoListNewBecado = em.merge(becadoListNewBecado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enfermedades.getId();
                if (findEnfermedades(id) == null) {
                    throw new NonexistentEntityException("The enfermedades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermedades enfermedades;
            try {
                enfermedades = em.getReference(Enfermedades.class, id);
                enfermedades.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enfermedades with id " + id + " no longer exists.", enfe);
            }
            List<Becado> becadoList = enfermedades.getBecadoList();
            for (Becado becadoListBecado : becadoList) {
                becadoListBecado.getEnfermedadesList().remove(enfermedades);
                becadoListBecado = em.merge(becadoListBecado);
            }
            em.remove(enfermedades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enfermedades> findEnfermedadesEntities() {
        return findEnfermedadesEntities(true, -1, -1);
    }

    public List<Enfermedades> findEnfermedadesEntities(int maxResults, int firstResult) {
        return findEnfermedadesEntities(false, maxResults, firstResult);
    }

    private List<Enfermedades> findEnfermedadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enfermedades.class));
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

    public Enfermedades findEnfermedades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enfermedades.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnfermedadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enfermedades> rt = cq.from(Enfermedades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
