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
import entities.Becado;
import entities.Edificio;
import entities.Trabajador;
import java.util.ArrayList;
import java.util.List;
import entities.Piso;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class EdificioJpaController implements Serializable {

    public EdificioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Edificio edificio) throws PreexistingEntityException, Exception {
        if (edificio.getPisoList() == null) {
            edificio.setPisoList(new ArrayList<Piso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado jefeedif = edificio.getJefeedif();
            if (jefeedif != null) {
                jefeedif = em.getReference(jefeedif.getClass(), jefeedif.getCi());
                edificio.setJefeedif(jefeedif);
            }
            Trabajador especialista = edificio.getEspecialista();
            if (especialista != null) {
                especialista = em.getReference(especialista.getClass(), especialista.getCi());
                edificio.setEspecialista(especialista);
            }
            Trabajador instructor = edificio.getInstructor();
            if (instructor != null) {
                instructor = em.getReference(instructor.getClass(), instructor.getCi());
                edificio.setInstructor(instructor);
            }
            List<Piso> attachedPisoList = new ArrayList<Piso>();
            for (Piso pisoListPisoToAttach : edificio.getPisoList()) {
                pisoListPisoToAttach = em.getReference(pisoListPisoToAttach.getClass(), pisoListPisoToAttach.getPisoPK());
                attachedPisoList.add(pisoListPisoToAttach);
            }
            edificio.setPisoList(attachedPisoList);
            em.persist(edificio);
            if (jefeedif != null) {
                jefeedif.getEdificioList().add(edificio);
                jefeedif = em.merge(jefeedif);
            }
            if (especialista != null) {
                especialista.getEdificioList().add(edificio);
                especialista = em.merge(especialista);
            }
            if (instructor != null) {
                instructor.getEdificioList().add(edificio);
                instructor = em.merge(instructor);
            }
            for (Piso pisoListPiso : edificio.getPisoList()) {
                Edificio oldEdificioOfPisoListPiso = pisoListPiso.getEdificio();
                pisoListPiso.setEdificio(edificio);
                pisoListPiso = em.merge(pisoListPiso);
                if (oldEdificioOfPisoListPiso != null) {
                    oldEdificioOfPisoListPiso.getPisoList().remove(pisoListPiso);
                    oldEdificioOfPisoListPiso = em.merge(oldEdificioOfPisoListPiso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEdificio(edificio.getId()) != null) {
                throw new PreexistingEntityException("Edificio " + edificio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Edificio edificio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Edificio persistentEdificio = em.find(Edificio.class, edificio.getId());
            Becado jefeedifOld = persistentEdificio.getJefeedif();
            Becado jefeedifNew = edificio.getJefeedif();
            Trabajador especialistaOld = persistentEdificio.getEspecialista();
            Trabajador especialistaNew = edificio.getEspecialista();
            Trabajador instructorOld = persistentEdificio.getInstructor();
            Trabajador instructorNew = edificio.getInstructor();
            List<Piso> pisoListOld = persistentEdificio.getPisoList();
            List<Piso> pisoListNew = edificio.getPisoList();
            List<String> illegalOrphanMessages = null;
            for (Piso pisoListOldPiso : pisoListOld) {
                if (!pisoListNew.contains(pisoListOldPiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Piso " + pisoListOldPiso + " since its edificio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (jefeedifNew != null) {
                jefeedifNew = em.getReference(jefeedifNew.getClass(), jefeedifNew.getCi());
                edificio.setJefeedif(jefeedifNew);
            }
            if (especialistaNew != null) {
                especialistaNew = em.getReference(especialistaNew.getClass(), especialistaNew.getCi());
                edificio.setEspecialista(especialistaNew);
            }
            if (instructorNew != null) {
                instructorNew = em.getReference(instructorNew.getClass(), instructorNew.getCi());
                edificio.setInstructor(instructorNew);
            }
            List<Piso> attachedPisoListNew = new ArrayList<Piso>();
            for (Piso pisoListNewPisoToAttach : pisoListNew) {
                pisoListNewPisoToAttach = em.getReference(pisoListNewPisoToAttach.getClass(), pisoListNewPisoToAttach.getPisoPK());
                attachedPisoListNew.add(pisoListNewPisoToAttach);
            }
            pisoListNew = attachedPisoListNew;
            edificio.setPisoList(pisoListNew);
            edificio = em.merge(edificio);
            if (jefeedifOld != null && !jefeedifOld.equals(jefeedifNew)) {
                jefeedifOld.getEdificioList().remove(edificio);
                jefeedifOld = em.merge(jefeedifOld);
            }
            if (jefeedifNew != null && !jefeedifNew.equals(jefeedifOld)) {
                jefeedifNew.getEdificioList().add(edificio);
                jefeedifNew = em.merge(jefeedifNew);
            }
            if (especialistaOld != null && !especialistaOld.equals(especialistaNew)) {
                especialistaOld.getEdificioList().remove(edificio);
                especialistaOld = em.merge(especialistaOld);
            }
            if (especialistaNew != null && !especialistaNew.equals(especialistaOld)) {
                especialistaNew.getEdificioList().add(edificio);
                especialistaNew = em.merge(especialistaNew);
            }
            if (instructorOld != null && !instructorOld.equals(instructorNew)) {
                instructorOld.getEdificioList().remove(edificio);
                instructorOld = em.merge(instructorOld);
            }
            if (instructorNew != null && !instructorNew.equals(instructorOld)) {
                instructorNew.getEdificioList().add(edificio);
                instructorNew = em.merge(instructorNew);
            }
            for (Piso pisoListNewPiso : pisoListNew) {
                if (!pisoListOld.contains(pisoListNewPiso)) {
                    Edificio oldEdificioOfPisoListNewPiso = pisoListNewPiso.getEdificio();
                    pisoListNewPiso.setEdificio(edificio);
                    pisoListNewPiso = em.merge(pisoListNewPiso);
                    if (oldEdificioOfPisoListNewPiso != null && !oldEdificioOfPisoListNewPiso.equals(edificio)) {
                        oldEdificioOfPisoListNewPiso.getPisoList().remove(pisoListNewPiso);
                        oldEdificioOfPisoListNewPiso = em.merge(oldEdificioOfPisoListNewPiso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = edificio.getId();
                if (findEdificio(id) == null) {
                    throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.");
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
            Edificio edificio;
            try {
                edificio = em.getReference(Edificio.class, id);
                edificio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Piso> pisoListOrphanCheck = edificio.getPisoList();
            for (Piso pisoListOrphanCheckPiso : pisoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Edificio (" + edificio + ") cannot be destroyed since the Piso " + pisoListOrphanCheckPiso + " in its pisoList field has a non-nullable edificio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Becado jefeedif = edificio.getJefeedif();
            if (jefeedif != null) {
                jefeedif.getEdificioList().remove(edificio);
                jefeedif = em.merge(jefeedif);
            }
            Trabajador especialista = edificio.getEspecialista();
            if (especialista != null) {
                especialista.getEdificioList().remove(edificio);
                especialista = em.merge(especialista);
            }
            Trabajador instructor = edificio.getInstructor();
            if (instructor != null) {
                instructor.getEdificioList().remove(edificio);
                instructor = em.merge(instructor);
            }
            em.remove(edificio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Edificio> findEdificioEntities() {
        return findEdificioEntities(true, -1, -1);
    }

    public List<Edificio> findEdificioEntities(int maxResults, int firstResult) {
        return findEdificioEntities(false, maxResults, firstResult);
    }

    private List<Edificio> findEdificioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Edificio.class));
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

    public Edificio findEdificio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Edificio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEdificioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Edificio> rt = cq.from(Edificio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
