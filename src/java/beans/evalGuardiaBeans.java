
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Becado;
import entities.Evalguardia;
import entities.EvalguardiaPK;
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

@Named(value = "evalGuardiaBeans")
@ManagedBean
@SessionScoped
public class evalGuardiaBeans implements Serializable {
    
    Date fecha;
    String becadoCi;
    String trabajadorci;
    int evaluacion;
    
    List<Evalguardia> listEvalGuardia = new ArrayList<>();
    
    List<Becado> listBecados = new ArrayList<>();
    Map<String, String> map_becado = new HashMap<>();
    
    List<Trabajador> listTrab = new ArrayList<>();
    Map<String, String> map_trab = new HashMap<>();
    
    Evalguardia evalguardia;
    
    public void cargarList(){
        
        listEvalGuardia = control.evalguardiaJpa.findEvalguardiaEntities();
        
        listTrab = control.trabajadorJPA.findTrabajadorEntities();
        listBecados = control.becadoJPA.findBecadoEntities();
        
        map_becado.clear();
        for (Becado t : listBecados) {
            if (t.getSegundonombre() == null) {
                map_becado.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_becado.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }
        
        map_trab.clear();
        String aux1;
        for (Trabajador t : listTrab) {
            if (t.getEnable()) {
                aux1 = t.getNombre() + " " + t.getApellidos();
                map_trab.put(aux1, t.getCi());
            }
        }
    }
    
    public void insert(){
        
        Becado bec = control.becadoJPA.findBecado(becadoCi);
        Trabajador califica = control.trabajadorJPA.findTrabajador(trabajadorci);
        
        if (fecha == null || becadoCi.isEmpty() || evaluacion < 0 || trabajadorci.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        }else{
            try {
                
                for (Evalguardia evg : listEvalGuardia) {
                    if (fecha.equals(evg.getEvalguardiaPK().getFecha()) && becadoCi.equals(evg.getEvalguardiaPK().getBecadoci())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe la evaluación", "Atención"));
                        return;
                    }

                }
                
                EvalguardiaPK e = new EvalguardiaPK(fecha, becadoCi);
                Evalguardia eg = new Evalguardia(e);
                
                control.evalguardiaJpa.create(new Evalguardia(e, bec, califica, evaluacion));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        Trabajador califica = control.trabajadorJPA.findTrabajador(trabajadorci);

        Evalguardia e = control.evalguardiaJpa.findEvalguardia(evalguardia.getEvalguardiaPK());

        if (!trabajadorci.isEmpty() && !trabajadorci.equals(evalguardia.getTrabajadorci().getCi())) {
            e.setTrabajadorci(califica);
            flag = true;
        
        if (evaluacion != 0 && evaluacion != evalguardia.getEvaluacion()) {
            e.setEvaluacion(evaluacion);
            flag = true;
        }
        }else if (trabajadorci.isEmpty() || evaluacion == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.evalguardiaJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido modificada", "Atención"));
            
            } catch (Exception ex) {
                Logger.getLogger(evalGuardiaBeans.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    /*public void delete(Evalguardia eval) {
        try {
            control.evalguardiaJpa.destroy(eval.getEvalguardiaPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación de la guardia", "Atención"));
        }
    }
    */
    
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

    public String getTrabajadorci() {
        return trabajadorci;
    }

    public void setTrabajadorci(String trabajadorci) {
        this.trabajadorci = trabajadorci;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<Evalguardia> getListEvalGuardia() {
        return listEvalGuardia;
    }

    public void setListEvalGuardia(List<Evalguardia> listEvalGuardia) {
        this.listEvalGuardia = listEvalGuardia;
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

    public List<Trabajador> getListTrab() {
        return listTrab;
    }

    public void setListTrab(List<Trabajador> listTrab) {
        this.listTrab = listTrab;
    }

    public Map<String, String> getMap_trab() {
        return map_trab;
    }

    public void setMap_trab(Map<String, String> map_trab) {
        this.map_trab = map_trab;
    }

    public Evalguardia getEvalguardia() {
        return evalguardia;
    }

    public void setEvalguardia(Evalguardia evalguardia) {
        this.evalguardia = evalguardia;
    }
}
