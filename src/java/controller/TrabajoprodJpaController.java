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
import entities.Piso;
import entities.Becado;
import entities.Trabajoprod;
import entities.TrabajoprodPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class TrabajoprodJpaController implements Serializable {

    public TrabajoprodJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajoprod trabajoprod) throws PreexistingEntityException, Exception {
        if (trabajoprod.getTrabajoprodPK() == null) {
            trabajoprod.setTrabajoprodPK(new TrabajoprodPK());
        }
        if (trabajoprod.getBecadoList() == null) {
            trabajoprod.setBecadoList(new ArrayList<Becado>());
        }
        trabajoprod.getTrabajoprodPK().setPisoid(trabajoprod.getPiso().getPisoPK().getId());
        trabajoprod.getTrabajoprodPK().setEdificioid(trabajoprod.getPiso().getPisoPK().getEdificioid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Piso piso = trabajoprod.getPiso();
            if (piso != null) {
                piso = em.getReference(piso.getClass(), piso.getPisoPK());
                trabajoprod.setPiso(piso);
            }
            List<Becado> attachedBecadoList = new ArrayList<Becado>();
            for (Becado becadoListBecadoToAttach : trabajoprod.getBecadoList()) {
                becadoListBecadoToAttach = em.getReference(becadoListBecadoToAttach.getClass(), becadoListBecadoToAttach.getCi());
                attachedBecadoList.add(becadoListBecadoToAttach);
            }
            trabajoprod.setBecadoList(attachedBecadoList);
            em.persist(trabajoprod);
            if (piso != null) {
                piso.getTrabajoprodList().add(trabajoprod);
                piso = em.merge(piso);
            }
            for (Becado becadoListBecado : trabajoprod.getBecadoList()) {
                becadoListBecado.getTrabajoprodList().add(trabajoprod);
                becadoListBecado = em.merge(becadoListBecado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrabajoprod(trabajoprod.getTrabajoprodPK()) != null) {
                throw new PreexistingEntityException("Trabajoprod " + trabajoprod + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trabajoprod trabajoprod) throws NonexistentEntityException, Exception {
        trabajoprod.getTrabajoprodPK().setPisoid(trabajoprod.getPiso().getPisoPK().getId());
        trabajoprod.getTrabajoprodPK().setEdificioid(trabajoprod.getPiso().getPisoPK().getEdificioid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajoprod persistentTrabajoprod = em.find(Trabajoprod.class, trabajoprod.getTrabajoprodPK());
            Piso pisoOld = persistentTrabajoprod.getPiso();
            Piso pisoNew = trabajoprod.getPiso();
            List<Becado> becadoListOld = persistentTrabajoprod.getBecadoList();
            List<Becado> becadoListNew = trabajoprod.getBecadoList();
            if (pisoNew != null) {
                pisoNew = em.getReference(pisoNew.getClass(), pisoNew.getPisoPK());
                trabajoprod.setPiso(pisoNew);
            }
            List<Becado> attachedBecadoListNew = new ArrayList<Becado>();
            for (Becado becadoListNewBecadoToAttach : becadoListNew) {
                becadoListNewBecadoToAttach = em.getReference(becadoListNewBecadoToAttach.getClass(), becadoListNewBecadoToAttach.getCi());
                attachedBecadoListNew.add(becadoListNewBecadoToAttach);
            }
            becadoListNew = attachedBecadoListNew;
            trabajoprod.setBecadoList(becadoListNew);
            trabajoprod = em.merge(trabajoprod);
            if (pisoOld != null && !pisoOld.equals(pisoNew)) {
                pisoOld.getTrabajoprodList().remove(trabajoprod);
                pisoOld = em.merge(pisoOld);
            }
            if (pisoNew != null && !pisoNew.equals(pisoOld)) {
                pisoNew.getTrabajoprodList().add(trabajoprod);
                pisoNew = em.merge(pisoNew);
            }
            for (Becado becadoListOldBecado : becadoListOld) {
                if (!becadoListNew.contains(becadoListOldBecado)) {
                    becadoListOldBecado.getTrabajoprodList().remove(trabajoprod);
                    becadoListOldBecado = em.merge(becadoListOldBecado);
                }
            }
            for (Becado becadoListNewBecado : becadoListNew) {
                if (!becadoListOld.contains(becadoListNewBecado)) {
                    becadoListNewBecado.getTrabajoprodList().add(trabajoprod);
                    becadoListNewBecado = em.merge(becadoListNewBecado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TrabajoprodPK id = trabajoprod.getTrabajoprodPK();
                if (findTrabajoprod(id) == null) {
                    throw new NonexistentEntityException("The trabajoprod with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TrabajoprodPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajoprod trabajoprod;
            try {
                trabajoprod = em.getReference(Trabajoprod.class, id);
                trabajoprod.getTrabajoprodPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajoprod with id " + id + " no longer exists.", enfe);
            }
            Piso piso = trabajoprod.getPiso();
            if (piso != null) {
                piso.getTrabajoprodList().remove(trabajoprod);
                piso = em.merge(piso);
            }
            List<Becado> becadoList = trabajoprod.getBecadoList();
            for (Becado becadoListBecado : becadoList) {
                becadoListBecado.getTrabajoprodList().remove(trabajoprod);
                becadoListBecado = em.merge(becadoListBecado);
            }
            em.remove(trabajoprod);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trabajoprod> findTrabajoprodEntities() {
        return findTrabajoprodEntities(true, -1, -1);
    }

    public List<Trabajoprod> findTrabajoprodEntities(int maxResults, int firstResult) {
        return findTrabajoprodEntities(false, maxResults, firstResult);
    }

    private List<Trabajoprod> findTrabajoprodEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajoprod.class));
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

    public Trabajoprod findTrabajoprod(TrabajoprodPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajoprod.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajoprodCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajoprod> rt = cq.from(Trabajoprod.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
