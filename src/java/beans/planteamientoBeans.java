
package beans;

import entities.Becado;
import entities.Planteamientos;
import entities.Trabajador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "planteamientoBeans")
@ManagedBean
@SessionScoped
public class planteamientoBeans implements Serializable {
    
    String id;
    Date fecha;
    String planteamiento;
    String respuesta;
    String estado;
    String asunto;
    String descripcion;
    String trabajadorCi;
    String becadoCi;
    
    List<Planteamientos> listPlantea = new ArrayList<>();
    List<Trabajador> listTrab = new ArrayList<>();
    List<Becado> listBecad = new ArrayList<>();
    
    Map<String, String> map_trab = new HashMap<>();
    Map<String, String> map_becad = new HashMap<>();
    
    Planteamientos planteamientos;
    Trabajador trabaj;
    Becado becad;
    
    public planteamientoBeans() {
    }
    
    public void cargarList() {
        listPlantea = control.planteamientosJpa.findPlanteamientosEntities();
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

        Trabajador trabajador = control.trabajadorJPA.findTrabajador(trabajadorCi);
        Becado becado = control.becadoJPA.findBecado(becadoCi);

        if (id.isEmpty() || fecha == null || planteamiento.isEmpty() || respuesta.isEmpty() || estado.isEmpty() || asunto.isEmpty() || trabajadorCi.isEmpty() || becadoCi.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Planteamientos p : listPlantea) {

                    if (p.getId().equals(id)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un planteamiento con el mismo ID", "Atención"));
                        return;
                    }
                }
                control.planteamientosJpa.create(new Planteamientos(id, fecha, planteamiento, respuesta, estado, asunto, descripcion, trabajador, becado));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El planteamiento ha sido insertado", "Atención"));
            } catch (Exception e) {
                Logger.getLogger(planteamientoBeans.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }
    public void edit() {
        boolean flag = false;
        int count = 0;

        Planteamientos e = control.planteamientosJpa.findPlanteamientos(planteamientos.getId());
        
        if (!planteamiento.isEmpty() && !planteamiento.equals(planteamientos.getPlanteamiento())) {
            e.setPlanteamiento(planteamiento);
            flag = true;
        }
        if (respuesta.isEmpty() && !respuesta.equals(planteamientos.getRespuesta())) {
            e.setRespuesta(respuesta);
            flag = true;
        }
        if (estado.isEmpty() && !estado.equals(planteamientos.getEstado())) {
            e.setEstado(estado);
            flag = true;
        }
        if (asunto.isEmpty() && !asunto.equals(planteamientos.getAsunto())) {
            e.setAsunto(asunto);
            flag = true;
        }
        if (descripcion.isEmpty() && !descripcion.equals(planteamientos.getDescripcion())) {
            e.setDescripcion(descripcion);
            flag = true;
        }
        else if (planteamiento.isEmpty() || respuesta.isEmpty() || estado.isEmpty() || asunto.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.planteamientosJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El Planteamiento ha sido modificada", "Atención"));
            
            } catch (Exception ex) {
                Logger.getLogger(planteamientoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    public String nombreApell(Trabajador trabajador) {

        String nombApell;

        return nombApell = trabajador.getNombre() + " " + trabajador.getApellidos();

    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    }
    
    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPlanteamiento() {
        return planteamiento;
    }

    public void setPlanteamiento(String planteamiento) {
        this.planteamiento = planteamiento;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTrabajadorCi() {
        return trabajadorCi;
    }

    public void setTrabajadorCi(String trabajadorCi) {
        this.trabajadorCi = trabajadorCi;
    }

    public String getBecadoCi() {
        return becadoCi;
    }

    public void setBecadoCi(String becadoCi) {
        this.becadoCi = becadoCi;
    }

    public List<Planteamientos> getListPlantea() {
        return listPlantea;
    }

    public void setListPlantea(List<Planteamientos> listPlantea) {
        this.listPlantea = listPlantea;
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

    public Planteamientos getPlanteamientos() {
        return planteamientos;
    }

    public void setPlanteamientos(Planteamientos planteamientos) {
        this.planteamientos = planteamientos;
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
