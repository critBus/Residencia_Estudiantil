
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Becado;
import entities.Incisoreglam;
import entities.Sanciones;
import entities.SancionesPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SancionesJpaController implements Serializable {

    public SancionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sanciones sanciones) throws PreexistingEntityException, Exception {
        if (sanciones.getSancionesPK() == null) {
            sanciones.setSancionesPK(new SancionesPK());
        }
        sanciones.getSancionesPK().setBecadoci(sanciones.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado becado = sanciones.getBecado();
            if (becado != null) {
                becado = em.getReference(becado.getClass(), becado.getCi());
                sanciones.setBecado(becado);
            }
            Incisoreglam incisoreglam = sanciones.getIncisoreglam();
            if (incisoreglam != null) {
                incisoreglam = em.getReference(incisoreglam.getClass(), incisoreglam.getIncisoreglamPK());
                sanciones.setIncisoreglam(incisoreglam);
            }
            em.persist(sanciones);
            if (becado != null) {
                becado.getSancionesList().add(sanciones);
                becado = em.merge(becado);
            }
            if (incisoreglam != null) {
                incisoreglam.getSancionesList().add(sanciones);
                incisoreglam = em.merge(incisoreglam);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSanciones(sanciones.getSancionesPK()) != null) {
                throw new PreexistingEntityException("Sanciones " + sanciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sanciones sanciones) throws NonexistentEntityException, Exception {
        sanciones.getSancionesPK().setBecadoci(sanciones.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sanciones persistentSanciones = em.find(Sanciones.class, sanciones.getSancionesPK());
            Becado becadoOld = persistentSanciones.getBecado();
            Becado becadoNew = sanciones.getBecado();
            Incisoreglam incisoreglamOld = persistentSanciones.getIncisoreglam();
            Incisoreglam incisoreglamNew = sanciones.getIncisoreglam();
            if (becadoNew != null) {
                becadoNew = em.getReference(becadoNew.getClass(), becadoNew.getCi());
                sanciones.setBecado(becadoNew);
            }
            if (incisoreglamNew != null) {
                incisoreglamNew = em.getReference(incisoreglamNew.getClass(), incisoreglamNew.getIncisoreglamPK());
                sanciones.setIncisoreglam(incisoreglamNew);
            }
            sanciones = em.merge(sanciones);
            if (becadoOld != null && !becadoOld.equals(becadoNew)) {
                becadoOld.getSancionesList().remove(sanciones);
                becadoOld = em.merge(becadoOld);
            }
            if (becadoNew != null && !becadoNew.equals(becadoOld)) {
                becadoNew.getSancionesList().add(sanciones);
                becadoNew = em.merge(becadoNew);
            }
            if (incisoreglamOld != null && !incisoreglamOld.equals(incisoreglamNew)) {
                incisoreglamOld.getSancionesList().remove(sanciones);
                incisoreglamOld = em.merge(incisoreglamOld);
            }
            if (incisoreglamNew != null && !incisoreglamNew.equals(incisoreglamOld)) {
                incisoreglamNew.getSancionesList().add(sanciones);
                incisoreglamNew = em.merge(incisoreglamNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SancionesPK id = sanciones.getSancionesPK();
                if (findSanciones(id) == null) {
                    throw new NonexistentEntityException("The sanciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SancionesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sanciones sanciones;
            try {
                sanciones = em.getReference(Sanciones.class, id);
                sanciones.getSancionesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sanciones with id " + id + " no longer exists.", enfe);
            }
            Becado becado = sanciones.getBecado();
            if (becado != null) {
                becado.getSancionesList().remove(sanciones);
                becado = em.merge(becado);
            }
            Incisoreglam incisoreglam = sanciones.getIncisoreglam();
            if (incisoreglam != null) {
                incisoreglam.getSancionesList().remove(sanciones);
                incisoreglam = em.merge(incisoreglam);
            }
            em.remove(sanciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sanciones> findSancionesEntities() {
        return findSancionesEntities(true, -1, -1);
    }

    public List<Sanciones> findSancionesEntities(int maxResults, int firstResult) {
        return findSancionesEntities(false, maxResults, firstResult);
    }

    private List<Sanciones> findSancionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sanciones.class));
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

    public Sanciones findSanciones(SancionesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sanciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSancionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sanciones> rt = cq.from(Sanciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
