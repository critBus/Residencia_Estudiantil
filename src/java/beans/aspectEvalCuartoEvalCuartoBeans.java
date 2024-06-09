
package beans;

import entities.AspectEvalcuartoEvalcuarto;
import entities.AspectEvalcuartoEvalcuartoPK;
import entities.Evalcuarto;
import entities.EvalcuartoPK;
import entities.AspectEvalcuarto;
import entities.Cuarto;
import entities.CuartoPK;
import entities.Piso;
import entities.PisoPK;
import entities.Edificio;
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

@Named(value = "aspectEvalCuartoEvalCuartoBeans")
@ManagedBean
@SessionScoped
public class aspectEvalCuartoEvalCuartoBeans implements Serializable {
    
    int aspEvalCuartoId;
    Date fecha;
    String cuartoId;
    String pisoId;
    String edificioId;
    int value;

    List<AspectEvalcuartoEvalcuarto> listAspEv_EvCuarto = new ArrayList<>();
    List<AspectEvalcuarto> listaspEvalCuarto = new ArrayList<>();
    List<Cuarto> listCuarto = new ArrayList<>();
    List<Piso> listPiso = new ArrayList<>();
    List<Edificio> listEdificio = new ArrayList<>();
    
    Map<String, Integer> map_aspEvalCuarto = new HashMap<>();
    Map<String, String> map_Cuarto = new HashMap<>();
    Map<String, String> map_Piso = new HashMap<>();
    Map<String, String> map_Edificio = new HashMap<>();
    
    AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuarto;
    String ubicacion;
    
    public void cargarList(){
        
        listAspEv_EvCuarto = control.aspectEvalcuartoEvalcuartoJpa.findAspectEvalcuartoEvalcuartoEntities();
        listaspEvalCuarto = control.aspectevalCuartoJPA.findAspectEvalcuartoEntities();
        listCuarto = control.cuartoJPA.findCuartoEntities();
        listPiso = control.pisoJPA.findPisoEntities();
        listEdificio = control.edificioJPA.findEdificioEntities();
        
        map_aspEvalCuarto.clear();
        String aux1;
        for(AspectEvalcuarto asp : listaspEvalCuarto){
            if (asp.getId() != null) {
                aux1 = asp.getName();
                map_aspEvalCuarto.put(aux1, asp.getId());
            }
        }
        
        map_Cuarto.clear();
        String aux2;
        for(Cuarto c : listCuarto){
            if (c.getCuartoPK().getId() != null) {
                aux2 = "Cuarto: " + c.getCuartoPK().getId();
                map_Cuarto.put(aux2, c.getCuartoPK().getId());
            }
        }
        
        map_Piso.clear();
        String aux3;
        for(Piso p : listPiso){
            if (p.getPisoPK().getId() != null) {
                aux3 = "Piso: " + p.getPisoPK().getId();
                map_Piso.put(aux3, p.getPisoPK().getId());
            }
        }
        
        map_Edificio.clear();
        String aux4;
        for(Edificio e : listEdificio){
            if (e.getId() != null) {
                aux4 = "Edificio: " + e.getNombre();
                map_Edificio.put(aux4, e.getId());
            }
        }
        
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

          AspectEvalcuartoEvalcuarto asp = control.aspectEvalcuartoEvalcuartoJpa.findAspectEvalcuartoEvalcuarto(aspectEvalcuartoEvalcuarto.getAspectEvalcuartoEvalcuartoPK());

        if (value != 0 && value != aspectEvalcuartoEvalcuarto.getValue()) {
           asp.setValue(value);
            flag = true;
        } else if (value == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'valor' está vacío", "Atención"));
            count++;
        }

        if (flag) {
            try {

                control.aspectEvalcuartoEvalcuartoJpa.edit(asp);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto evaluación cuarto ha sido modificada", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(aspectEvalCuartoEvalCuartoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }

    public int getAspEvalCuartoId() {
        return aspEvalCuartoId;
    }

    public void setAspEvalCuartoId(int aspEvalCuartoId) {
        this.aspEvalCuartoId = aspEvalCuartoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCuartoId() {
        return cuartoId;
    }

    public void setCuartoId(String cuartoId) {
        this.cuartoId = cuartoId;
    }

    public String getPisoId() {
        return pisoId;
    }

    public void setPisoId(String pisoId) {
        this.pisoId = pisoId;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<AspectEvalcuartoEvalcuarto> getListAspEv_EvCuarto() {
        return listAspEv_EvCuarto;
    }

    public void setListAspEv_EvCuarto(List<AspectEvalcuartoEvalcuarto> listAspEv_EvCuarto) {
        this.listAspEv_EvCuarto = listAspEv_EvCuarto;
    }

    public List<AspectEvalcuarto> getListaspEvalCuarto() {
        return listaspEvalCuarto;
    }

    public void setListaspEvalCuarto(List<AspectEvalcuarto> listaspEvalCuarto) {
        this.listaspEvalCuarto = listaspEvalCuarto;
    }

    public List<Cuarto> getListCuarto() {
        return listCuarto;
    }

    public void setListCuarto(List<Cuarto> listCuarto) {
        this.listCuarto = listCuarto;
    }

    public List<Piso> getListPiso() {
        return listPiso;
    }

    public void setListPiso(List<Piso> listPiso) {
        this.listPiso = listPiso;
    }

    public List<Edificio> getListEdificio() {
        return listEdificio;
    }

    public void setListEdificio(List<Edificio> listEdificio) {
        this.listEdificio = listEdificio;
    }

    public Map<String, Integer> getMap_aspEvalCuarto() {
        return map_aspEvalCuarto;
    }

    public void setMap_aspEvalCuarto(Map<String, Integer> map_aspEvalCuarto) {
        this.map_aspEvalCuarto = map_aspEvalCuarto;
    }

    public Map<String, String> getMap_Cuarto() {
        return map_Cuarto;
    }

    public void setMap_Cuarto(Map<String, String> map_Cuarto) {
        this.map_Cuarto = map_Cuarto;
    }

    public Map<String, String> getMap_Piso() {
        return map_Piso;
    }

    public void setMap_Piso(Map<String, String> map_Piso) {
        this.map_Piso = map_Piso;
    }

    public Map<String, String> getMap_Edificio() {
        return map_Edificio;
    }

    public void setMap_Edificio(Map<String, String> map_Edificio) {
        this.map_Edificio = map_Edificio;
    }

    public AspectEvalcuartoEvalcuarto getAspectEvalcuartoEvalcuarto() {
        return aspectEvalcuartoEvalcuarto;
    }

    public void setAspectEvalcuartoEvalcuarto(AspectEvalcuartoEvalcuarto aspectEvalcuartoEvalcuarto) {
        this.aspectEvalcuartoEvalcuarto = aspectEvalcuartoEvalcuarto;
    }

}
