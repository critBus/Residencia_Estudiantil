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
import entities.Incisoreglam;
import entities.IncisoreglamPK;
import entities.Sanciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class IncisoreglamJpaController implements Serializable {

    public IncisoreglamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Incisoreglam incisoreglam) throws PreexistingEntityException, Exception {
        if (incisoreglam.getIncisoreglamPK() == null) {
            incisoreglam.setIncisoreglamPK(new IncisoreglamPK());
        }
        if (incisoreglam.getSancionesList() == null) {
            incisoreglam.setSancionesList(new ArrayList<Sanciones>());
        }
        incisoreglam.getIncisoreglamPK().setCapituloid(incisoreglam.getArticuloreglam().getArticuloreglamPK().getCapituloid());
        incisoreglam.getIncisoreglamPK().setArticuloid(incisoreglam.getArticuloreglam().getArticuloreglamPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articuloreglam articuloreglam = incisoreglam.getArticuloreglam();
            if (articuloreglam != null) {
                articuloreglam = em.getReference(articuloreglam.getClass(), articuloreglam.getArticuloreglamPK());
                incisoreglam.setArticuloreglam(articuloreglam);
            }
            List<Sanciones> attachedSancionesList = new ArrayList<Sanciones>();
            for (Sanciones sancionesListSancionesToAttach : incisoreglam.getSancionesList()) {
                sancionesListSancionesToAttach = em.getReference(sancionesListSancionesToAttach.getClass(), sancionesListSancionesToAttach.getSancionesPK());
                attachedSancionesList.add(sancionesListSancionesToAttach);
            }
            incisoreglam.setSancionesList(attachedSancionesList);
            em.persist(incisoreglam);
            if (articuloreglam != null) {
                articuloreglam.getIncisoreglamList().add(incisoreglam);
                articuloreglam = em.merge(articuloreglam);
            }
            for (Sanciones sancionesListSanciones : incisoreglam.getSancionesList()) {
                Incisoreglam oldIncisoreglamOfSancionesListSanciones = sancionesListSanciones.getIncisoreglam();
                sancionesListSanciones.setIncisoreglam(incisoreglam);
                sancionesListSanciones = em.merge(sancionesListSanciones);
                if (oldIncisoreglamOfSancionesListSanciones != null) {
                    oldIncisoreglamOfSancionesListSanciones.getSancionesList().remove(sancionesListSanciones);
                    oldIncisoreglamOfSancionesListSanciones = em.merge(oldIncisoreglamOfSancionesListSanciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIncisoreglam(incisoreglam.getIncisoreglamPK()) != null) {
                throw new PreexistingEntityException("Incisoreglam " + incisoreglam + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Incisoreglam incisoreglam) throws IllegalOrphanException, NonexistentEntityException, Exception {
        incisoreglam.getIncisoreglamPK().setCapituloid(incisoreglam.getArticuloreglam().getArticuloreglamPK().getCapituloid());
        incisoreglam.getIncisoreglamPK().setArticuloid(incisoreglam.getArticuloreglam().getArticuloreglamPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incisoreglam persistentIncisoreglam = em.find(Incisoreglam.class, incisoreglam.getIncisoreglamPK());
            Articuloreglam articuloreglamOld = persistentIncisoreglam.getArticuloreglam();
            Articuloreglam articuloreglamNew = incisoreglam.getArticuloreglam();
            List<Sanciones> sancionesListOld = persistentIncisoreglam.getSancionesList();
            List<Sanciones> sancionesListNew = incisoreglam.getSancionesList();
            List<String> illegalOrphanMessages = null;
            for (Sanciones sancionesListOldSanciones : sancionesListOld) {
                if (!sancionesListNew.contains(sancionesListOldSanciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sanciones " + sancionesListOldSanciones + " since its incisoreglam field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (articuloreglamNew != null) {
                articuloreglamNew = em.getReference(articuloreglamNew.getClass(), articuloreglamNew.getArticuloreglamPK());
                incisoreglam.setArticuloreglam(articuloreglamNew);
            }
            List<Sanciones> attachedSancionesListNew = new ArrayList<Sanciones>();
            for (Sanciones sancionesListNewSancionesToAttach : sancionesListNew) {
                sancionesListNewSancionesToAttach = em.getReference(sancionesListNewSancionesToAttach.getClass(), sancionesListNewSancionesToAttach.getSancionesPK());
                attachedSancionesListNew.add(sancionesListNewSancionesToAttach);
            }
            sancionesListNew = attachedSancionesListNew;
            incisoreglam.setSancionesList(sancionesListNew);
            incisoreglam = em.merge(incisoreglam);
            if (articuloreglamOld != null && !articuloreglamOld.equals(articuloreglamNew)) {
                articuloreglamOld.getIncisoreglamList().remove(incisoreglam);
                articuloreglamOld = em.merge(articuloreglamOld);
            }
            if (articuloreglamNew != null && !articuloreglamNew.equals(articuloreglamOld)) {
                articuloreglamNew.getIncisoreglamList().add(incisoreglam);
                articuloreglamNew = em.merge(articuloreglamNew);
            }
            for (Sanciones sancionesListNewSanciones : sancionesListNew) {
                if (!sancionesListOld.contains(sancionesListNewSanciones)) {
                    Incisoreglam oldIncisoreglamOfSancionesListNewSanciones = sancionesListNewSanciones.getIncisoreglam();
                    sancionesListNewSanciones.setIncisoreglam(incisoreglam);
                    sancionesListNewSanciones = em.merge(sancionesListNewSanciones);
                    if (oldIncisoreglamOfSancionesListNewSanciones != null && !oldIncisoreglamOfSancionesListNewSanciones.equals(incisoreglam)) {
                        oldIncisoreglamOfSancionesListNewSanciones.getSancionesList().remove(sancionesListNewSanciones);
                        oldIncisoreglamOfSancionesListNewSanciones = em.merge(oldIncisoreglamOfSancionesListNewSanciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IncisoreglamPK id = incisoreglam.getIncisoreglamPK();
                if (findIncisoreglam(id) == null) {
                    throw new NonexistentEntityException("The incisoreglam with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IncisoreglamPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incisoreglam incisoreglam;
            try {
                incisoreglam = em.getReference(Incisoreglam.class, id);
                incisoreglam.getIncisoreglamPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incisoreglam with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sanciones> sancionesListOrphanCheck = incisoreglam.getSancionesList();
            for (Sanciones sancionesListOrphanCheckSanciones : sancionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Incisoreglam (" + incisoreglam + ") cannot be destroyed since the Sanciones " + sancionesListOrphanCheckSanciones + " in its sancionesList field has a non-nullable incisoreglam field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Articuloreglam articuloreglam = incisoreglam.getArticuloreglam();
            if (articuloreglam != null) {
                articuloreglam.getIncisoreglamList().remove(incisoreglam);
                articuloreglam = em.merge(articuloreglam);
            }
            em.remove(incisoreglam);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Incisoreglam> findIncisoreglamEntities() {
        return findIncisoreglamEntities(true, -1, -1);
    }

    public List<Incisoreglam> findIncisoreglamEntities(int maxResults, int firstResult) {
        return findIncisoreglamEntities(false, maxResults, firstResult);
    }

    private List<Incisoreglam> findIncisoreglamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Incisoreglam.class));
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

    public Incisoreglam findIncisoreglam(IncisoreglamPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Incisoreglam.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncisoreglamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Incisoreglam> rt = cq.from(Incisoreglam.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
