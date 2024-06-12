
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entities.Enfermedades;
import entities.BecadoEnfermedades;
import entities.BecadoEnfermedadesPK;
import entities.Becado;
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
public class BecadoEnfermedadJpaController implements Serializable{
    
    public BecadoEnfermedadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BecadoEnfermedades becadoEnfermedades) throws PreexistingEntityException, Exception {
        if (becadoEnfermedades.getBecadoEnfermedadesPK()== null) {
            becadoEnfermedades.setBecadoEnfermedadesPK(new BecadoEnfermedadesPK());
        }
        becadoEnfermedades.getBecadoEnfermedadesPK().setEnfermid(becadoEnfermedades.getEnfermedades().getId());
        becadoEnfermedades.getBecadoEnfermedadesPK().setBecadoci(becadoEnfermedades.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermedades enfermedades = becadoEnfermedades.getEnfermedades();
            if (enfermedades != null) {
                enfermedades = em.getReference(enfermedades.getClass(), enfermedades.getId());
                becadoEnfermedades.setEnfermedades(enfermedades);
            }
            Becado becado = becadoEnfermedades.getBecado();
            if (becado != null) {
                becado = em.getReference(becado.getClass(), becado.getCi());
                becadoEnfermedades.setBecado(becado);
            }
            em.persist(becadoEnfermedades);
            if (enfermedades != null) {
                enfermedades.getBecadoEnfermedadesList().add(becadoEnfermedades);
                enfermedades = em.merge(enfermedades);
            }
            if (becado != null) {
                becado.getBecadoEnfermedadesList().add(becadoEnfermedades);
                becado = em.merge(becado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBecadoEnfermedades(becadoEnfermedades.getBecadoEnfermedadesPK()) != null) {
                throw new PreexistingEntityException("BecadoEnfermedades " + becadoEnfermedades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BecadoEnfermedades becadoEnfermedades) throws NonexistentEntityException, Exception {
        becadoEnfermedades.getBecadoEnfermedadesPK().setEnfermid(becadoEnfermedades.getEnfermedades().getId());
        becadoEnfermedades.getBecadoEnfermedadesPK().setBecadoci(becadoEnfermedades.getBecado().getCi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BecadoEnfermedades persistentBecadoEnfermedades = em.find(BecadoEnfermedades.class, becadoEnfermedades.getBecadoEnfermedadesPK());
            Enfermedades enfermedadesOld = persistentBecadoEnfermedades.getEnfermedades();
            Enfermedades enfermedadesNew = becadoEnfermedades.getEnfermedades();
            Becado becadoOld = persistentBecadoEnfermedades.getBecado();
            Becado becadoNew = becadoEnfermedades.getBecado();
            if (enfermedadesNew != null) {
                enfermedadesNew = em.getReference(enfermedadesNew.getClass(), enfermedadesNew.getId());
                becadoEnfermedades.setEnfermedades(enfermedadesNew);
            }
            if (becadoNew != null) {
                becadoNew = em.getReference(becadoNew.getClass(), becadoNew.getCi());
                becadoEnfermedades.setBecado(becadoNew);
            }
            becadoEnfermedades = em.merge(becadoEnfermedades);
            if (enfermedadesOld != null && !enfermedadesOld.equals(enfermedadesNew)) {
                enfermedadesOld.getBecadoEnfermedadesList().remove(becadoEnfermedades);
                enfermedadesOld = em.merge(enfermedadesOld);
            }
            if (enfermedadesNew != null && !enfermedadesNew.equals(enfermedadesOld)) {
                enfermedadesNew.getBecadoEnfermedadesList().add(becadoEnfermedades);
                enfermedadesNew = em.merge(enfermedadesNew);
            }
            if (becadoOld != null && !becadoOld.equals(becadoNew)) {
                becadoOld.getBecadoEnfermedadesList().remove(becadoEnfermedades);
                becadoOld = em.merge(becadoOld);
            }
            if (becadoNew != null && !becadoNew.equals(becadoOld)) {
                becadoNew.getBecadoEnfermedadesList().add(becadoEnfermedades);
                becadoNew = em.merge(becadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BecadoEnfermedadesPK id = becadoEnfermedades.getBecadoEnfermedadesPK();
                if (findBecadoEnfermedades(id) == null) {
                    throw new NonexistentEntityException("The becadoEnfermedades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BecadoEnfermedadesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BecadoEnfermedades becadoEnfermedades;
            try {
                becadoEnfermedades = em.getReference(BecadoEnfermedades.class, id);
                becadoEnfermedades.getBecadoEnfermedadesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The becadoEnfermedades with id " + id + " no longer exists.", enfe);
            }
            Enfermedades enfermedades = becadoEnfermedades.getEnfermedades();
            if (enfermedades != null) {
                enfermedades.getBecadoEnfermedadesList().remove(becadoEnfermedades);
                enfermedades = em.merge(enfermedades);
            }
            Becado becado = becadoEnfermedades.getBecado();
            if (becado != null) {
                becado.getBecadoEnfermedadesList().remove(becadoEnfermedades);
                becado = em.merge(becado);
            }
            em.remove(becadoEnfermedades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BecadoEnfermedades> findBecadoEnfermedadesEntities() {
        return findBecadoEnfermedadesEntities(true, -1, -1);
    }

    public List<BecadoEnfermedades> findBecadoEnfermedadesEntities(int maxResults, int firstResult) {
        return findBecadoEnfermedadesEntities(false, maxResults, firstResult);
    }

    private List<BecadoEnfermedades> findBecadoEnfermedadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BecadoEnfermedades.class));
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

    public BecadoEnfermedades findBecadoEnfermedades(BecadoEnfermedadesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BecadoEnfermedades.class, id);
        } finally {
            em.close();
        }
    }

    public int getBecadoEnfermedadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BecadoEnfermedades> rt = cq.from(BecadoEnfermedades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
