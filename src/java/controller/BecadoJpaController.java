
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entities.Becado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Cuarto;
import entities.Medicamentos;
import java.util.ArrayList;
import java.util.List;
import entities.Trabajoprod;
import entities.Enfermedades;
import entities.Piso;
import entities.Evalcuarteleria;
import entities.Pacientesatendidos;
import entities.Evalguardia;
import entities.Evalbecado;
import entities.Sanciones;
import entities.Edificio;
import entities.Planteamientos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class BecadoJpaController implements Serializable {

    public BecadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Becado becado) throws PreexistingEntityException, Exception {
        if (becado.getMedicamentosList() == null) {
            becado.setMedicamentosList(new ArrayList<Medicamentos>());
        }
        if (becado.getTrabajoprodList() == null) {
            becado.setTrabajoprodList(new ArrayList<Trabajoprod>());
        }
        if (becado.getEnfermedadesList() == null) {
            becado.setEnfermedadesList(new ArrayList<Enfermedades>());
        }
        if (becado.getPisoList() == null) {
            becado.setPisoList(new ArrayList<Piso>());
        }
        if (becado.getPisoList1() == null) {
            becado.setPisoList1(new ArrayList<Piso>());
        }
        if (becado.getEvalcuarteleriaList() == null) {
            becado.setEvalcuarteleriaList(new ArrayList<Evalcuarteleria>());
        }
        if (becado.getPacientesatendidosList() == null) {
            becado.setPacientesatendidosList(new ArrayList<Pacientesatendidos>());
        }
        if (becado.getEvalguardiaList() == null) {
            becado.setEvalguardiaList(new ArrayList<Evalguardia>());
        }
        if (becado.getEvalbecadoList() == null) {
            becado.setEvalbecadoList(new ArrayList<Evalbecado>());
        }
        if (becado.getSancionesList() == null) {
            becado.setSancionesList(new ArrayList<Sanciones>());
        }
        if (becado.getEdificioList() == null) {
            becado.setEdificioList(new ArrayList<Edificio>());
        }
        if (becado.getPlanteamientosList() == null) {
            becado.setPlanteamientosList(new ArrayList<Planteamientos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuarto cuarto = becado.getCuarto();
            if (cuarto != null) {
                cuarto = em.getReference(cuarto.getClass(), cuarto.getCuartoPK());
                becado.setCuarto(cuarto);
            }
            List<Medicamentos> attachedMedicamentosList = new ArrayList<Medicamentos>();
            for (Medicamentos medicamentosListMedicamentosToAttach : becado.getMedicamentosList()) {
                medicamentosListMedicamentosToAttach = em.getReference(medicamentosListMedicamentosToAttach.getClass(), medicamentosListMedicamentosToAttach.getId());
                attachedMedicamentosList.add(medicamentosListMedicamentosToAttach);
            }
            becado.setMedicamentosList(attachedMedicamentosList);
            List<Trabajoprod> attachedTrabajoprodList = new ArrayList<Trabajoprod>();
            for (Trabajoprod trabajoprodListTrabajoprodToAttach : becado.getTrabajoprodList()) {
                trabajoprodListTrabajoprodToAttach = em.getReference(trabajoprodListTrabajoprodToAttach.getClass(), trabajoprodListTrabajoprodToAttach.getTrabajoprodPK());
                attachedTrabajoprodList.add(trabajoprodListTrabajoprodToAttach);
            }
            becado.setTrabajoprodList(attachedTrabajoprodList);
            List<Enfermedades> attachedEnfermedadesList = new ArrayList<Enfermedades>();
            for (Enfermedades enfermedadesListEnfermedadesToAttach : becado.getEnfermedadesList()) {
                enfermedadesListEnfermedadesToAttach = em.getReference(enfermedadesListEnfermedadesToAttach.getClass(), enfermedadesListEnfermedadesToAttach.getId());
                attachedEnfermedadesList.add(enfermedadesListEnfermedadesToAttach);
            }
            becado.setEnfermedadesList(attachedEnfermedadesList);
            List<Piso> attachedPisoList = new ArrayList<Piso>();
            for (Piso pisoListPisoToAttach : becado.getPisoList()) {
                pisoListPisoToAttach = em.getReference(pisoListPisoToAttach.getClass(), pisoListPisoToAttach.getPisoPK());
                attachedPisoList.add(pisoListPisoToAttach);
            }
            becado.setPisoList(attachedPisoList);
            List<Piso> attachedPisoList1 = new ArrayList<Piso>();
            for (Piso pisoList1PisoToAttach : becado.getPisoList1()) {
                pisoList1PisoToAttach = em.getReference(pisoList1PisoToAttach.getClass(), pisoList1PisoToAttach.getPisoPK());
                attachedPisoList1.add(pisoList1PisoToAttach);
            }
            becado.setPisoList1(attachedPisoList1);
            List<Evalcuarteleria> attachedEvalcuarteleriaList = new ArrayList<Evalcuarteleria>();
            for (Evalcuarteleria evalcuarteleriaListEvalcuarteleriaToAttach : becado.getEvalcuarteleriaList()) {
                evalcuarteleriaListEvalcuarteleriaToAttach = em.getReference(evalcuarteleriaListEvalcuarteleriaToAttach.getClass(), evalcuarteleriaListEvalcuarteleriaToAttach.getEvalcuarteleriaPK());
                attachedEvalcuarteleriaList.add(evalcuarteleriaListEvalcuarteleriaToAttach);
            }
            becado.setEvalcuarteleriaList(attachedEvalcuarteleriaList);
            List<Pacientesatendidos> attachedPacientesatendidosList = new ArrayList<Pacientesatendidos>();
            for (Pacientesatendidos pacientesatendidosListPacientesatendidosToAttach : becado.getPacientesatendidosList()) {
                pacientesatendidosListPacientesatendidosToAttach = em.getReference(pacientesatendidosListPacientesatendidosToAttach.getClass(), pacientesatendidosListPacientesatendidosToAttach.getId());
                attachedPacientesatendidosList.add(pacientesatendidosListPacientesatendidosToAttach);
            }
            becado.setPacientesatendidosList(attachedPacientesatendidosList);
            List<Evalguardia> attachedEvalguardiaList = new ArrayList<Evalguardia>();
            for (Evalguardia evalguardiaListEvalguardiaToAttach : becado.getEvalguardiaList()) {
                evalguardiaListEvalguardiaToAttach = em.getReference(evalguardiaListEvalguardiaToAttach.getClass(), evalguardiaListEvalguardiaToAttach.getEvalguardiaPK());
                attachedEvalguardiaList.add(evalguardiaListEvalguardiaToAttach);
            }
            becado.setEvalguardiaList(attachedEvalguardiaList);
            List<Evalbecado> attachedEvalbecadoList = new ArrayList<Evalbecado>();
            for (Evalbecado evalbecadoListEvalbecadoToAttach : becado.getEvalbecadoList()) {
                evalbecadoListEvalbecadoToAttach = em.getReference(evalbecadoListEvalbecadoToAttach.getClass(), evalbecadoListEvalbecadoToAttach.getEvalbecadoPK());
                attachedEvalbecadoList.add(evalbecadoListEvalbecadoToAttach);
            }
            becado.setEvalbecadoList(attachedEvalbecadoList);
            List<Sanciones> attachedSancionesList = new ArrayList<Sanciones>();
            for (Sanciones sancionesListSancionesToAttach : becado.getSancionesList()) {
                sancionesListSancionesToAttach = em.getReference(sancionesListSancionesToAttach.getClass(), sancionesListSancionesToAttach.getSancionesPK());
                attachedSancionesList.add(sancionesListSancionesToAttach);
            }
            becado.setSancionesList(attachedSancionesList);
            List<Edificio> attachedEdificioList = new ArrayList<Edificio>();
            for (Edificio edificioListEdificioToAttach : becado.getEdificioList()) {
                edificioListEdificioToAttach = em.getReference(edificioListEdificioToAttach.getClass(), edificioListEdificioToAttach.getId());
                attachedEdificioList.add(edificioListEdificioToAttach);
            }
            becado.setEdificioList(attachedEdificioList);
            List<Planteamientos> attachedPlanteamientosList = new ArrayList<Planteamientos>();
            for (Planteamientos planteamientosListPlanteamientosToAttach : becado.getPlanteamientosList()) {
                planteamientosListPlanteamientosToAttach = em.getReference(planteamientosListPlanteamientosToAttach.getClass(), planteamientosListPlanteamientosToAttach.getId());
                attachedPlanteamientosList.add(planteamientosListPlanteamientosToAttach);
            }
            becado.setPlanteamientosList(attachedPlanteamientosList);
            em.persist(becado);
            if (cuarto != null) {
                cuarto.getBecadoList().add(becado);
                cuarto = em.merge(cuarto);
            }
            for (Medicamentos medicamentosListMedicamentos : becado.getMedicamentosList()) {
                medicamentosListMedicamentos.getBecadoList().add(becado);
                medicamentosListMedicamentos = em.merge(medicamentosListMedicamentos);
            }
            for (Trabajoprod trabajoprodListTrabajoprod : becado.getTrabajoprodList()) {
                trabajoprodListTrabajoprod.getBecadoList().add(becado);
                trabajoprodListTrabajoprod = em.merge(trabajoprodListTrabajoprod);
            }
            for (Enfermedades enfermedadesListEnfermedades : becado.getEnfermedadesList()) {
                enfermedadesListEnfermedades.getBecadoList().add(becado);
                enfermedadesListEnfermedades = em.merge(enfermedadesListEnfermedades);
            }
            for (Piso pisoListPiso : becado.getPisoList()) {
                Becado oldJefepisoOfPisoListPiso = pisoListPiso.getJefepiso();
                pisoListPiso.setJefepiso(becado);
                pisoListPiso = em.merge(pisoListPiso);
                if (oldJefepisoOfPisoListPiso != null) {
                    oldJefepisoOfPisoListPiso.getPisoList().remove(pisoListPiso);
                    oldJefepisoOfPisoListPiso = em.merge(oldJefepisoOfPisoListPiso);
                }
            }
            for (Piso pisoList1Piso : becado.getPisoList1()) {
                Becado oldJefelimpOfPisoList1Piso = pisoList1Piso.getJefelimp();
                pisoList1Piso.setJefelimp(becado);
                pisoList1Piso = em.merge(pisoList1Piso);
                if (oldJefelimpOfPisoList1Piso != null) {
                    oldJefelimpOfPisoList1Piso.getPisoList1().remove(pisoList1Piso);
                    oldJefelimpOfPisoList1Piso = em.merge(oldJefelimpOfPisoList1Piso);
                }
            }
            for (Evalcuarteleria evalcuarteleriaListEvalcuarteleria : becado.getEvalcuarteleriaList()) {
                Becado oldBecadoOfEvalcuarteleriaListEvalcuarteleria = evalcuarteleriaListEvalcuarteleria.getBecado();
                evalcuarteleriaListEvalcuarteleria.setBecado(becado);
                evalcuarteleriaListEvalcuarteleria = em.merge(evalcuarteleriaListEvalcuarteleria);
                if (oldBecadoOfEvalcuarteleriaListEvalcuarteleria != null) {
                    oldBecadoOfEvalcuarteleriaListEvalcuarteleria.getEvalcuarteleriaList().remove(evalcuarteleriaListEvalcuarteleria);
                    oldBecadoOfEvalcuarteleriaListEvalcuarteleria = em.merge(oldBecadoOfEvalcuarteleriaListEvalcuarteleria);
                }
            }
            for (Pacientesatendidos pacientesatendidosListPacientesatendidos : becado.getPacientesatendidosList()) {
                Becado oldBecadociOfPacientesatendidosListPacientesatendidos = pacientesatendidosListPacientesatendidos.getBecadoci();
                pacientesatendidosListPacientesatendidos.setBecadoci(becado);
                pacientesatendidosListPacientesatendidos = em.merge(pacientesatendidosListPacientesatendidos);
                if (oldBecadociOfPacientesatendidosListPacientesatendidos != null) {
                    oldBecadociOfPacientesatendidosListPacientesatendidos.getPacientesatendidosList().remove(pacientesatendidosListPacientesatendidos);
                    oldBecadociOfPacientesatendidosListPacientesatendidos = em.merge(oldBecadociOfPacientesatendidosListPacientesatendidos);
                }
            }
            for (Evalguardia evalguardiaListEvalguardia : becado.getEvalguardiaList()) {
                Becado oldBecadoOfEvalguardiaListEvalguardia = evalguardiaListEvalguardia.getBecado();
                evalguardiaListEvalguardia.setBecado(becado);
                evalguardiaListEvalguardia = em.merge(evalguardiaListEvalguardia);
                if (oldBecadoOfEvalguardiaListEvalguardia != null) {
                    oldBecadoOfEvalguardiaListEvalguardia.getEvalguardiaList().remove(evalguardiaListEvalguardia);
                    oldBecadoOfEvalguardiaListEvalguardia = em.merge(oldBecadoOfEvalguardiaListEvalguardia);
                }
            }
            for (Evalbecado evalbecadoListEvalbecado : becado.getEvalbecadoList()) {
                Becado oldBecadoOfEvalbecadoListEvalbecado = evalbecadoListEvalbecado.getBecado();
                evalbecadoListEvalbecado.setBecado(becado);
                evalbecadoListEvalbecado = em.merge(evalbecadoListEvalbecado);
                if (oldBecadoOfEvalbecadoListEvalbecado != null) {
                    oldBecadoOfEvalbecadoListEvalbecado.getEvalbecadoList().remove(evalbecadoListEvalbecado);
                    oldBecadoOfEvalbecadoListEvalbecado = em.merge(oldBecadoOfEvalbecadoListEvalbecado);
                }
            }
            for (Sanciones sancionesListSanciones : becado.getSancionesList()) {
                Becado oldBecadoOfSancionesListSanciones = sancionesListSanciones.getBecado();
                sancionesListSanciones.setBecado(becado);
                sancionesListSanciones = em.merge(sancionesListSanciones);
                if (oldBecadoOfSancionesListSanciones != null) {
                    oldBecadoOfSancionesListSanciones.getSancionesList().remove(sancionesListSanciones);
                    oldBecadoOfSancionesListSanciones = em.merge(oldBecadoOfSancionesListSanciones);
                }
            }
            for (Edificio edificioListEdificio : becado.getEdificioList()) {
                Becado oldJefeedifOfEdificioListEdificio = edificioListEdificio.getJefeedif();
                edificioListEdificio.setJefeedif(becado);
                edificioListEdificio = em.merge(edificioListEdificio);
                if (oldJefeedifOfEdificioListEdificio != null) {
                    oldJefeedifOfEdificioListEdificio.getEdificioList().remove(edificioListEdificio);
                    oldJefeedifOfEdificioListEdificio = em.merge(oldJefeedifOfEdificioListEdificio);
                }
            }
            for (Planteamientos planteamientosListPlanteamientos : becado.getPlanteamientosList()) {
                Becado oldBecadociOfPlanteamientosListPlanteamientos = planteamientosListPlanteamientos.getBecadoci();
                planteamientosListPlanteamientos.setBecadoci(becado);
                planteamientosListPlanteamientos = em.merge(planteamientosListPlanteamientos);
                if (oldBecadociOfPlanteamientosListPlanteamientos != null) {
                    oldBecadociOfPlanteamientosListPlanteamientos.getPlanteamientosList().remove(planteamientosListPlanteamientos);
                    oldBecadociOfPlanteamientosListPlanteamientos = em.merge(oldBecadociOfPlanteamientosListPlanteamientos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBecado(becado.getCi()) != null) {
                throw new PreexistingEntityException("Becado " + becado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Becado becado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Becado persistentBecado = em.find(Becado.class, becado.getCi());
            Cuarto cuartoOld = persistentBecado.getCuarto();
            Cuarto cuartoNew = becado.getCuarto();
            List<Medicamentos> medicamentosListOld = persistentBecado.getMedicamentosList();
            List<Medicamentos> medicamentosListNew = becado.getMedicamentosList();
            List<Trabajoprod> trabajoprodListOld = persistentBecado.getTrabajoprodList();
            List<Trabajoprod> trabajoprodListNew = becado.getTrabajoprodList();
            List<Enfermedades> enfermedadesListOld = persistentBecado.getEnfermedadesList();
            List<Enfermedades> enfermedadesListNew = becado.getEnfermedadesList();
            List<Piso> pisoListOld = persistentBecado.getPisoList();
            List<Piso> pisoListNew = becado.getPisoList();
            List<Piso> pisoList1Old = persistentBecado.getPisoList1();
            List<Piso> pisoList1New = becado.getPisoList1();
            List<Evalcuarteleria> evalcuarteleriaListOld = persistentBecado.getEvalcuarteleriaList();
            List<Evalcuarteleria> evalcuarteleriaListNew = becado.getEvalcuarteleriaList();
            List<Pacientesatendidos> pacientesatendidosListOld = persistentBecado.getPacientesatendidosList();
            List<Pacientesatendidos> pacientesatendidosListNew = becado.getPacientesatendidosList();
            List<Evalguardia> evalguardiaListOld = persistentBecado.getEvalguardiaList();
            List<Evalguardia> evalguardiaListNew = becado.getEvalguardiaList();
            List<Evalbecado> evalbecadoListOld = persistentBecado.getEvalbecadoList();
            List<Evalbecado> evalbecadoListNew = becado.getEvalbecadoList();
            List<Sanciones> sancionesListOld = persistentBecado.getSancionesList();
            List<Sanciones> sancionesListNew = becado.getSancionesList();
            List<Edificio> edificioListOld = persistentBecado.getEdificioList();
            List<Edificio> edificioListNew = becado.getEdificioList();
            List<Planteamientos> planteamientosListOld = persistentBecado.getPlanteamientosList();
            List<Planteamientos> planteamientosListNew = becado.getPlanteamientosList();
            List<String> illegalOrphanMessages = null;
            for (Evalcuarteleria evalcuarteleriaListOldEvalcuarteleria : evalcuarteleriaListOld) {
                if (!evalcuarteleriaListNew.contains(evalcuarteleriaListOldEvalcuarteleria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalcuarteleria " + evalcuarteleriaListOldEvalcuarteleria + " since its becado field is not nullable.");
                }
            }
            for (Pacientesatendidos pacientesatendidosListOldPacientesatendidos : pacientesatendidosListOld) {
                if (!pacientesatendidosListNew.contains(pacientesatendidosListOldPacientesatendidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacientesatendidos " + pacientesatendidosListOldPacientesatendidos + " since its becadoci field is not nullable.");
                }
            }
            for (Evalguardia evalguardiaListOldEvalguardia : evalguardiaListOld) {
                if (!evalguardiaListNew.contains(evalguardiaListOldEvalguardia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalguardia " + evalguardiaListOldEvalguardia + " since its becado field is not nullable.");
                }
            }
            for (Evalbecado evalbecadoListOldEvalbecado : evalbecadoListOld) {
                if (!evalbecadoListNew.contains(evalbecadoListOldEvalbecado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evalbecado " + evalbecadoListOldEvalbecado + " since its becado field is not nullable.");
                }
            }
            for (Sanciones sancionesListOldSanciones : sancionesListOld) {
                if (!sancionesListNew.contains(sancionesListOldSanciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sanciones " + sancionesListOldSanciones + " since its becado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuartoNew != null) {
                cuartoNew = em.getReference(cuartoNew.getClass(), cuartoNew.getCuartoPK());
                becado.setCuarto(cuartoNew);
            }
            List<Medicamentos> attachedMedicamentosListNew = new ArrayList<Medicamentos>();
            for (Medicamentos medicamentosListNewMedicamentosToAttach : medicamentosListNew) {
                medicamentosListNewMedicamentosToAttach = em.getReference(medicamentosListNewMedicamentosToAttach.getClass(), medicamentosListNewMedicamentosToAttach.getId());
                attachedMedicamentosListNew.add(medicamentosListNewMedicamentosToAttach);
            }
            medicamentosListNew = attachedMedicamentosListNew;
            becado.setMedicamentosList(medicamentosListNew);
            List<Trabajoprod> attachedTrabajoprodListNew = new ArrayList<Trabajoprod>();
            for (Trabajoprod trabajoprodListNewTrabajoprodToAttach : trabajoprodListNew) {
                trabajoprodListNewTrabajoprodToAttach = em.getReference(trabajoprodListNewTrabajoprodToAttach.getClass(), trabajoprodListNewTrabajoprodToAttach.getTrabajoprodPK());
                attachedTrabajoprodListNew.add(trabajoprodListNewTrabajoprodToAttach);
            }
            trabajoprodListNew = attachedTrabajoprodListNew;
            becado.setTrabajoprodList(trabajoprodListNew);
            List<Enfermedades> attachedEnfermedadesListNew = new ArrayList<Enfermedades>();
            for (Enfermedades enfermedadesListNewEnfermedadesToAttach : enfermedadesListNew) {
                enfermedadesListNewEnfermedadesToAttach = em.getReference(enfermedadesListNewEnfermedadesToAttach.getClass(), enfermedadesListNewEnfermedadesToAttach.getId());
                attachedEnfermedadesListNew.add(enfermedadesListNewEnfermedadesToAttach);
            }
            enfermedadesListNew = attachedEnfermedadesListNew;
            becado.setEnfermedadesList(enfermedadesListNew);
            List<Piso> attachedPisoListNew = new ArrayList<Piso>();
            for (Piso pisoListNewPisoToAttach : pisoListNew) {
                pisoListNewPisoToAttach = em.getReference(pisoListNewPisoToAttach.getClass(), pisoListNewPisoToAttach.getPisoPK());
                attachedPisoListNew.add(pisoListNewPisoToAttach);
            }
            pisoListNew = attachedPisoListNew;
            becado.setPisoList(pisoListNew);
            List<Piso> attachedPisoList1New = new ArrayList<Piso>();
            for (Piso pisoList1NewPisoToAttach : pisoList1New) {
                pisoList1NewPisoToAttach = em.getReference(pisoList1NewPisoToAttach.getClass(), pisoList1NewPisoToAttach.getPisoPK());
                attachedPisoList1New.add(pisoList1NewPisoToAttach);
            }
            pisoList1New = attachedPisoList1New;
            becado.setPisoList1(pisoList1New);
            List<Evalcuarteleria> attachedEvalcuarteleriaListNew = new ArrayList<Evalcuarteleria>();
            for (Evalcuarteleria evalcuarteleriaListNewEvalcuarteleriaToAttach : evalcuarteleriaListNew) {
                evalcuarteleriaListNewEvalcuarteleriaToAttach = em.getReference(evalcuarteleriaListNewEvalcuarteleriaToAttach.getClass(), evalcuarteleriaListNewEvalcuarteleriaToAttach.getEvalcuarteleriaPK());
                attachedEvalcuarteleriaListNew.add(evalcuarteleriaListNewEvalcuarteleriaToAttach);
            }
            evalcuarteleriaListNew = attachedEvalcuarteleriaListNew;
            becado.setEvalcuarteleriaList(evalcuarteleriaListNew);
            List<Pacientesatendidos> attachedPacientesatendidosListNew = new ArrayList<Pacientesatendidos>();
            for (Pacientesatendidos pacientesatendidosListNewPacientesatendidosToAttach : pacientesatendidosListNew) {
                pacientesatendidosListNewPacientesatendidosToAttach = em.getReference(pacientesatendidosListNewPacientesatendidosToAttach.getClass(), pacientesatendidosListNewPacientesatendidosToAttach.getId());
                attachedPacientesatendidosListNew.add(pacientesatendidosListNewPacientesatendidosToAttach);
            }
            pacientesatendidosListNew = attachedPacientesatendidosListNew;
            becado.setPacientesatendidosList(pacientesatendidosListNew);
            List<Evalguardia> attachedEvalguardiaListNew = new ArrayList<Evalguardia>();
            for (Evalguardia evalguardiaListNewEvalguardiaToAttach : evalguardiaListNew) {
                evalguardiaListNewEvalguardiaToAttach = em.getReference(evalguardiaListNewEvalguardiaToAttach.getClass(), evalguardiaListNewEvalguardiaToAttach.getEvalguardiaPK());
                attachedEvalguardiaListNew.add(evalguardiaListNewEvalguardiaToAttach);
            }
            evalguardiaListNew = attachedEvalguardiaListNew;
            becado.setEvalguardiaList(evalguardiaListNew);
            List<Evalbecado> attachedEvalbecadoListNew = new ArrayList<Evalbecado>();
            for (Evalbecado evalbecadoListNewEvalbecadoToAttach : evalbecadoListNew) {
                evalbecadoListNewEvalbecadoToAttach = em.getReference(evalbecadoListNewEvalbecadoToAttach.getClass(), evalbecadoListNewEvalbecadoToAttach.getEvalbecadoPK());
                attachedEvalbecadoListNew.add(evalbecadoListNewEvalbecadoToAttach);
            }
            evalbecadoListNew = attachedEvalbecadoListNew;
            becado.setEvalbecadoList(evalbecadoListNew);
            List<Sanciones> attachedSancionesListNew = new ArrayList<Sanciones>();
            for (Sanciones sancionesListNewSancionesToAttach : sancionesListNew) {
                sancionesListNewSancionesToAttach = em.getReference(sancionesListNewSancionesToAttach.getClass(), sancionesListNewSancionesToAttach.getSancionesPK());
                attachedSancionesListNew.add(sancionesListNewSancionesToAttach);
            }
            sancionesListNew = attachedSancionesListNew;
            becado.setSancionesList(sancionesListNew);
            List<Edificio> attachedEdificioListNew = new ArrayList<Edificio>();
            for (Edificio edificioListNewEdificioToAttach : edificioListNew) {
                edificioListNewEdificioToAttach = em.getReference(edificioListNewEdificioToAttach.getClass(), edificioListNewEdificioToAttach.getId());
                attachedEdificioListNew.add(edificioListNewEdificioToAttach);
            }
            edificioListNew = attachedEdificioListNew;
            becado.setEdificioList(edificioListNew);
            List<Planteamientos> attachedPlanteamientosListNew = new ArrayList<Planteamientos>();
            for (Planteamientos planteamientosListNewPlanteamientosToAttach : planteamientosListNew) {
                planteamientosListNewPlanteamientosToAttach = em.getReference(planteamientosListNewPlanteamientosToAttach.getClass(), planteamientosListNewPlanteamientosToAttach.getId());
                attachedPlanteamientosListNew.add(planteamientosListNewPlanteamientosToAttach);
            }
            planteamientosListNew = attachedPlanteamientosListNew;
            becado.setPlanteamientosList(planteamientosListNew);
            becado = em.merge(becado);
            if (cuartoOld != null && !cuartoOld.equals(cuartoNew)) {
                cuartoOld.getBecadoList().remove(becado);
                cuartoOld = em.merge(cuartoOld);
            }
            if (cuartoNew != null && !cuartoNew.equals(cuartoOld)) {
                cuartoNew.getBecadoList().add(becado);
                cuartoNew = em.merge(cuartoNew);
            }
            for (Medicamentos medicamentosListOldMedicamentos : medicamentosListOld) {
                if (!medicamentosListNew.contains(medicamentosListOldMedicamentos)) {
                    medicamentosListOldMedicamentos.getBecadoList().remove(becado);
                    medicamentosListOldMedicamentos = em.merge(medicamentosListOldMedicamentos);
                }
            }
            for (Medicamentos medicamentosListNewMedicamentos : medicamentosListNew) {
                if (!medicamentosListOld.contains(medicamentosListNewMedicamentos)) {
                    medicamentosListNewMedicamentos.getBecadoList().add(becado);
                    medicamentosListNewMedicamentos = em.merge(medicamentosListNewMedicamentos);
                }
            }
            for (Trabajoprod trabajoprodListOldTrabajoprod : trabajoprodListOld) {
                if (!trabajoprodListNew.contains(trabajoprodListOldTrabajoprod)) {
                    trabajoprodListOldTrabajoprod.getBecadoList().remove(becado);
                    trabajoprodListOldTrabajoprod = em.merge(trabajoprodListOldTrabajoprod);
                }
            }
            for (Trabajoprod trabajoprodListNewTrabajoprod : trabajoprodListNew) {
                if (!trabajoprodListOld.contains(trabajoprodListNewTrabajoprod)) {
                    trabajoprodListNewTrabajoprod.getBecadoList().add(becado);
                    trabajoprodListNewTrabajoprod = em.merge(trabajoprodListNewTrabajoprod);
                }
            }
            for (Enfermedades enfermedadesListOldEnfermedades : enfermedadesListOld) {
                if (!enfermedadesListNew.contains(enfermedadesListOldEnfermedades)) {
                    enfermedadesListOldEnfermedades.getBecadoList().remove(becado);
                    enfermedadesListOldEnfermedades = em.merge(enfermedadesListOldEnfermedades);
                }
            }
            for (Enfermedades enfermedadesListNewEnfermedades : enfermedadesListNew) {
                if (!enfermedadesListOld.contains(enfermedadesListNewEnfermedades)) {
                    enfermedadesListNewEnfermedades.getBecadoList().add(becado);
                    enfermedadesListNewEnfermedades = em.merge(enfermedadesListNewEnfermedades);
                }
            }
            for (Piso pisoListOldPiso : pisoListOld) {
                if (!pisoListNew.contains(pisoListOldPiso)) {
                    pisoListOldPiso.setJefepiso(null);
                    pisoListOldPiso = em.merge(pisoListOldPiso);
                }
            }
            for (Piso pisoListNewPiso : pisoListNew) {
                if (!pisoListOld.contains(pisoListNewPiso)) {
                    Becado oldJefepisoOfPisoListNewPiso = pisoListNewPiso.getJefepiso();
                    pisoListNewPiso.setJefepiso(becado);
                    pisoListNewPiso = em.merge(pisoListNewPiso);
                    if (oldJefepisoOfPisoListNewPiso != null && !oldJefepisoOfPisoListNewPiso.equals(becado)) {
                        oldJefepisoOfPisoListNewPiso.getPisoList().remove(pisoListNewPiso);
                        oldJefepisoOfPisoListNewPiso = em.merge(oldJefepisoOfPisoListNewPiso);
                    }
                }
            }
            for (Piso pisoList1OldPiso : pisoList1Old) {
                if (!pisoList1New.contains(pisoList1OldPiso)) {
                    pisoList1OldPiso.setJefelimp(null);
                    pisoList1OldPiso = em.merge(pisoList1OldPiso);
                }
            }
            for (Piso pisoList1NewPiso : pisoList1New) {
                if (!pisoList1Old.contains(pisoList1NewPiso)) {
                    Becado oldJefelimpOfPisoList1NewPiso = pisoList1NewPiso.getJefelimp();
                    pisoList1NewPiso.setJefelimp(becado);
                    pisoList1NewPiso = em.merge(pisoList1NewPiso);
                    if (oldJefelimpOfPisoList1NewPiso != null && !oldJefelimpOfPisoList1NewPiso.equals(becado)) {
                        oldJefelimpOfPisoList1NewPiso.getPisoList1().remove(pisoList1NewPiso);
                        oldJefelimpOfPisoList1NewPiso = em.merge(oldJefelimpOfPisoList1NewPiso);
                    }
                }
            }
            for (Evalcuarteleria evalcuarteleriaListNewEvalcuarteleria : evalcuarteleriaListNew) {
                if (!evalcuarteleriaListOld.contains(evalcuarteleriaListNewEvalcuarteleria)) {
                    Becado oldBecadoOfEvalcuarteleriaListNewEvalcuarteleria = evalcuarteleriaListNewEvalcuarteleria.getBecado();
                    evalcuarteleriaListNewEvalcuarteleria.setBecado(becado);
                    evalcuarteleriaListNewEvalcuarteleria = em.merge(evalcuarteleriaListNewEvalcuarteleria);
                    if (oldBecadoOfEvalcuarteleriaListNewEvalcuarteleria != null && !oldBecadoOfEvalcuarteleriaListNewEvalcuarteleria.equals(becado)) {
                        oldBecadoOfEvalcuarteleriaListNewEvalcuarteleria.getEvalcuarteleriaList().remove(evalcuarteleriaListNewEvalcuarteleria);
                        oldBecadoOfEvalcuarteleriaListNewEvalcuarteleria = em.merge(oldBecadoOfEvalcuarteleriaListNewEvalcuarteleria);
                    }
                }
            }
            for (Pacientesatendidos pacientesatendidosListNewPacientesatendidos : pacientesatendidosListNew) {
                if (!pacientesatendidosListOld.contains(pacientesatendidosListNewPacientesatendidos)) {
                    Becado oldBecadociOfPacientesatendidosListNewPacientesatendidos = pacientesatendidosListNewPacientesatendidos.getBecadoci();
                    pacientesatendidosListNewPacientesatendidos.setBecadoci(becado);
                    pacientesatendidosListNewPacientesatendidos = em.merge(pacientesatendidosListNewPacientesatendidos);
                    if (oldBecadociOfPacientesatendidosListNewPacientesatendidos != null && !oldBecadociOfPacientesatendidosListNewPacientesatendidos.equals(becado)) {
                        oldBecadociOfPacientesatendidosListNewPacientesatendidos.getPacientesatendidosList().remove(pacientesatendidosListNewPacientesatendidos);
                        oldBecadociOfPacientesatendidosListNewPacientesatendidos = em.merge(oldBecadociOfPacientesatendidosListNewPacientesatendidos);
                    }
                }
            }
            for (Evalguardia evalguardiaListNewEvalguardia : evalguardiaListNew) {
                if (!evalguardiaListOld.contains(evalguardiaListNewEvalguardia)) {
                    Becado oldBecadoOfEvalguardiaListNewEvalguardia = evalguardiaListNewEvalguardia.getBecado();
                    evalguardiaListNewEvalguardia.setBecado(becado);
                    evalguardiaListNewEvalguardia = em.merge(evalguardiaListNewEvalguardia);
                    if (oldBecadoOfEvalguardiaListNewEvalguardia != null && !oldBecadoOfEvalguardiaListNewEvalguardia.equals(becado)) {
                        oldBecadoOfEvalguardiaListNewEvalguardia.getEvalguardiaList().remove(evalguardiaListNewEvalguardia);
                        oldBecadoOfEvalguardiaListNewEvalguardia = em.merge(oldBecadoOfEvalguardiaListNewEvalguardia);
                    }
                }
            }
            for (Evalbecado evalbecadoListNewEvalbecado : evalbecadoListNew) {
                if (!evalbecadoListOld.contains(evalbecadoListNewEvalbecado)) {
                    Becado oldBecadoOfEvalbecadoListNewEvalbecado = evalbecadoListNewEvalbecado.getBecado();
                    evalbecadoListNewEvalbecado.setBecado(becado);
                    evalbecadoListNewEvalbecado = em.merge(evalbecadoListNewEvalbecado);
                    if (oldBecadoOfEvalbecadoListNewEvalbecado != null && !oldBecadoOfEvalbecadoListNewEvalbecado.equals(becado)) {
                        oldBecadoOfEvalbecadoListNewEvalbecado.getEvalbecadoList().remove(evalbecadoListNewEvalbecado);
                        oldBecadoOfEvalbecadoListNewEvalbecado = em.merge(oldBecadoOfEvalbecadoListNewEvalbecado);
                    }
                }
            }
            for (Sanciones sancionesListNewSanciones : sancionesListNew) {
                if (!sancionesListOld.contains(sancionesListNewSanciones)) {
                    Becado oldBecadoOfSancionesListNewSanciones = sancionesListNewSanciones.getBecado();
                    sancionesListNewSanciones.setBecado(becado);
                    sancionesListNewSanciones = em.merge(sancionesListNewSanciones);
                    if (oldBecadoOfSancionesListNewSanciones != null && !oldBecadoOfSancionesListNewSanciones.equals(becado)) {
                        oldBecadoOfSancionesListNewSanciones.getSancionesList().remove(sancionesListNewSanciones);
                        oldBecadoOfSancionesListNewSanciones = em.merge(oldBecadoOfSancionesListNewSanciones);
                    }
                }
            }
            for (Edificio edificioListOldEdificio : edificioListOld) {
                if (!edificioListNew.contains(edificioListOldEdificio)) {
                    edificioListOldEdificio.setJefeedif(null);
                    edificioListOldEdificio = em.merge(edificioListOldEdificio);
                }
            }
            for (Edificio edificioListNewEdificio : edificioListNew) {
                if (!edificioListOld.contains(edificioListNewEdificio)) {
                    Becado oldJefeedifOfEdificioListNewEdificio = edificioListNewEdificio.getJefeedif();
                    edificioListNewEdificio.setJefeedif(becado);
                    edificioListNewEdificio = em.merge(edificioListNewEdificio);
                    if (oldJefeedifOfEdificioListNewEdificio != null && !oldJefeedifOfEdificioListNewEdificio.equals(becado)) {
                        oldJefeedifOfEdificioListNewEdificio.getEdificioList().remove(edificioListNewEdificio);
                        oldJefeedifOfEdificioListNewEdificio = em.merge(oldJefeedifOfEdificioListNewEdificio);
                    }
                }
            }
            for (Planteamientos planteamientosListOldPlanteamientos : planteamientosListOld) {
                if (!planteamientosListNew.contains(planteamientosListOldPlanteamientos)) {
                    planteamientosListOldPlanteamientos.setBecadoci(null);
                    planteamientosListOldPlanteamientos = em.merge(planteamientosListOldPlanteamientos);
                }
            }
            for (Planteamientos planteamientosListNewPlanteamientos : planteamientosListNew) {
                if (!planteamientosListOld.contains(planteamientosListNewPlanteamientos)) {
                    Becado oldBecadociOfPlanteamientosListNewPlanteamientos = planteamientosListNewPlanteamientos.getBecadoci();
                    planteamientosListNewPlanteamientos.setBecadoci(becado);
                    planteamientosListNewPlanteamientos = em.merge(planteamientosListNewPlanteamientos);
                    if (oldBecadociOfPlanteamientosListNewPlanteamientos != null && !oldBecadociOfPlanteamientosListNewPlanteamientos.equals(becado)) {
                        oldBecadociOfPlanteamientosListNewPlanteamientos.getPlanteamientosList().remove(planteamientosListNewPlanteamientos);
                        oldBecadociOfPlanteamientosListNewPlanteamientos = em.merge(oldBecadociOfPlanteamientosListNewPlanteamientos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = becado.getCi();
                if (findBecado(id) == null) {
                    throw new NonexistentEntityException("The becado with id " + id + " no longer exists.");
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
            Becado becado;
            try {
                becado = em.getReference(Becado.class, id);
                becado.getCi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The becado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evalcuarteleria> evalcuarteleriaListOrphanCheck = becado.getEvalcuarteleriaList();
            for (Evalcuarteleria evalcuarteleriaListOrphanCheckEvalcuarteleria : evalcuarteleriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Becado (" + becado + ") cannot be destroyed since the Evalcuarteleria " + evalcuarteleriaListOrphanCheckEvalcuarteleria + " in its evalcuarteleriaList field has a non-nullable becado field.");
            }
            List<Pacientesatendidos> pacientesatendidosListOrphanCheck = becado.getPacientesatendidosList();
            for (Pacientesatendidos pacientesatendidosListOrphanCheckPacientesatendidos : pacientesatendidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Becado (" + becado + ") cannot be destroyed since the Pacientesatendidos " + pacientesatendidosListOrphanCheckPacientesatendidos + " in its pacientesatendidosList field has a non-nullable becadoci field.");
            }
            List<Evalguardia> evalguardiaListOrphanCheck = becado.getEvalguardiaList();
            for (Evalguardia evalguardiaListOrphanCheckEvalguardia : evalguardiaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Becado (" + becado + ") cannot be destroyed since the Evalguardia " + evalguardiaListOrphanCheckEvalguardia + " in its evalguardiaList field has a non-nullable becado field.");
            }
            List<Evalbecado> evalbecadoListOrphanCheck = becado.getEvalbecadoList();
            for (Evalbecado evalbecadoListOrphanCheckEvalbecado : evalbecadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Becado (" + becado + ") cannot be destroyed since the Evalbecado " + evalbecadoListOrphanCheckEvalbecado + " in its evalbecadoList field has a non-nullable becado field.");
            }
            List<Sanciones> sancionesListOrphanCheck = becado.getSancionesList();
            for (Sanciones sancionesListOrphanCheckSanciones : sancionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Becado (" + becado + ") cannot be destroyed since the Sanciones " + sancionesListOrphanCheckSanciones + " in its sancionesList field has a non-nullable becado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cuarto cuarto = becado.getCuarto();
            if (cuarto != null) {
                cuarto.getBecadoList().remove(becado);
                cuarto = em.merge(cuarto);
            }
            List<Medicamentos> medicamentosList = becado.getMedicamentosList();
            for (Medicamentos medicamentosListMedicamentos : medicamentosList) {
                medicamentosListMedicamentos.getBecadoList().remove(becado);
                medicamentosListMedicamentos = em.merge(medicamentosListMedicamentos);
            }
            List<Trabajoprod> trabajoprodList = becado.getTrabajoprodList();
            for (Trabajoprod trabajoprodListTrabajoprod : trabajoprodList) {
                trabajoprodListTrabajoprod.getBecadoList().remove(becado);
                trabajoprodListTrabajoprod = em.merge(trabajoprodListTrabajoprod);
            }
            List<Enfermedades> enfermedadesList = becado.getEnfermedadesList();
            for (Enfermedades enfermedadesListEnfermedades : enfermedadesList) {
                enfermedadesListEnfermedades.getBecadoList().remove(becado);
                enfermedadesListEnfermedades = em.merge(enfermedadesListEnfermedades);
            }
            List<Piso> pisoList = becado.getPisoList();
            for (Piso pisoListPiso : pisoList) {
                pisoListPiso.setJefepiso(null);
                pisoListPiso = em.merge(pisoListPiso);
            }
            List<Piso> pisoList1 = becado.getPisoList1();
            for (Piso pisoList1Piso : pisoList1) {
                pisoList1Piso.setJefelimp(null);
                pisoList1Piso = em.merge(pisoList1Piso);
            }
            List<Edificio> edificioList = becado.getEdificioList();
            for (Edificio edificioListEdificio : edificioList) {
                edificioListEdificio.setJefeedif(null);
                edificioListEdificio = em.merge(edificioListEdificio);
            }
            List<Planteamientos> planteamientosList = becado.getPlanteamientosList();
            for (Planteamientos planteamientosListPlanteamientos : planteamientosList) {
                planteamientosListPlanteamientos.setBecadoci(null);
                planteamientosListPlanteamientos = em.merge(planteamientosListPlanteamientos);
            }
            em.remove(becado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Becado> findBecadoEntities() {
        return findBecadoEntities(true, -1, -1);
    }

    public List<Becado> findBecadoEntities(int maxResults, int firstResult) {
        return findBecadoEntities(false, maxResults, firstResult);
    }

    private List<Becado> findBecadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Becado.class));
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

    public Becado findBecado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Becado.class, id);
        } finally {
            em.close();
        }
    }

    public int getBecadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Becado> rt = cq.from(Becado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
