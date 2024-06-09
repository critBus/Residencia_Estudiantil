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
import entities.Articuloreglam;
import entities.Capituloreglam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class CapituloreglamJpaController implements Serializable {

    public CapituloreglamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Capituloreglam capituloreglam) throws PreexistingEntityException, Exception {
        if (capituloreglam.getArticuloreglamList() == null) {
            capituloreglam.setArticuloreglamList(new ArrayList<Articuloreglam>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Articuloreglam> attachedArticuloreglamList = new ArrayList<Articuloreglam>();
            for (Articuloreglam articuloreglamListArticuloreglamToAttach : capituloreglam.getArticuloreglamList()) {
                articuloreglamListArticuloreglamToAttach = em.getReference(articuloreglamListArticuloreglamToAttach.getClass(), articuloreglamListArticuloreglamToAttach.getArticuloreglamPK());
                attachedArticuloreglamList.add(articuloreglamListArticuloreglamToAttach);
            }
            capituloreglam.setArticuloreglamList(attachedArticuloreglamList);
            em.persist(capituloreglam);
            for (Articuloreglam articuloreglamListArticuloreglam : capituloreglam.getArticuloreglamList()) {
                Capituloreglam oldCapituloreglamOfArticuloreglamListArticuloreglam = articuloreglamListArticuloreglam.getCapituloreglam();
                articuloreglamListArticuloreglam.setCapituloreglam(capituloreglam);
                articuloreglamListArticuloreglam = em.merge(articuloreglamListArticuloreglam);
                if (oldCapituloreglamOfArticuloreglamListArticuloreglam != null) {
                    oldCapituloreglamOfArticuloreglamListArticuloreglam.getArticuloreglamList().remove(articuloreglamListArticuloreglam);
                    oldCapituloreglamOfArticuloreglamListArticuloreglam = em.merge(oldCapituloreglamOfArticuloreglamListArticuloreglam);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCapituloreglam(capituloreglam.getId()) != null) {
                throw new PreexistingEntityException("Capituloreglam " + capituloreglam + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Capituloreglam capituloreglam) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Capituloreglam persistentCapituloreglam = em.find(Capituloreglam.class, capituloreglam.getId());
            List<Articuloreglam> articuloreglamListOld = persistentCapituloreglam.getArticuloreglamList();
            List<Articuloreglam> articuloreglamListNew = capituloreglam.getArticuloreglamList();
            List<String> illegalOrphanMessages = null;
            for (Articuloreglam articuloreglamListOldArticuloreglam : articuloreglamListOld) {
                if (!articuloreglamListNew.contains(articuloreglamListOldArticuloreglam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articuloreglam " + articuloreglamListOldArticuloreglam + " since its capituloreglam field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Articuloreglam> attachedArticuloreglamListNew = new ArrayList<Articuloreglam>();
            for (Articuloreglam articuloreglamListNewArticuloreglamToAttach : articuloreglamListNew) {
                articuloreglamListNewArticuloreglamToAttach = em.getReference(articuloreglamListNewArticuloreglamToAttach.getClass(), articuloreglamListNewArticuloreglamToAttach.getArticuloreglamPK());
                attachedArticuloreglamListNew.add(articuloreglamListNewArticuloreglamToAttach);
            }
            articuloreglamListNew = attachedArticuloreglamListNew;
            capituloreglam.setArticuloreglamList(articuloreglamListNew);
            capituloreglam = em.merge(capituloreglam);
            for (Articuloreglam articuloreglamListNewArticuloreglam : articuloreglamListNew) {
                if (!articuloreglamListOld.contains(articuloreglamListNewArticuloreglam)) {
                    Capituloreglam oldCapituloreglamOfArticuloreglamListNewArticuloreglam = articuloreglamListNewArticuloreglam.getCapituloreglam();
                    articuloreglamListNewArticuloreglam.setCapituloreglam(capituloreglam);
                    articuloreglamListNewArticuloreglam = em.merge(articuloreglamListNewArticuloreglam);
                    if (oldCapituloreglamOfArticuloreglamListNewArticuloreglam != null && !oldCapituloreglamOfArticuloreglamListNewArticuloreglam.equals(capituloreglam)) {
                        oldCapituloreglamOfArticuloreglamListNewArticuloreglam.getArticuloreglamList().remove(articuloreglamListNewArticuloreglam);
                        oldCapituloreglamOfArticuloreglamListNewArticuloreglam = em.merge(oldCapituloreglamOfArticuloreglamListNewArticuloreglam);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = capituloreglam.getId();
                if (findCapituloreglam(id) == null) {
                    throw new NonexistentEntityException("The capituloreglam with id " + id + " no longer exists.");
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
            Capituloreglam capituloreglam;
            try {
                capituloreglam = em.getReference(Capituloreglam.class, id);
                capituloreglam.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capituloreglam with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Articuloreglam> articuloreglamListOrphanCheck = capituloreglam.getArticuloreglamList();
            for (Articuloreglam articuloreglamListOrphanCheckArticuloreglam : articuloreglamListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capituloreglam (" + capituloreglam + ") cannot be destroyed since the Articuloreglam " + articuloreglamListOrphanCheckArticuloreglam + " in its articuloreglamList field has a non-nullable capituloreglam field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(capituloreglam);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Capituloreglam> findCapituloreglamEntities() {
        return findCapituloreglamEntities(true, -1, -1);
    }

    public List<Capituloreglam> findCapituloreglamEntities(int maxResults, int firstResult) {
        return findCapituloreglamEntities(false, maxResults, firstResult);
    }

    private List<Capituloreglam> findCapituloreglamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Capituloreglam.class));
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

    public Capituloreglam findCapituloreglam(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Capituloreglam.class, id);
        } finally {
            em.close();
        }
    }

    public int getCapituloreglamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Capituloreglam> rt = cq.from(Capituloreglam.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
