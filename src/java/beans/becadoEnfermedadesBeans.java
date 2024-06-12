
package beans;

import controller.exceptions.NonexistentEntityException;
import entities.Enfermedades;
import entities.BecadoEnfermedades;
import entities.BecadoEnfermedadesPK;
import entities.Becado;
import java.io.Serializable;
import java.util.ArrayList;
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

@Named(value = "becadoEnfermedadesBeans")
@ManagedBean
@SessionScoped
public class becadoEnfermedadesBeans implements Serializable{
    
    String becadoCi;
    int enfermId;
    String descripcion;
    
    List<BecadoEnfermedades> listBecadoEnfermedades = new ArrayList<>();
    
     List<Becado> listBecado = new ArrayList<>();
    Map<String, String> map_becado = new HashMap<>();
    
    List<Enfermedades> listEnfermedades = new ArrayList<>();
    Map<String, Integer> map_Enfermedades = new HashMap<>();
    
    BecadoEnfermedades becadoEnfermedades;
    
    public void cargarList(){
        
        listBecadoEnfermedades = control.becadoEnfermedadJpa.findBecadoEnfermedadesEntities();
        
        listEnfermedades = control.enfermedadesJpa.findEnfermedadesEntities();
        listBecado = control.becadoJPA.findBecadoEntities();
        
        map_Enfermedades.clear();
        for (Enfermedades e : listEnfermedades) {
            if (e.getId() != null) {
                map_Enfermedades.put(e.getNombre(), e.getId());
            }
        }
        
        map_becado.clear();
        for (Becado t : listBecado) {
            if (t.getSegundonombre() == null) {
                map_becado.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_becado.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }
    }
    
    public void insert(){
        
        Becado becado = control.becadoJPA.findBecado(becadoCi);
        Enfermedades enfermedades = control.enfermedadesJpa.findEnfermedades(enfermId);
        
        if (becadoCi.isEmpty() || enfermId == 0 || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        }else{
            try {
                
                for (BecadoEnfermedades pa : listBecadoEnfermedades) {
                    if (becadoCi.equals(pa.getBecadoEnfermedadesPK().getBecadoci()) && enfermId == pa.getBecadoEnfermedadesPK().getEnfermid()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe Becado con enfermedad", "Atención"));
                        return;
                    }

                }
                
                BecadoEnfermedadesPK b = new BecadoEnfermedadesPK(becadoCi, enfermId);
                BecadoEnfermedades eg = new BecadoEnfermedades(b);
                
                control.becadoEnfermedadJpa.create(new BecadoEnfermedades(b, enfermedades, becado, descripcion));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Becado con enfermedad ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        BecadoEnfermedades e = control.becadoEnfermedadJpa.findBecadoEnfermedades(becadoEnfermedades.getBecadoEnfermedadesPK());

        if (!descripcion.isEmpty() && !descripcion.equals(becadoEnfermedades.getDescripcion())) {
            e.setDescripcion(descripcion);
            flag = true;
        }else if (descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }
        if (flag) {
            try {
                control.becadoEnfermedadJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Becado con enfermedad ha sido modificada", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(becadoEnfermedadesBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    public void delete(BecadoEnfermedades paca) {
        try {
            control.becadoEnfermedadJpa.destroy(paca.getBecadoEnfermedadesPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Becado con enfermedad se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar Becado con enfermedad", "Atención"));
        }
    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    }

    public String getBecadoCi() {
        return becadoCi;
    }

    public void setBecadoCi(String becadoCi) {
        this.becadoCi = becadoCi;
    }

    public int getEnfermId() {
        return enfermId;
    }

    public void setEnfermId(int enfermId) {
        this.enfermId = enfermId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<BecadoEnfermedades> getListBecadoEnfermedades() {
        return listBecadoEnfermedades;
    }

    public void setListBecadoEnfermedades(List<BecadoEnfermedades> listBecadoEnfermedades) {
        this.listBecadoEnfermedades = listBecadoEnfermedades;
    }

    public List<Becado> getListBecado() {
        return listBecado;
    }

    public void setListBecado(List<Becado> listBecado) {
        this.listBecado = listBecado;
    }

    public Map<String, String> getMap_becado() {
        return map_becado;
    }

    public void setMap_becado(Map<String, String> map_becado) {
        this.map_becado = map_becado;
    }

    public List<Enfermedades> getListEnfermedades() {
        return listEnfermedades;
    }

    public void setListEnfermedades(List<Enfermedades> listEnfermedades) {
        this.listEnfermedades = listEnfermedades;
    }

    public Map<String, Integer> getMap_Enfermedades() {
        return map_Enfermedades;
    }

    public void setMap_Enfermedades(Map<String, Integer> map_Enfermedades) {
        this.map_Enfermedades = map_Enfermedades;
    }

    public BecadoEnfermedades getBecadoEnfermedades() {
        return becadoEnfermedades;
    }

    public void setBecadoEnfermedades(BecadoEnfermedades becadoEnfermedades) {
        this.becadoEnfermedades = becadoEnfermedades;
    }
}
