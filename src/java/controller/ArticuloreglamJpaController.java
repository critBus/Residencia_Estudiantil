/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entities.Articuloreglam;
import entities.ArticuloreglamPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Capituloreglam;
import entities.Incisoreglam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class ArticuloreglamJpaController implements Serializable {

    public ArticuloreglamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articuloreglam articuloreglam) throws PreexistingEntityException, Exception {
        if (articuloreglam.getArticuloreglamPK() == null) {
            articuloreglam.setArticuloreglamPK(new ArticuloreglamPK());
        }
        if (articuloreglam.getIncisoreglamList() == null) {
            articuloreglam.setIncisoreglamList(new ArrayList<Incisoreglam>());
        }
        articuloreglam.getArticuloreglamPK().setCapituloid(articuloreglam.getCapituloreglam().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Capituloreglam capituloreglam = articuloreglam.getCapituloreglam();
            if (capituloreglam != null) {
                capituloreglam = em.getReference(capituloreglam.getClass(), capituloreglam.getId());
                articuloreglam.setCapituloreglam(capituloreglam);
            }
            List<Incisoreglam> attachedIncisoreglamList = new ArrayList<Incisoreglam>();
            for (Incisoreglam incisoreglamListIncisoreglamToAttach : articuloreglam.getIncisoreglamList()) {
                incisoreglamListIncisoreglamToAttach = em.getReference(incisoreglamListIncisoreglamToAttach.getClass(), incisoreglamListIncisoreglamToAttach.getIncisoreglamPK());
                attachedIncisoreglamList.add(incisoreglamListIncisoreglamToAttach);
            }
            articuloreglam.setIncisoreglamList(attachedIncisoreglamList);
            em.persist(articuloreglam);
            if (capituloreglam != null) {
                capituloreglam.getArticuloreglamList().add(articuloreglam);
                capituloreglam = em.merge(capituloreglam);
            }
            for (Incisoreglam incisoreglamListIncisoreglam : articuloreglam.getIncisoreglamList()) {
                Articuloreglam oldArticuloreglamOfIncisoreglamListIncisoreglam = incisoreglamListIncisoreglam.getArticuloreglam();
                incisoreglamListIncisoreglam.setArticuloreglam(articuloreglam);
                incisoreglamListIncisoreglam = em.merge(incisoreglamListIncisoreglam);
                if (oldArticuloreglamOfIncisoreglamListIncisoreglam != null) {
                    oldArticuloreglamOfIncisoreglamListIncisoreglam.getIncisoreglamList().remove(incisoreglamListIncisoreglam);
                    oldArticuloreglamOfIncisoreglamListIncisoreglam = em.merge(oldArticuloreglamOfIncisoreglamListIncisoreglam);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArticuloreglam(articuloreglam.getArticuloreglamPK()) != null) {
                throw new PreexistingEntityException("Articuloreglam " + articuloreglam + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articuloreglam articuloreglam) throws IllegalOrphanException, NonexistentEntityException, Exception {
        articuloreglam.getArticuloreglamPK().setCapituloid(articuloreglam.getCapituloreglam().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articuloreglam persistentArticuloreglam = em.find(Articuloreglam.class, articuloreglam.getArticuloreglamPK());
            Capituloreglam capituloreglamOld = persistentArticuloreglam.getCapituloreglam();
            Capituloreglam capituloreglamNew = articuloreglam.getCapituloreglam();
            List<Incisoreglam> incisoreglamListOld = persistentArticuloreglam.getIncisoreglamList();
            List<Incisoreglam> incisoreglamListNew = articuloreglam.getIncisoreglamList();
            List<String> illegalOrphanMessages = null;
            for (Incisoreglam incisoreglamListOldIncisoreglam : incisoreglamListOld) {
                if (!incisoreglamListNew.contains(incisoreglamListOldIncisoreglam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Incisoreglam " + incisoreglamListOldIncisoreglam + " since its articuloreglam field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (capituloreglamNew != null) {
                capituloreglamNew = em.getReference(capituloreglamNew.getClass(), capituloreglamNew.getId());
                articuloreglam.setCapituloreglam(capituloreglamNew);
            }
            List<Incisoreglam> attachedIncisoreglamListNew = new ArrayList<Incisoreglam>();
            for (Incisoreglam incisoreglamListNewIncisoreglamToAttach : incisoreglamListNew) {
                incisoreglamListNewIncisoreglamToAttach = em.getReference(incisoreglamListNewIncisoreglamToAttach.getClass(), incisoreglamListNewIncisoreglamToAttach.getIncisoreglamPK());
                attachedIncisoreglamListNew.add(incisoreglamListNewIncisoreglamToAttach);
            }
            incisoreglamListNew = attachedIncisoreglamListNew;
            articuloreglam.setIncisoreglamList(incisoreglamListNew);
            articuloreglam = em.merge(articuloreglam);
            if (capituloreglamOld != null && !capituloreglamOld.equals(capituloreglamNew)) {
                capituloreglamOld.getArticuloreglamList().remove(articuloreglam);
                capituloreglamOld = em.merge(capituloreglamOld);
            }
            if (capituloreglamNew != null && !capituloreglamNew.equals(capituloreglamOld)) {
                capituloreglamNew.getArticuloreglamList().add(articuloreglam);
                capituloreglamNew = em.merge(capituloreglamNew);
            }
            for (Incisoreglam incisoreglamListNewIncisoreglam : incisoreglamListNew) {
                if (!incisoreglamListOld.contains(incisoreglamListNewIncisoreglam)) {
                    Articuloreglam oldArticuloreglamOfIncisoreglamListNewIncisoreglam = incisoreglamListNewIncisoreglam.getArticuloreglam();
                    incisoreglamListNewIncisoreglam.setArticuloreglam(articuloreglam);
                    incisoreglamListNewIncisoreglam = em.merge(incisoreglamListNewIncisoreglam);
                    if (oldArticuloreglamOfIncisoreglamListNewIncisoreglam != null && !oldArticuloreglamOfIncisoreglamListNewIncisoreglam.equals(articuloreglam)) {
                        oldArticuloreglamOfIncisoreglamListNewIncisoreglam.getIncisoreglamList().remove(incisoreglamListNewIncisoreglam);
                        oldArticuloreglamOfIncisoreglamListNewIncisoreglam = em.merge(oldArticuloreglamOfIncisoreglamListNewIncisoreglam);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ArticuloreglamPK id = articuloreglam.getArticuloreglamPK();
                if (findArticuloreglam(id) == null) {
                    throw new NonexistentEntityException("The articuloreglam with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ArticuloreglamPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articuloreglam articuloreglam;
            try {
                articuloreglam = em.getReference(Articuloreglam.class, id);
                articuloreglam.getArticuloreglamPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articuloreglam with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Incisoreglam> incisoreglamListOrphanCheck = articuloreglam.getIncisoreglamList();
            for (Incisoreglam incisoreglamListOrphanCheckIncisoreglam : incisoreglamListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articuloreglam (" + articuloreglam + ") cannot be destroyed since the Incisoreglam " + incisoreglamListOrphanCheckIncisoreglam + " in its incisoreglamList field has a non-nullable articuloreglam field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Capituloreglam capituloreglam = articuloreglam.getCapituloreglam();
            if (capituloreglam != null) {
                capituloreglam.getArticuloreglamList().remove(articuloreglam);
                capituloreglam = em.merge(capituloreglam);
            }
            em.remove(articuloreglam);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articuloreglam> findArticuloreglamEntities() {
        return findArticuloreglamEntities(true, -1, -1);
    }

    public List<Articuloreglam> findArticuloreglamEntities(int maxResults, int firstResult) {
        return findArticuloreglamEntities(false, maxResults, firstResult);
    }

    private List<Articuloreglam> findArticuloreglamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articuloreglam.class));
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

    public Articuloreglam findArticuloreglam(ArticuloreglamPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articuloreglam.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloreglamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articuloreglam> rt = cq.from(Articuloreglam.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
