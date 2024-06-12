
package beans;

import controller.exceptions.NonexistentEntityException;
import entities.Becado;
import entities.Evalbecado;
import entities.EvalbecadoPK;
import entities.Tipoevalbecado;
import entities.Rangos;
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

@Named(value = "evalBecadoBeans")
@ManagedBean
@SessionScoped
public class evalBecadoBeans implements Serializable{
    
    Date fecha;
    String becadoCi;
    String tipoEvalBecadoid;
    String cualitativa;
    String reglamento;
    
    List<Evalbecado> listEvalBecado = new ArrayList<>();
    
    List<Becado> listBecados = new ArrayList<>();
    Map<String, String> map_becado = new HashMap<>();
    
    List<Tipoevalbecado> listTipoEvalbecad = new ArrayList<>();
    Map<String, String> map_tipoEvalbec = new HashMap<>();
    
    List<Rangos> listrango = new ArrayList<>();
    Map<String, String> map_rango = new HashMap<>();
    
    Evalbecado evalbecado;
    
    public void cargarList(){
        
        listEvalBecado = control.evalbecadoJpa.findEvalbecadoEntities();
        
        listTipoEvalbecad = control.tipoevalbecadoJpa.findTipoevalbecadoEntities();
        listBecados = control.becadoJPA.findBecadoEntities();
        listrango = control.rangosJpa.findRangosEntities();
        
        map_becado.clear();
        for (Becado t : listBecados) {
            if (t.getSegundonombre() == null) {
                map_becado.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_becado.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }
        
        map_tipoEvalbec.clear();
        String aux1;
        for (Tipoevalbecado t : listTipoEvalbecad) {
            if (t.getId() != null) {
                aux1 = t.getTipo() + " : " + t.getDescripcion();
                map_tipoEvalbec.put(aux1, t.getId());
            }
        }
        
        map_rango.clear();
        String aux2;
        for (Rangos r : listrango) {
            if (r.getNombre() != null) {
                aux1 = r.getNombre();
                map_rango.put(aux1, r.getNombre());
            }
        }
    }
    
    public void insert(){
        
        Becado bec = control.becadoJPA.findBecado(becadoCi);
        Tipoevalbecado tipoevalbecado = control.tipoevalbecadoJpa.findTipoevalbecado(tipoEvalBecadoid);
        Rangos rangos = control.rangosJpa.findRangos(cualitativa);
        
        if (fecha == null || becadoCi.isEmpty() || tipoEvalBecadoid.isEmpty() || cualitativa.isEmpty() || reglamento.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        }else{
            try {
                
                for (Evalbecado eb : listEvalBecado) {
                    if (fecha.equals(eb.getEvalbecadoPK().getFecha()) && becadoCi.equals(eb.getEvalbecadoPK().getBecadoci())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe la evaluación", "Atención"));
                        return;
                    }

                }
                
                EvalbecadoPK e = new EvalbecadoPK(fecha, becadoCi);
                Evalbecado eg = new Evalbecado(e);
                
                control.evalbecadoJpa.create(new Evalbecado(e, bec, tipoevalbecado, rangos, reglamento));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        Tipoevalbecado tipoevalbecado = control.tipoevalbecadoJpa.findTipoevalbecado(tipoEvalBecadoid);
        Rangos rangos = control.rangosJpa.findRangos(cualitativa);
        Evalbecado e = control.evalbecadoJpa.findEvalbecado(evalbecado.getEvalbecadoPK());

        if (!tipoEvalBecadoid.isEmpty() && !tipoEvalBecadoid.equals(evalbecado.getTipoevalbecadoid().getId())) {
            e.setTipoevalbecadoid(tipoevalbecado);
            flag = true;
        
        if (cualitativa.isEmpty() && !cualitativa.equals(evalbecado.getCualitativa().getNombre())) {
            e.setCualitativa(rangos);
            flag = true;
        }
        
        }else if (tipoEvalBecadoid.isEmpty() || cualitativa.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.evalbecadoJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido modificada", "Atención"));
            
            } catch (Exception ex) {
                Logger.getLogger(evalBecadoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public void delete(Evalbecado eval) {
        try {
            control.evalbecadoJpa.destroy(eval.getEvalbecadoPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación de la guardia", "Atención"));
        }
    }
    
    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBecadoCi() {
        return becadoCi;
    }

    public void setBecadoCi(String becadoCi) {
        this.becadoCi = becadoCi;
    }

    public String getTipoEvalBecadoid() {
        return tipoEvalBecadoid;
    }

    public void setTipoEvalBecadoid(String tipoEvalBecadoid) {
        this.tipoEvalBecadoid = tipoEvalBecadoid;
    }

    public String getCualitativa() {
        return cualitativa;
    }

    public void setCualitativa(String cualitativa) {
        this.cualitativa = cualitativa;
    }

    public String getReglamento() {
        return reglamento;
    }

    public void setReglamento(String reglamento) {
        this.reglamento = reglamento;
    }

    public List<Evalbecado> getListEvalBecado() {
        return listEvalBecado;
    }

    public void setListEvalBecado(List<Evalbecado> listEvalBecado) {
        this.listEvalBecado = listEvalBecado;
    }

    public List<Becado> getListBecados() {
        return listBecados;
    }

    public void setListBecados(List<Becado> listBecados) {
        this.listBecados = listBecados;
    }

    public Map<String, String> getMap_becado() {
        return map_becado;
    }

    public void setMap_becado(Map<String, String> map_becado) {
        this.map_becado = map_becado;
    }

    public List<Tipoevalbecado> getListTipoEvalbecad() {
        return listTipoEvalbecad;
    }

    public void setListTipoEvalbecad(List<Tipoevalbecado> listTipoEvalbecad) {
        this.listTipoEvalbecad = listTipoEvalbecad;
    }

    public Map<String, String> getMap_tipoEvalbec() {
        return map_tipoEvalbec;
    }

    public void setMap_tipoEvalbec(Map<String, String> map_tipoEvalbec) {
        this.map_tipoEvalbec = map_tipoEvalbec;
    }

    public List<Rangos> getListrango() {
        return listrango;
    }

    public void setListrango(List<Rangos> listrango) {
        this.listrango = listrango;
    }

    public Map<String, String> getMap_rango() {
        return map_rango;
    }

    public void setMap_rango(Map<String, String> map_rango) {
        this.map_rango = map_rango;
    }

    public Evalbecado getEvalbecado() {
        return evalbecado;
    }

    public void setEvalbecado(Evalbecado evalbecado) {
        this.evalbecado = evalbecado;
    }
    
}
