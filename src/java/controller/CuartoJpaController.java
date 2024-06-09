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
import entities.Piso;
import java.util.ArrayList;
import java.util.List;
import entities.Becado;
import entities.Cuarto;
import entities.CuartoPK;
import entities.Evalcuarto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class CuartoJpaController implements Serializable {

    public CuartoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuarto cuarto) throws PreexistingEntityException, Exception {
        if (cuarto.getCuartoPK() == null) {
            cuarto.setCuartoPK(new CuartoPK());
        }
        if (cuarto.getBecadoList() == null) {
            cuarto.setBecadoList(new ArrayList<Becado>());
        }
        if (cuarto.getEvalcuartoList() == null) {
            cuarto.setEvalcuartoList(new ArrayList<Evalcuarto>());
        }
        cuarto.getCuartoPK().setEdificioid(cuarto.getPiso().getPisoPK().getEdificioid());
        cuarto.getCuartoPK().setPisoid(cuarto.getPiso().getPisoPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Piso piso = cuarto.getPiso();
            if (piso != null) {
                piso = em.getReference(piso.getClass(), piso.getPisoPK());
                cuarto.setPiso(piso);
            }
            List<Becado> attachedBecadoList = new ArrayList<Becado>();
            for (Becado becadoListBecadoToAttach : cuarto.getBecadoList()) {
                becadoListBecadoToAttach = em.getReference(becadoListBecadoToAttach.getClass(), becadoListBecadoToAttach.getCi());
                attachedBecadoList.add(becadoListBecadoToAttach);
            }
            cuarto.setBecadoList(attachedBecadoList);
            List<Evalcuarto> attachedEvalcuartoList = new ArrayList<Evalcuarto>();
            for (Evalcuarto evalcuartoListEvalcuartoToAttach : cuarto.getEvalcuartoList()) {
                evalcuartoListEvalcuartoToAttach = em.getReference(evalcuartoListEvalcuartoToAttach.getClass(), evalcuartoListEvalcuartoToAttach.getEvalcuartoPK());
                attachedEvalcuartoList.add(evalcuartoListEvalcuartoToAttach);
            }
            cuarto.setEvalcuartoList(attachedEvalcuartoList);
            em.persist(cuarto);
            if (piso != null) {
                piso.getCuartoList().add(cuarto);
                piso = em.merge(piso);
            }
            for (Becado becadoListBecado : cuarto.getBecadoList()) {
                Cuarto oldCuartoOfBecadoListBecado = becadoListBecado.getCuarto();
                becadoListBecado.setCuarto(cuarto);
                becadoListBecado = em.merge(becadoListBecado);
                if (oldCuartoOfBecadoListBecado != null) {
                    oldCuartoOfBecadoListBecado.getBecadoList().remove(becadoListBecado);
                    oldCuartoOfBecadoListBecado = em.merge(oldCuartoOfBecadoListBecado);
                }
            }
            for (Evalcuarto evalcuartoListEvalcuarto : cuarto.getEvalcuartoList()) {
                Cuarto oldCuartoOfEvalcuartoListEvalcuarto = evalcuartoListEvalcuarto.getCuarto();
                evalcuartoListEvalcuarto.setCuarto(cuarto);
                evalcuartoListEvalcuarto = em.merge(evalcuartoListEvalcuarto);
                if (oldCuartoOfEvalcuartoListEvalcuarto != null) {
                    oldCuartoOfEvalcuartoListEvalcuarto.getEvalcuartoList().remove(evalcuartoListEvalcuarto);
                    oldCuartoOfEvalcuartoListEvalcuarto = em.merge(oldCuartoOfEvalcuartoListEvalcuarto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuarto(cuarto.getCuartoPK()) != null) {
                throw new PreexistingEntityException("Cuarto " + cuarto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuarto cuarto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        cuarto.getCuartoPK().setEdificioid(cuarto.getPiso().getPisoPK().getEdificioid());
        cuarto.getCuartoPK().setPisoid(cuarto.getPiso().getPisoPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuarto persistentCuarto = em.find(Cuarto.class, cuarto.getCuartoPK());
            Piso pisoOld = persistentCuarto.getPiso();
            Piso pisoNew = cuarto.getPiso();
            List<Becado> becadoListOld = persistentCuarto.getBecadoList();
            List<Becado> becadoListNew = cuarto.getBecadoList();
            List<Evalcuarto> evalcuartoListOld = persistentCuarto.getEvalcuartoList();
            List<Evalcuarto> evalcuartoListNew = cuarto.getEvalcuartoList();
            List<String> illegalOrphanMessages = null;
            for (Becado becadoListOldBecado : becadoListOld) {
                if (!becadoListNew.contains(becadoListOldBecado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Becado " + becadoListOldBecado + " since its cuarto field is not nullable.");
                }
            }
            for (Evalcuarto evalcuartoListOldEvalcuarto : evalcuartoListOld) {
                if (!evalcuartoListNew.contains(evalcuartoListOldEvalcuarto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalcuarto " + evalcuartoListOldEvalcuarto + " since its cuarto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pisoNew != null) {
                pisoNew = em.getReference(pisoNew.getClass(), pisoNew.getPisoPK());
                cuarto.setPiso(pisoNew);
            }
            List<Becado> attachedBecadoListNew = new ArrayList<Becado>();
            for (Becado becadoListNewBecadoToAttach : becadoListNew) {
                becadoListNewBecadoToAttach = em.getReference(becadoListNewBecadoToAttach.getClass(), becadoListNewBecadoToAttach.getCi());
                attachedBecadoListNew.add(becadoListNewBecadoToAttach);
            }
            becadoListNew = attachedBecadoListNew;
            cuarto.setBecadoList(becadoListNew);
            List<Evalcuarto> attachedEvalcuartoListNew = new ArrayList<Evalcuarto>();
            for (Evalcuarto evalcuartoListNewEvalcuartoToAttach : evalcuartoListNew) {
                evalcuartoListNewEvalcuartoToAttach = em.getReference(evalcuartoListNewEvalcuartoToAttach.getClass(), evalcuartoListNewEvalcuartoToAttach.getEvalcuartoPK());
                attachedEvalcuartoListNew.add(evalcuartoListNewEvalcuartoToAttach);
            }
            evalcuartoListNew = attachedEvalcuartoListNew;
            cuarto.setEvalcuartoList(evalcuartoListNew);
            cuarto = em.merge(cuarto);
            if (pisoOld != null && !pisoOld.equals(pisoNew)) {
                pisoOld.getCuartoList().remove(cuarto);
                pisoOld = em.merge(pisoOld);
            }
            if (pisoNew != null && !pisoNew.equals(pisoOld)) {
                pisoNew.getCuartoList().add(cuarto);
                pisoNew = em.merge(pisoNew);
            }
            for (Becado becadoListNewBecado : becadoListNew) {
                if (!becadoListOld.contains(becadoListNewBecado)) {
                    Cuarto oldCuartoOfBecadoListNewBecado = becadoListNewBecado.getCuarto();
                    becadoListNewBecado.setCuarto(cuarto);
                    becadoListNewBecado = em.merge(becadoListNewBecado);
                    if (oldCuartoOfBecadoListNewBecado != null && !oldCuartoOfBecadoListNewBecado.equals(cuarto)) {
                        oldCuartoOfBecadoListNewBecado.getBecadoList().remove(becadoListNewBecado);
                        oldCuartoOfBecadoListNewBecado = em.merge(oldCuartoOfBecadoListNewBecado);
                    }
                }
            }
            for (Evalcuarto evalcuartoListNewEvalcuarto : evalcuartoListNew) {
                if (!evalcuartoListOld.contains(evalcuartoListNewEvalcuarto)) {
                    Cuarto oldCuartoOfEvalcuartoListNewEvalcuarto = evalcuartoListNewEvalcuarto.getCuarto();
                    evalcuartoListNewEvalcuarto.setCuarto(cuarto);
                    evalcuartoListNewEvalcuarto = em.merge(evalcuartoListNewEvalcuarto);
                    if (oldCuartoOfEvalcuartoListNewEvalcuarto != null && !oldCuartoOfEvalcuartoListNewEvalcuarto.equals(cuarto)) {
                        oldCuartoOfEvalcuartoListNewEvalcuarto.getEvalcuartoList().remove(evalcuartoListNewEvalcuarto);
                        oldCuartoOfEvalcuartoListNewEvalcuarto = em.merge(oldCuartoOfEvalcuartoListNewEvalcuarto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CuartoPK id = cuarto.getCuartoPK();
                if (findCuarto(id) == null) {
                    throw new NonexistentEntityException("The cuarto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CuartoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuarto cuarto;
            try {
                cuarto = em.getReference(Cuarto.class, id);
                cuarto.getCuartoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuarto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Becado> becadoListOrphanCheck = cuarto.getBecadoList();
            for (Becado becadoListOrphanCheckBecado : becadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuarto (" + cuarto + ") cannot be destroyed since the Becado " + becadoListOrphanCheckBecado + " in its becadoList field has a non-nullable cuarto field.");
            }
            List<Evalcuarto> evalcuartoListOrphanCheck = cuarto.getEvalcuartoList();
            for (Evalcuarto evalcuartoListOrphanCheckEvalcuarto : evalcuartoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuarto (" + cuarto + ") cannot be destroyed since the Evalcuarto " + evalcuartoListOrphanCheckEvalcuarto + " in its evalcuartoList field has a non-nullable cuarto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Piso piso = cuarto.getPiso();
            if (piso != null) {
                piso.getCuartoList().remove(cuarto);
                piso = em.merge(piso);
            }
            em.remove(cuarto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuarto> findCuartoEntities() {
        return findCuartoEntities(true, -1, -1);
    }

    public List<Cuarto> findCuartoEntities(int maxResults, int firstResult) {
        return findCuartoEntities(false, maxResults, firstResult);
    }

    private List<Cuarto> findCuartoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuarto.class));
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

    public Cuarto findCuarto(CuartoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuarto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuartoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuarto> rt = cq.from(Cuarto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
