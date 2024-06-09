/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Becado;
import entities.Edificio;
import entities.Trabajador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@Named(value = "edificio")
@ManagedBean
@SessionScoped
public class edificioBeans implements Serializable{

    String idEdificio;
    String nombre;
    String especialista;
    String instructor;
    String jefeEdificio;

    List<Edificio> listEdif = new ArrayList<>();
    List<Trabajador> listTrab = new ArrayList<>();
    List<Becado> listBecad = new ArrayList<>();

    Map<String, String> map_trab = new HashMap<>();
    Map<String, String> map_becad = new HashMap<>();

    Edificio edificio;
    Trabajador trabaj;
    Becado becad;

    public edificioBeans() {
    }

    public void cargarList() {
        listEdif = control.edificioJPA.findEdificioEntities();
        listTrab = control.trabajadorJPA.findTrabajadorEntities();
        listBecad = control.becadoJPA.findBecadoEntities();

        map_trab.clear();
        for (Trabajador t : listTrab) {
            if (t.getEnable()){
            map_trab.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
        }

        map_becad.clear();
        for (Becado t : listBecad) {
            if (t.getSegundonombre() == null) {
                map_becad.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_becad.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }

    }

    public void insert() {

        Trabajador traEspecilista = control.trabajadorJPA.findTrabajador(especialista);
        Trabajador traInstructor = control.trabajadorJPA.findTrabajador(instructor);
        Becado becJefeEdif = control.becadoJPA.findBecado(jefeEdificio);

        if (idEdificio.isEmpty() || nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Edificio e : listEdif) {

                    if (e.getId().equals(idEdificio)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el mismo ID", "Atención"));
                        return;
                    }
                    if (e.getNombre().equals(nombre)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el mismo nombre", "Atención"));
                        return;
                    }
                    if (e.getEspecialista() != null) {
                        if (e.getEspecialista().getCi().equals(especialista)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el especialista", "Atención"));
                            return;
                        }
                    }

                    if (e.getInstructor() != null) {
                        if (e.getInstructor().getCi().equals(instructor)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el instructor", "Atención"));
                            return;
                        }
                    }

                    if (e.getJefeedif() != null) {
                        if (e.getJefeedif().getCi().equals(jefeEdificio)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el jefe de edificio", "Atención"));
                            return;
                        }
                    }

                    if (!especialista.isEmpty() && !instructor.isEmpty() && especialista.equals(instructor)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El especialista y el instructor que desea insertar, son iguales", "Atención"));
                        return;
                    }
                    
                    if (!especialista.isEmpty() && e.getEspecialista()!= null && e.getInstructor()!= null) {
                        if (e.getInstructor().getCi().equals(especialista)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El especialista está registrado como instructor", "Atención"));
                            return;
                        }
                    }
                    
                    if (!instructor.isEmpty() && e.getInstructor()!= null && e.getEspecialista()!= null) {
                        if (e.getEspecialista().getCi().equals(instructor)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El instructor está registrado como especialista", "Atención"));
                            return;
                        }
                    }
                }
                control.edificioJPA.create(new Edificio(idEdificio, nombre, traEspecilista, traInstructor, becJefeEdif));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El edificio ha sido insertado", "Atención"));
            } catch (Exception e) {
                Logger.getLogger(edificioBeans.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    public String nombreApell(Trabajador trabajador) {

        String nombApell;
        if (trabajador==null){
            return "desconocido";
        }
        return nombApell = trabajador.getNombre() + " " + trabajador.getApellidos();

    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        Edificio e = control.edificioJPA.findEdificio(edificio.getId());

        Trabajador esp = control.trabajadorJPA.findTrabajador(especialista);
        Trabajador ins = control.trabajadorJPA.findTrabajador(instructor);
        Becado bec = control.becadoJPA.findBecado(jefeEdificio);

        if (!idEdificio.isEmpty() && !idEdificio.equals(edificio.getId())) {
            e.setId(idEdificio);
            flag = true;
        } else if (idEdificio.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'ID' está vacío", "Atención"));
            count++;
        }

        if (!nombre.isEmpty() && !nombre.equals(edificio.getNombre())) {
            e.setNombre(nombre);
            flag = true;

        } else if (nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'Nombre' está vacío", "Atención"));
            count++;
        }

        if (!especialista.isEmpty()) {
            if (edificio.getEspecialista() != null && !especialista.equals(edificio.getEspecialista().getCi())) {
                e.setEspecialista(esp);
                flag = true;

            } else if (edificio.getEspecialista() == null) {
                e.setEspecialista(esp);
                flag = true;

            }
        }
        else if (especialista.isEmpty()&& edificio.getEspecialista() != null) {
            e.setEspecialista(esp);
            flag = true;
        }

        if (!instructor.isEmpty()) {
            if (edificio.getInstructor() != null && !instructor.equals(edificio.getInstructor().getCi())) {
                e.setInstructor(ins);
                flag = true;

            } else if (edificio.getInstructor() == null) {
                e.setInstructor(ins);
                flag = true;

            }
        } 
        else if (instructor.isEmpty() && edificio.getInstructor()!= null) {
            e.setInstructor(ins);
            flag = true;
        }

        if (!jefeEdificio.isEmpty()) {
            if (edificio.getJefeedif() != null && !jefeEdificio.equals(edificio.getJefeedif().getCi())) {
                e.setJefeedif(bec);
                flag = true;

            } else if (edificio.getJefeedif() == null) {
                e.setJefeedif(bec);
                flag = true;

            }
        } 
        else if (jefeEdificio.isEmpty() && edificio.getJefeedif()!= null) {
            e.setJefeedif(bec);
            flag = true;
        }

//   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------     
//   --------------------------------------------------  VALIDACIONES  -------------------------------------------------------------------------------------------------------------
//   ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
        if (flag) {
            try {
                for (Edificio edif : listEdif) {
                    if (!edificio.getNombre().equals(nombre) && edif.getNombre().equals(nombre)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con ese nombre", "Atención"));
                        return;
                    }

                    if (!especialista.isEmpty() && !instructor.isEmpty() && especialista.equals(instructor)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El especialista y el instructor que desea modificar, son iguales", "Atención"));
                        return;
                    }
//-------------------------------------------------------  ESPECIALISTA  ------------------------------------------------------------------------------------------
                    if (!especialista.isEmpty() && edif.getEspecialista() != null && edificio.getEspecialista() != null) {
                        if (!edificio.getEspecialista().getCi().equals(especialista) && edif.getEspecialista().getCi().equals(especialista)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el especialista", "Atención"));
                            return;
                        }
                    } else if (!especialista.isEmpty() && edificio.getEspecialista() == null && edif.getEspecialista() != null) {
                        if (edif.getEspecialista().getCi().equals(especialista)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con la especialista", "Atención"));
                            return;
                        }
                    } else if (edif.getEspecialista() == null && especialista.isEmpty()) {
                        count++;
                    }

//------------------------------------------------------  FIN ESPECIALISTA  ------------------------------------------------------------------------------------
//---------------------------------------------------------  INSTRUCTOR  ------------------------------------------------------------------------------------------
                    if (!instructor.isEmpty() && edif.getInstructor() != null && edificio.getInstructor() != null) {
                        if (!edificio.getInstructor().getCi().equals(instructor) && edif.getInstructor().getCi().equals(instructor)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el instructor", "Atención"));
                            return;
                        }
                    } else if (!instructor.isEmpty() && edificio.getInstructor() == null && edif.getInstructor() != null) {
                        if (edif.getInstructor().getCi().equals(instructor)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el instructor", "Atención"));
                            return;
                        }
                    } else if (edif.getInstructor() == null && instructor.isEmpty()) {
                        count++;
                    }
//----------------------------------------------------------  FIN INSTRUCTOR  ------------------------------------------------------------------------------------

//-----------------------------------------------------  ESPECIALISTA CON INSTRUCTOR  --------------------------------------------------------------------
                    if (!especialista.isEmpty() && edif.getEspecialista() != null && edificio.getEspecialista() != null && edif.getInstructor() != null) {
                        if (!edificio.getEspecialista().getCi().equals(especialista) && edif.getInstructor().getCi().equals(especialista)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El especialista que desea agregar ya se encuentra registrado como instructor", "Atención"));
                            return;
                        }
                    }

                    if (!especialista.isEmpty() && edificio.getEspecialista() == null && edif.getInstructor() != null) {
                        if (edif.getInstructor().getCi().equals(especialista)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El especialista que desea agregar ya se encuentra registrado como instructor", "Atención"));
                            return;
                        }
                    }

//---------------------------------------------------  FIN ESPECIALISTA CON INSTRUCTOR  --------------------------------------------------------------------
//----------------------------------------------------- INSTRUCTOR CON ESPECIALISTA  --------------------------------------------------------------------
                    if (!instructor.isEmpty() && edif.getEspecialista() != null && edif.getEspecialista().getCi().equals(instructor)) {
                        System.out.println("S.O.S");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El instructor que desea agregar ya se encuentra registrado como especialista", "Atención"));
                        return;

                    } else if (!instructor.isEmpty() && edificio.getInstructor() == null && edif.getEspecialista() != null) {
                        if (edif.getEspecialista().getCi().equals(instructor)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El instructor que desea agregar ya se encuentra registrado como especialista xx", "Atención"));
                            return;
                        }
                    }

//----------------------------------------------------- FIN INSTRUCTOR CON ESPECIALISTA  --------------------------------------------------------------------
//------------------------------------------------------------  JEFE DE EDIFICIO  --------------------------------------------------------------------
                    if (!jefeEdificio.isEmpty() && edif.getJefeedif() != null && edificio.getJefeedif() != null) {
                        if (!edificio.getJefeedif().getCi().equals(jefeEdificio) && edif.getJefeedif().getCi().equals(jefeEdificio)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con el jefe de edificio", "Atención"));
                            return;
                        }
                    } else if (!jefeEdificio.isEmpty() && edificio.getJefeedif() == null && edif.getJefeedif() != null) {
                        if (edif.getJefeedif().getCi().equals(jefeEdificio)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un edificio con la jefe de edificio", "Atención"));
                            return;
                        }
                    } else if (edif.getJefeedif() == null && jefeEdificio.isEmpty()) {
                        count++;
                    } else if (edif.getJefeedif() != null && edif.getJefeedif().getCi().equals(jefeEdificio)) {
                        count++;
                    }

//----------------------------------------------------------- FIN JEFE DE EDIFICIO --------------------------------------------------------------------
                    if (!especialista.isEmpty() && esp == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El especialista no se encuentra registrado como trabajador", "Atención"));
                        return;

                    }
                    if (!instructor.isEmpty() && ins == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El instructor no se encuentra registrado como trabajador", "Atención"));
                        return;

                    }
                    if (!jefeEdificio.isEmpty() && bec == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de edificio no se encuentra registrado como becado", "Atención"));
                        return;

                    }

                }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------                
//------------------------------------------------------------  FIN VALIDACIONES  ----------------------------------------------------------------------------------                
//------------------------------------------------------------------------------------------------------------------------------------------------------------------      
                control.edificioJPA.edit(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El edificio ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(edificioBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

   /* public void delete(Edificio edific) {
        try {
            control.edificioJPA.destroy(edific.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El edificio se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el edificio", "Atención"));

        }

    }*/

    public String getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(String idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialista() {
        return especialista;
    }

    public void setEspecialista(String especialista) {
        this.especialista = especialista;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getJefeEdificio() {
        return jefeEdificio;
    }

    public void setJefeEdificio(String jefeEdificio) {
        this.jefeEdificio = jefeEdificio;
    }

    public List<Edificio> getListEdif() {
        return listEdif;
    }

    public void setListEdif(List<Edificio> listEdif) {
        this.listEdif = listEdif;
    }

    public List<Trabajador> getListTrab() {
        return listTrab;
    }

    public void setListTrab(List<Trabajador> listTrab) {
        this.listTrab = listTrab;
    }

    public List<Becado> getListBecad() {
        return listBecad;
    }

    public void setListBecad(List<Becado> listBecad) {
        this.listBecad = listBecad;
    }

    public Map<String, String> getMap_trab() {
        return map_trab;
    }

    public void setMap_trab(Map<String, String> map_trab) {
        this.map_trab = map_trab;
    }

    public Map<String, String> getMap_becad() {
        return map_becad;
    }

    public void setMap_becad(Map<String, String> map_becad) {
        this.map_becad = map_becad;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public Trabajador getTrabaj() {
        return trabaj;
    }

    public void setTrabaj(Trabajador trabaj) {
        this.trabaj = trabaj;
    }

    public Becado getBecad() {
        return becad;
    }

    public void setBecad(Becado becad) {
        this.becad = becad;
    }

}
