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
import entities.Evalcuarteleria;
import java.util.ArrayList;
import java.util.List;
import entities.Evalcuarto;
import entities.Edificio;
import entities.Planteamientos;
import entities.Trabajador;
import entities.Evalguardia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author neylis
 */
public class TrabajadorJpaController implements Serializable {

    public TrabajadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajador trabajador) throws PreexistingEntityException, Exception {
        
        if (trabajador.getEvalcuarteleriaList() == null) {
            trabajador.setEvalcuarteleriaList(new ArrayList<Evalcuarteleria>());
        }
        if (trabajador.getEvalcuartoList() == null) {
            trabajador.setEvalcuartoList(new ArrayList<Evalcuarto>());
        }
        if (trabajador.getEdificioList() == null) {
            trabajador.setEdificioList(new ArrayList<Edificio>());
        }
        if (trabajador.getEdificioList1() == null) {
            trabajador.setEdificioList1(new ArrayList<Edificio>());
        }
        if (trabajador.getPlanteamientosList() == null) {
            trabajador.setPlanteamientosList(new ArrayList<Planteamientos>());
        }
        if (trabajador.getEvalguardiaList()== null) {
            trabajador.setEvalguardiaList(new ArrayList<Evalguardia>());
        }
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evalcuarteleria> attachedEvalcuarteleriaList = new ArrayList<Evalcuarteleria>();
            for (Evalcuarteleria evalcuarteleriaListEvalcuarteleriaToAttach : trabajador.getEvalcuarteleriaList()) {
                evalcuarteleriaListEvalcuarteleriaToAttach = em.getReference(evalcuarteleriaListEvalcuarteleriaToAttach.getClass(), evalcuarteleriaListEvalcuarteleriaToAttach.getEvalcuarteleriaPK());
                attachedEvalcuarteleriaList.add(evalcuarteleriaListEvalcuarteleriaToAttach);
            }
            List<Evalguardia> attachedEvalguardiaList = new ArrayList<Evalguardia>();
            for (Evalguardia evalguardiaListEvalguardiaToAttach : trabajador.getEvalguardiaList()) {
                evalguardiaListEvalguardiaToAttach = em.getReference(evalguardiaListEvalguardiaToAttach.getClass(), evalguardiaListEvalguardiaToAttach.getEvalguardiaPK());
                attachedEvalguardiaList.add(evalguardiaListEvalguardiaToAttach);
            }
            trabajador.setEvalcuarteleriaList(attachedEvalcuarteleriaList);
            List<Evalcuarto> attachedEvalcuartoList = new ArrayList<Evalcuarto>();
            for (Evalcuarto evalcuartoListEvalcuartoToAttach : trabajador.getEvalcuartoList()) {
                evalcuartoListEvalcuartoToAttach = em.getReference(evalcuartoListEvalcuartoToAttach.getClass(), evalcuartoListEvalcuartoToAttach.getEvalcuartoPK());
                attachedEvalcuartoList.add(evalcuartoListEvalcuartoToAttach);
            }
            trabajador.setEvalcuartoList(attachedEvalcuartoList);
            List<Edificio> attachedEdificioList = new ArrayList<Edificio>();
            for (Edificio edificioListEdificioToAttach : trabajador.getEdificioList()) {
                edificioListEdificioToAttach = em.getReference(edificioListEdificioToAttach.getClass(), edificioListEdificioToAttach.getId());
                attachedEdificioList.add(edificioListEdificioToAttach);
            }
            trabajador.setEdificioList(attachedEdificioList);
            List<Edificio> attachedEdificioList1 = new ArrayList<Edificio>();
            for (Edificio edificioList1EdificioToAttach : trabajador.getEdificioList1()) {
                edificioList1EdificioToAttach = em.getReference(edificioList1EdificioToAttach.getClass(), edificioList1EdificioToAttach.getId());
                attachedEdificioList1.add(edificioList1EdificioToAttach);
            }
            trabajador.setEdificioList1(attachedEdificioList1);
            List<Planteamientos> attachedPlanteamientosList = new ArrayList<Planteamientos>();
            for (Planteamientos planteamientosListPlanteamientosToAttach : trabajador.getPlanteamientosList()) {
                planteamientosListPlanteamientosToAttach = em.getReference(planteamientosListPlanteamientosToAttach.getClass(), planteamientosListPlanteamientosToAttach.getId());
                attachedPlanteamientosList.add(planteamientosListPlanteamientosToAttach);
            }
            trabajador.setPlanteamientosList(attachedPlanteamientosList);
            em.persist(trabajador);
            for (Evalcuarteleria evalcuarteleriaListEvalcuarteleria : trabajador.getEvalcuarteleriaList()) {
                Trabajador oldInspeccionaciOfEvalcuarteleriaListEvalcuarteleria = evalcuarteleriaListEvalcuarteleria.getInspeccionaci();
                evalcuarteleriaListEvalcuarteleria.setInspeccionaci(trabajador);
                evalcuarteleriaListEvalcuarteleria = em.merge(evalcuarteleriaListEvalcuarteleria);
                if (oldInspeccionaciOfEvalcuarteleriaListEvalcuarteleria != null) {
                    oldInspeccionaciOfEvalcuarteleriaListEvalcuarteleria.getEvalcuarteleriaList().remove(evalcuarteleriaListEvalcuarteleria);
                    oldInspeccionaciOfEvalcuarteleriaListEvalcuarteleria = em.merge(oldInspeccionaciOfEvalcuarteleriaListEvalcuarteleria);
                }
            }
            
            for (Evalguardia evalguardiaListEvalguardia : trabajador.getEvalguardiaList()) {
                Trabajador oldTrabajadorciOfEvalguardiaListEvalguardia = evalguardiaListEvalguardia.getTrabajadorci();
                evalguardiaListEvalguardia.setTrabajadorci(trabajador);
                evalguardiaListEvalguardia = em.merge(evalguardiaListEvalguardia);
                if (oldTrabajadorciOfEvalguardiaListEvalguardia != null) {
                    oldTrabajadorciOfEvalguardiaListEvalguardia.getEvalguardiaList().remove(evalguardiaListEvalguardia);
                    oldTrabajadorciOfEvalguardiaListEvalguardia = em.merge(oldTrabajadorciOfEvalguardiaListEvalguardia);
                }
            }
            for (Evalcuarto evalcuartoListEvalcuarto : trabajador.getEvalcuartoList()) {
                Trabajador oldTrabajadorciOfEvalcuartoListEvalcuarto = evalcuartoListEvalcuarto.getTrabajadorci();
                evalcuartoListEvalcuarto.setTrabajadorci(trabajador);
                evalcuartoListEvalcuarto = em.merge(evalcuartoListEvalcuarto);
                if (oldTrabajadorciOfEvalcuartoListEvalcuarto != null) {
                    oldTrabajadorciOfEvalcuartoListEvalcuarto.getEvalcuartoList().remove(evalcuartoListEvalcuarto);
                    oldTrabajadorciOfEvalcuartoListEvalcuarto = em.merge(oldTrabajadorciOfEvalcuartoListEvalcuarto);
                }
            }
            for (Edificio edificioListEdificio : trabajador.getEdificioList()) {
                Trabajador oldEspecialistaOfEdificioListEdificio = edificioListEdificio.getEspecialista();
                edificioListEdificio.setEspecialista(trabajador);
                edificioListEdificio = em.merge(edificioListEdificio);
                if (oldEspecialistaOfEdificioListEdificio != null) {
                    oldEspecialistaOfEdificioListEdificio.getEdificioList().remove(edificioListEdificio);
                    oldEspecialistaOfEdificioListEdificio = em.merge(oldEspecialistaOfEdificioListEdificio);
                }
            }
            for (Edificio edificioList1Edificio : trabajador.getEdificioList1()) {
                Trabajador oldInstructorOfEdificioList1Edificio = edificioList1Edificio.getInstructor();
                edificioList1Edificio.setInstructor(trabajador);
                edificioList1Edificio = em.merge(edificioList1Edificio);
                if (oldInstructorOfEdificioList1Edificio != null) {
                    oldInstructorOfEdificioList1Edificio.getEdificioList1().remove(edificioList1Edificio);
                    oldInstructorOfEdificioList1Edificio = em.merge(oldInstructorOfEdificioList1Edificio);
                }
            }
            for (Planteamientos planteamientosListPlanteamientos : trabajador.getPlanteamientosList()) {
                Trabajador oldTrabajadorciOfPlanteamientosListPlanteamientos = planteamientosListPlanteamientos.getTrabajadorci();
                planteamientosListPlanteamientos.setTrabajadorci(trabajador);
                planteamientosListPlanteamientos = em.merge(planteamientosListPlanteamientos);
                if (oldTrabajadorciOfPlanteamientosListPlanteamientos != null) {
                    oldTrabajadorciOfPlanteamientosListPlanteamientos.getPlanteamientosList().remove(planteamientosListPlanteamientos);
                    oldTrabajadorciOfPlanteamientosListPlanteamientos = em.merge(oldTrabajadorciOfPlanteamientosListPlanteamientos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrabajador(trabajador.getCi()) != null) {
                throw new PreexistingEntityException("Trabajador " + trabajador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trabajador trabajador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajador persistentTrabajador = em.find(Trabajador.class, trabajador.getCi());
            List<Evalcuarteleria> evalcuarteleriaListOld = persistentTrabajador.getEvalcuarteleriaList();
            List<Evalcuarteleria> evalcuarteleriaListNew = trabajador.getEvalcuarteleriaList();
            List<Evalguardia> evalguardiaListOld = persistentTrabajador.getEvalguardiaList();
            List<Evalguardia> evalguardiaListNew = trabajador.getEvalguardiaList();
            List<Evalcuarto> evalcuartoListOld = persistentTrabajador.getEvalcuartoList();
            List<Evalcuarto> evalcuartoListNew = trabajador.getEvalcuartoList();
            List<Edificio> edificioListOld = persistentTrabajador.getEdificioList();
            List<Edificio> edificioListNew = trabajador.getEdificioList();
            List<Edificio> edificioList1Old = persistentTrabajador.getEdificioList1();
            List<Edificio> edificioList1New = trabajador.getEdificioList1();
            List<Planteamientos> planteamientosListOld = persistentTrabajador.getPlanteamientosList();
            List<Planteamientos> planteamientosListNew = trabajador.getPlanteamientosList();
            List<String> illegalOrphanMessages = null;
            
            for (Evalcuarteleria evalcuarteleriaListOldEvalcuarteleria : evalcuarteleriaListOld) {
                if (!evalcuarteleriaListNew.contains(evalcuarteleriaListOldEvalcuarteleria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalcuarteleria " + evalcuarteleriaListOldEvalcuarteleria + " since its inspeccionaci field is not nullable.");
                }
            }
            
            for (Evalguardia evalguardiaListOldEvalguardia : evalguardiaListOld) {
                if (!evalguardiaListNew.contains(evalguardiaListOldEvalguardia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalcuarto " + evalguardiaListOldEvalguardia + " since its trabajadorci field is not nullable.");
                }
            }
            
            for (Evalcuarto evalcuartoListOldEvalcuarto : evalcuartoListOld) {
                if (!evalcuartoListNew.contains(evalcuartoListOldEvalcuarto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalcuarto " + evalcuartoListOldEvalcuarto + " since its trabajadorci field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evalcuarteleria> attachedEvalcuarteleriaListNew = new ArrayList<Evalcuarteleria>();
            for (Evalcuarteleria evalcuarteleriaListNewEvalcuarteleriaToAttach : evalcuarteleriaListNew) {
                evalcuarteleriaListNewEvalcuarteleriaToAttach = em.getReference(evalcuarteleriaListNewEvalcuarteleriaToAttach.getClass(), evalcuarteleriaListNewEvalcuarteleriaToAttach.getEvalcuarteleriaPK());
                attachedEvalcuarteleriaListNew.add(evalcuarteleriaListNewEvalcuarteleriaToAttach);
            }
            evalcuarteleriaListNew = attachedEvalcuarteleriaListNew;
            trabajador.setEvalcuarteleriaList(evalcuarteleriaListNew);
            List<Evalcuarto> attachedEvalcuartoListNew = new ArrayList<Evalcuarto>();
            for (Evalcuarto evalcuartoListNewEvalcuartoToAttach : evalcuartoListNew) {
                evalcuartoListNewEvalcuartoToAttach = em.getReference(evalcuartoListNewEvalcuartoToAttach.getClass(), evalcuartoListNewEvalcuartoToAttach.getEvalcuartoPK());
                attachedEvalcuartoListNew.add(evalcuartoListNewEvalcuartoToAttach);
            }
            evalcuartoListNew = attachedEvalcuartoListNew;
            trabajador.setEvalcuartoList(evalcuartoListNew);
            List<Evalguardia> attachedEvalguardiaListNew = new ArrayList<Evalguardia>();
            for (Evalguardia evalguardiaListNewEvalguardiaToAttach : evalguardiaListNew) {
                evalguardiaListNewEvalguardiaToAttach = em.getReference(evalguardiaListNewEvalguardiaToAttach.getClass(), evalguardiaListNewEvalguardiaToAttach.getEvalguardiaPK());
                attachedEvalguardiaListNew.add(evalguardiaListNewEvalguardiaToAttach);
            }
            evalguardiaListNew = attachedEvalguardiaListNew;
            trabajador.setEvalguardiaList(evalguardiaListNew);
            List<Edificio> attachedEdificioListNew = new ArrayList<Edificio>();
            for (Edificio edificioListNewEdificioToAttach : edificioListNew) {
                edificioListNewEdificioToAttach = em.getReference(edificioListNewEdificioToAttach.getClass(), edificioListNewEdificioToAttach.getId());
                attachedEdificioListNew.add(edificioListNewEdificioToAttach);
            }
            edificioListNew = attachedEdificioListNew;
            trabajador.setEdificioList(edificioListNew);
            List<Edificio> attachedEdificioList1New = new ArrayList<Edificio>();
            for (Edificio edificioList1NewEdificioToAttach : edificioList1New) {
                edificioList1NewEdificioToAttach = em.getReference(edificioList1NewEdificioToAttach.getClass(), edificioList1NewEdificioToAttach.getId());
                attachedEdificioList1New.add(edificioList1NewEdificioToAttach);
            }
            edificioList1New = attachedEdificioList1New;
            trabajador.setEdificioList1(edificioList1New);
            List<Planteamientos> attachedPlanteamientosListNew = new ArrayList<Planteamientos>();
            for (Planteamientos planteamientosListNewPlanteamientosToAttach : planteamientosListNew) {
                planteamientosListNewPlanteamientosToAttach = em.getReference(planteamientosListNewPlanteamientosToAttach.getClass(), planteamientosListNewPlanteamientosToAttach.getId());
                attachedPlanteamientosListNew.add(planteamientosListNewPlanteamientosToAttach);
            }
            planteamientosListNew = attachedPlanteamientosListNew;
            trabajador.setPlanteamientosList(planteamientosListNew);
            trabajador = em.merge(trabajador);
            
            for (Evalcuarteleria evalcuarteleriaListNewEvalcuarteleria : evalcuarteleriaListNew) {
                if (!evalcuarteleriaListOld.contains(evalcuarteleriaListNewEvalcuarteleria)) {
                    Trabajador oldInspeccionaciOfEvalcuarteleriaListNewEvalcuarteleria = evalcuarteleriaListNewEvalcuarteleria.getInspeccionaci();
                    evalcuarteleriaListNewEvalcuarteleria.setInspeccionaci(trabajador);
                    evalcuarteleriaListNewEvalcuarteleria = em.merge(evalcuarteleriaListNewEvalcuarteleria);
                    if (oldInspeccionaciOfEvalcuarteleriaListNewEvalcuarteleria != null && !oldInspeccionaciOfEvalcuarteleriaListNewEvalcuarteleria.equals(trabajador)) {
                        oldInspeccionaciOfEvalcuarteleriaListNewEvalcuarteleria.getEvalcuarteleriaList().remove(evalcuarteleriaListNewEvalcuarteleria);
                        oldInspeccionaciOfEvalcuarteleriaListNewEvalcuarteleria = em.merge(oldInspeccionaciOfEvalcuarteleriaListNewEvalcuarteleria);
                    }
                }
            }
            for (Evalguardia evalguardiaListNewEvalguardia : evalguardiaListNew) {
                if (!evalguardiaListOld.contains(evalguardiaListNewEvalguardia)) {
                    Trabajador oldTrabajadorciOfEvalguardiaListNewEvalguardia = evalguardiaListNewEvalguardia.getTrabajadorci();
                    evalguardiaListNewEvalguardia.setTrabajadorci(trabajador);
                    evalguardiaListNewEvalguardia = em.merge(evalguardiaListNewEvalguardia);
                    if (oldTrabajadorciOfEvalguardiaListNewEvalguardia != null && !oldTrabajadorciOfEvalguardiaListNewEvalguardia.equals(trabajador)) {
                        oldTrabajadorciOfEvalguardiaListNewEvalguardia.getEvalguardiaList().remove(evalguardiaListNewEvalguardia);
                        oldTrabajadorciOfEvalguardiaListNewEvalguardia = em.merge(oldTrabajadorciOfEvalguardiaListNewEvalguardia);
                    }
                }
            }
            
            for (Evalcuarto evalcuartoListNewEvalcuarto : evalcuartoListNew) {
                if (!evalcuartoListOld.contains(evalcuartoListNewEvalcuarto)) {
                    Trabajador oldTrabajadorciOfEvalcuartoListNewEvalcuarto = evalcuartoListNewEvalcuarto.getTrabajadorci();
                    evalcuartoListNewEvalcuarto.setTrabajadorci(trabajador);
                    evalcuartoListNewEvalcuarto = em.merge(evalcuartoListNewEvalcuarto);
                    if (oldTrabajadorciOfEvalcuartoListNewEvalcuarto != null && !oldTrabajadorciOfEvalcuartoListNewEvalcuarto.equals(trabajador)) {
                        oldTrabajadorciOfEvalcuartoListNewEvalcuarto.getEvalcuartoList().remove(evalcuartoListNewEvalcuarto);
                        oldTrabajadorciOfEvalcuartoListNewEvalcuarto = em.merge(oldTrabajadorciOfEvalcuartoListNewEvalcuarto);
                    }
                }
            }
            for (Edificio edificioListOldEdificio : edificioListOld) {
                if (!edificioListNew.contains(edificioListOldEdificio)) {
                    edificioListOldEdificio.setEspecialista(null);
                    edificioListOldEdificio = em.merge(edificioListOldEdificio);
                }
            }
            for (Edificio edificioListNewEdificio : edificioListNew) {
                if (!edificioListOld.contains(edificioListNewEdificio)) {
                    Trabajador oldEspecialistaOfEdificioListNewEdificio = edificioListNewEdificio.getEspecialista();
                    edificioListNewEdificio.setEspecialista(trabajador);
                    edificioListNewEdificio = em.merge(edificioListNewEdificio);
                    if (oldEspecialistaOfEdificioListNewEdificio != null && !oldEspecialistaOfEdificioListNewEdificio.equals(trabajador)) {
                        oldEspecialistaOfEdificioListNewEdificio.getEdificioList().remove(edificioListNewEdificio);
                        oldEspecialistaOfEdificioListNewEdificio = em.merge(oldEspecialistaOfEdificioListNewEdificio);
                    }
                }
            }
            for (Edificio edificioList1OldEdificio : edificioList1Old) {
                if (!edificioList1New.contains(edificioList1OldEdificio)) {
                    edificioList1OldEdificio.setInstructor(null);
                    edificioList1OldEdificio = em.merge(edificioList1OldEdificio);
                }
            }
            for (Edificio edificioList1NewEdificio : edificioList1New) {
                if (!edificioList1Old.contains(edificioList1NewEdificio)) {
                    Trabajador oldInstructorOfEdificioList1NewEdificio = edificioList1NewEdificio.getInstructor();
                    edificioList1NewEdificio.setInstructor(trabajador);
                    edificioList1NewEdificio = em.merge(edificioList1NewEdificio);
                    if (oldInstructorOfEdificioList1NewEdificio != null && !oldInstructorOfEdificioList1NewEdificio.equals(trabajador)) {
                        oldInstructorOfEdificioList1NewEdificio.getEdificioList1().remove(edificioList1NewEdificio);
                        oldInstructorOfEdificioList1NewEdificio = em.merge(oldInstructorOfEdificioList1NewEdificio);
                    }
                }
            }
            for (Planteamientos planteamientosListOldPlanteamientos : planteamientosListOld) {
                if (!planteamientosListNew.contains(planteamientosListOldPlanteamientos)) {
                    planteamientosListOldPlanteamientos.setTrabajadorci(null);
                    planteamientosListOldPlanteamientos = em.merge(planteamientosListOldPlanteamientos);
                }
            }
            for (Planteamientos planteamientosListNewPlanteamientos : planteamientosListNew) {
                if (!planteamientosListOld.contains(planteamientosListNewPlanteamientos)) {
                    Trabajador oldTrabajadorciOfPlanteamientosListNewPlanteamientos = planteamientosListNewPlanteamientos.getTrabajadorci();
                    planteamientosListNewPlanteamientos.setTrabajadorci(trabajador);
                    planteamientosListNewPlanteamientos = em.merge(planteamientosListNewPlanteamientos);
                    if (oldTrabajadorciOfPlanteamientosListNewPlanteamientos != null && !oldTrabajadorciOfPlanteamientosListNewPlanteamientos.equals(trabajador)) {
                        oldTrabajadorciOfPlanteamientosListNewPlanteamientos.getPlanteamientosList().remove(planteamientosListNewPlanteamientos);
                        oldTrabajadorciOfPlanteamientosListNewPlanteamientos = em.merge(oldTrabajadorciOfPlanteamientosListNewPlanteamientos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = trabajador.getCi();
                if (findTrabajador(id) == null) {
                    throw new NonexistentEntityException("The trabajador with id " + id + " no longer exists.");
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
            Trabajador trabajador;
            try {
                trabajador = em.getReference(Trabajador.class, id);
                trabajador.getCi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evalcuarteleria> evalcuarteleriaListOrphanCheck = trabajador.getEvalcuarteleriaList();
            for (Evalcuarteleria evalcuarteleriaListOrphanCheckEvalcuarteleria : evalcuarteleriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajador (" + trabajador + ") cannot be destroyed since the Evalcuarteleria " + evalcuarteleriaListOrphanCheckEvalcuarteleria + " in its evalcuarteleriaList field has a non-nullable inspeccionaci field.");
            }
            
            List<Evalguardia> evalguardiaListOrphanCheck = trabajador.getEvalguardiaList();
            for (Evalguardia evalguardiaListOrphanCheckEvalguardia : evalguardiaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajador (" + trabajador + ") cannot be destroyed since the Evalcuarto " + evalguardiaListOrphanCheckEvalguardia + " in its evalcuartoList field has a non-nullable trabajadorci field.");
            }
            
            List<Evalcuarto> evalcuartoListOrphanCheck = trabajador.getEvalcuartoList();
            for (Evalcuarto evalcuartoListOrphanCheckEvalcuarto : evalcuartoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajador (" + trabajador + ") cannot be destroyed since the Evalcuarto " + evalcuartoListOrphanCheckEvalcuarto + " in its evalcuartoList field has a non-nullable trabajadorci field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Edificio> edificioList = trabajador.getEdificioList();
            for (Edificio edificioListEdificio : edificioList) {
                edificioListEdificio.setEspecialista(null);
                edificioListEdificio = em.merge(edificioListEdificio);
            }
            List<Edificio> edificioList1 = trabajador.getEdificioList1();
            for (Edificio edificioList1Edificio : edificioList1) {
                edificioList1Edificio.setInstructor(null);
                edificioList1Edificio = em.merge(edificioList1Edificio);
            }
            List<Planteamientos> planteamientosList = trabajador.getPlanteamientosList();
            for (Planteamientos planteamientosListPlanteamientos : planteamientosList) {
                planteamientosListPlanteamientos.setTrabajadorci(null);
                planteamientosListPlanteamientos = em.merge(planteamientosListPlanteamientos);
            }
            em.remove(trabajador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trabajador> findTrabajadorEntities() {
        return findTrabajadorEntities(true, -1, -1);
    }

    public List<Trabajador> findTrabajadorEntities(int maxResults, int firstResult) {
        return findTrabajadorEntities(false, maxResults, firstResult);
    }

    private List<Trabajador> findTrabajadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajador.class));
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

    public Trabajador findTrabajador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajador> rt = cq.from(Trabajador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
