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
import java.util.ArrayList;
import java.util.List;
import entities.Cuarto;
import entities.Piso;
import entities.PisoPK;
import entities.Trabajoprod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class PisoJpaController implements Serializable {

    public PisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Piso piso) throws PreexistingEntityException, Exception {
        if (piso.getPisoPK() == null) {
            piso.setPisoPK(new PisoPK());
        }
        if (piso.getCuartoList() == null) {
            piso.setCuartoList(new ArrayList<Cuarto>());
        }
        if (piso.getTrabajoprodList() == null) {
            piso.setTrabajoprodList(new ArrayList<Trabajoprod>());
        }
        piso.getPisoPK().setEdificioid(piso.getEdificio().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado jefepiso = piso.getJefepiso();
            if (jefepiso != null) {
                jefepiso = em.getReference(jefepiso.getClass(), jefepiso.getCi());
                piso.setJefepiso(jefepiso);
            }
            Becado jefelimp = piso.getJefelimp();
            if (jefelimp != null) {
                jefelimp = em.getReference(jefelimp.getClass(), jefelimp.getCi());
                piso.setJefelimp(jefelimp);
            }
            Edificio edificio = piso.getEdificio();
            if (edificio != null) {
                edificio = em.getReference(edificio.getClass(), edificio.getId());
                piso.setEdificio(edificio);
            }
            List<Cuarto> attachedCuartoList = new ArrayList<Cuarto>();
            for (Cuarto cuartoListCuartoToAttach : piso.getCuartoList()) {
                cuartoListCuartoToAttach = em.getReference(cuartoListCuartoToAttach.getClass(), cuartoListCuartoToAttach.getCuartoPK());
                attachedCuartoList.add(cuartoListCuartoToAttach);
            }
            piso.setCuartoList(attachedCuartoList);
            List<Trabajoprod> attachedTrabajoprodList = new ArrayList<Trabajoprod>();
            for (Trabajoprod trabajoprodListTrabajoprodToAttach : piso.getTrabajoprodList()) {
                trabajoprodListTrabajoprodToAttach = em.getReference(trabajoprodListTrabajoprodToAttach.getClass(), trabajoprodListTrabajoprodToAttach.getTrabajoprodPK());
                attachedTrabajoprodList.add(trabajoprodListTrabajoprodToAttach);
            }
            piso.setTrabajoprodList(attachedTrabajoprodList);
            em.persist(piso);
            if (jefepiso != null) {
                jefepiso.getPisoList().add(piso);
                jefepiso = em.merge(jefepiso);
            }
            if (jefelimp != null) {
                jefelimp.getPisoList().add(piso);
                jefelimp = em.merge(jefelimp);
            }
            if (edificio != null) {
                edificio.getPisoList().add(piso);
                edificio = em.merge(edificio);
            }
            for (Cuarto cuartoListCuarto : piso.getCuartoList()) {
                Piso oldPisoOfCuartoListCuarto = cuartoListCuarto.getPiso();
                cuartoListCuarto.setPiso(piso);
                cuartoListCuarto = em.merge(cuartoListCuarto);
                if (oldPisoOfCuartoListCuarto != null) {
                    oldPisoOfCuartoListCuarto.getCuartoList().remove(cuartoListCuarto);
                    oldPisoOfCuartoListCuarto = em.merge(oldPisoOfCuartoListCuarto);
                }
            }
            for (Trabajoprod trabajoprodListTrabajoprod : piso.getTrabajoprodList()) {
                Piso oldPisoOfTrabajoprodListTrabajoprod = trabajoprodListTrabajoprod.getPiso();
                trabajoprodListTrabajoprod.setPiso(piso);
                trabajoprodListTrabajoprod = em.merge(trabajoprodListTrabajoprod);
                if (oldPisoOfTrabajoprodListTrabajoprod != null) {
                    oldPisoOfTrabajoprodListTrabajoprod.getTrabajoprodList().remove(trabajoprodListTrabajoprod);
                    oldPisoOfTrabajoprodListTrabajoprod = em.merge(oldPisoOfTrabajoprodListTrabajoprod);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPiso(piso.getPisoPK()) != null) {
                throw new PreexistingEntityException("Piso " + piso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Piso piso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        piso.getPisoPK().setEdificioid(piso.getEdificio().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Piso persistentPiso = em.find(Piso.class, piso.getPisoPK());
            Becado jefepisoOld = persistentPiso.getJefepiso();
            Becado jefepisoNew = piso.getJefepiso();
            Becado jefelimpOld = persistentPiso.getJefelimp();
            Becado jefelimpNew = piso.getJefelimp();
            Edificio edificioOld = persistentPiso.getEdificio();
            Edificio edificioNew = piso.getEdificio();
            List<Cuarto> cuartoListOld = persistentPiso.getCuartoList();
            List<Cuarto> cuartoListNew = piso.getCuartoList();
            List<Trabajoprod> trabajoprodListOld = persistentPiso.getTrabajoprodList();
            List<Trabajoprod> trabajoprodListNew = piso.getTrabajoprodList();
            List<String> illegalOrphanMessages = null;
            for (Cuarto cuartoListOldCuarto : cuartoListOld) {
                if (!cuartoListNew.contains(cuartoListOldCuarto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuarto " + cuartoListOldCuarto + " since its piso field is not nullable.");
                }
            }
            for (Trabajoprod trabajoprodListOldTrabajoprod : trabajoprodListOld) {
                if (!trabajoprodListNew.contains(trabajoprodListOldTrabajoprod)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trabajoprod " + trabajoprodListOldTrabajoprod + " since its piso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (jefepisoNew != null) {
                jefepisoNew = em.getReference(jefepisoNew.getClass(), jefepisoNew.getCi());
                piso.setJefepiso(jefepisoNew);
            }
            if (jefelimpNew != null) {
                jefelimpNew = em.getReference(jefelimpNew.getClass(), jefelimpNew.getCi());
                piso.setJefelimp(jefelimpNew);
            }
            if (edificioNew != null) {
                edificioNew = em.getReference(edificioNew.getClass(), edificioNew.getId());
                piso.setEdificio(edificioNew);
            }
            List<Cuarto> attachedCuartoListNew = new ArrayList<Cuarto>();
            for (Cuarto cuartoListNewCuartoToAttach : cuartoListNew) {
                cuartoListNewCuartoToAttach = em.getReference(cuartoListNewCuartoToAttach.getClass(), cuartoListNewCuartoToAttach.getCuartoPK());
                attachedCuartoListNew.add(cuartoListNewCuartoToAttach);
            }
            cuartoListNew = attachedCuartoListNew;
            piso.setCuartoList(cuartoListNew);
            List<Trabajoprod> attachedTrabajoprodListNew = new ArrayList<Trabajoprod>();
            for (Trabajoprod trabajoprodListNewTrabajoprodToAttach : trabajoprodListNew) {
                trabajoprodListNewTrabajoprodToAttach = em.getReference(trabajoprodListNewTrabajoprodToAttach.getClass(), trabajoprodListNewTrabajoprodToAttach.getTrabajoprodPK());
                attachedTrabajoprodListNew.add(trabajoprodListNewTrabajoprodToAttach);
            }
            trabajoprodListNew = attachedTrabajoprodListNew;
            piso.setTrabajoprodList(trabajoprodListNew);
            piso = em.merge(piso);
            if (jefepisoOld != null && !jefepisoOld.equals(jefepisoNew)) {
                jefepisoOld.getPisoList().remove(piso);
                jefepisoOld = em.merge(jefepisoOld);
            }
            if (jefepisoNew != null && !jefepisoNew.equals(jefepisoOld)) {
                jefepisoNew.getPisoList().add(piso);
                jefepisoNew = em.merge(jefepisoNew);
            }
            if (jefelimpOld != null && !jefelimpOld.equals(jefelimpNew)) {
                jefelimpOld.getPisoList().remove(piso);
                jefelimpOld = em.merge(jefelimpOld);
            }
            if (jefelimpNew != null && !jefelimpNew.equals(jefelimpOld)) {
                jefelimpNew.getPisoList().add(piso);
                jefelimpNew = em.merge(jefelimpNew);
            }
            if (edificioOld != null && !edificioOld.equals(edificioNew)) {
                edificioOld.getPisoList().remove(piso);
                edificioOld = em.merge(edificioOld);
            }
            if (edificioNew != null && !edificioNew.equals(edificioOld)) {
                edificioNew.getPisoList().add(piso);
                edificioNew = em.merge(edificioNew);
            }
            for (Cuarto cuartoListNewCuarto : cuartoListNew) {
                if (!cuartoListOld.contains(cuartoListNewCuarto)) {
                    Piso oldPisoOfCuartoListNewCuarto = cuartoListNewCuarto.getPiso();
                    cuartoListNewCuarto.setPiso(piso);
                    cuartoListNewCuarto = em.merge(cuartoListNewCuarto);
                    if (oldPisoOfCuartoListNewCuarto != null && !oldPisoOfCuartoListNewCuarto.equals(piso)) {
                        oldPisoOfCuartoListNewCuarto.getCuartoList().remove(cuartoListNewCuarto);
                        oldPisoOfCuartoListNewCuarto = em.merge(oldPisoOfCuartoListNewCuarto);
                    }
                }
            }
            for (Trabajoprod trabajoprodListNewTrabajoprod : trabajoprodListNew) {
                if (!trabajoprodListOld.contains(trabajoprodListNewTrabajoprod)) {
                    Piso oldPisoOfTrabajoprodListNewTrabajoprod = trabajoprodListNewTrabajoprod.getPiso();
                    trabajoprodListNewTrabajoprod.setPiso(piso);
                    trabajoprodListNewTrabajoprod = em.merge(trabajoprodListNewTrabajoprod);
                    if (oldPisoOfTrabajoprodListNewTrabajoprod != null && !oldPisoOfTrabajoprodListNewTrabajoprod.equals(piso)) {
                        oldPisoOfTrabajoprodListNewTrabajoprod.getTrabajoprodList().remove(trabajoprodListNewTrabajoprod);
                        oldPisoOfTrabajoprodListNewTrabajoprod = em.merge(oldPisoOfTrabajoprodListNewTrabajoprod);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PisoPK id = piso.getPisoPK();
                if (findPiso(id) == null) {
                    throw new NonexistentEntityException("The piso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PisoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Piso piso;
            try {
                piso = em.getReference(Piso.class, id);
                piso.getPisoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The piso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuarto> cuartoListOrphanCheck = piso.getCuartoList();
            for (Cuarto cuartoListOrphanCheckCuarto : cuartoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Piso (" + piso + ") cannot be destroyed since the Cuarto " + cuartoListOrphanCheckCuarto + " in its cuartoList field has a non-nullable piso field.");
            }
            List<Trabajoprod> trabajoprodListOrphanCheck = piso.getTrabajoprodList();
            for (Trabajoprod trabajoprodListOrphanCheckTrabajoprod : trabajoprodListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Piso (" + piso + ") cannot be destroyed since the Trabajoprod " + trabajoprodListOrphanCheckTrabajoprod + " in its trabajoprodList field has a non-nullable piso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Becado jefepiso = piso.getJefepiso();
            if (jefepiso != null) {
                jefepiso.getPisoList().remove(piso);
                jefepiso = em.merge(jefepiso);
            }
            Becado jefelimp = piso.getJefelimp();
            if (jefelimp != null) {
                jefelimp.getPisoList().remove(piso);
                jefelimp = em.merge(jefelimp);
            }
            Edificio edificio = piso.getEdificio();
            if (edificio != null) {
                edificio.getPisoList().remove(piso);
                edificio = em.merge(edificio);
            }
            em.remove(piso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Piso> findPisoEntities() {
        return findPisoEntities(true, -1, -1);
    }

    public List<Piso> findPisoEntities(int maxResults, int firstResult) {
        return findPisoEntities(false, maxResults, firstResult);
    }

    private List<Piso> findPisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Piso.class));
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

    public Piso findPiso(PisoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Piso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Piso> rt = cq.from(Piso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
