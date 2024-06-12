
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Tipoevalbecado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "tipoEvalBecadoBeans")
@ManagedBean
@SessionScoped
public class tipoEvalBecadoBeans implements Serializable {

    String id;
    String tipo;
    String descripcion;

    List<Tipoevalbecado> listTipoEvalBecad = new ArrayList<>();

    Tipoevalbecado tipoevalbecado;

    public void cargarList() {
        listTipoEvalBecad = control.tipoevalbecadoJpa.findTipoevalbecadoEntities();
    }

    public void insert() {

        if (id.isEmpty() || tipo.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                for (Tipoevalbecado te : listTipoEvalBecad) {
                    
                    if (te.getId().equals(id)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un tipo de evaluación registrado con el mismo Id", "Atención"));
                        return;
                    }
                }
                control.tipoevalbecadoJpa.create(new Tipoevalbecado(id, tipo, descripcion));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo de evaluación de becado se ha insertado", "Atención"));

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        Tipoevalbecado a = control.tipoevalbecadoJpa.findTipoevalbecado(id);
        
        if (!tipo.isEmpty() && !tipo.equals(tipoevalbecado.getTipo())) {
            a.setTipo(tipo);
            flag = true;
        }
        if (!descripcion.isEmpty() && !descripcion.equals(tipoevalbecado.getDescripcion())) {
            a.setDescripcion(descripcion);
            flag = true;
            
        } else if (tipo.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                for (Tipoevalbecado te : listTipoEvalBecad) {

                    if (tipo.equals(te.getTipo()) && !tipo.equals(tipoevalbecado.getTipo())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un tipo de evaluación con el mismo nombre", "Atención"));
                        return;
                    }
                    if (descripcion.equals(te.getDescripcion()) && !descripcion.equals(tipoevalbecado.getDescripcion())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un tipo de evaluación con la misma descripción", "Atención"));
                        return;
                    }
                }
                control.tipoevalbecadoJpa.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo evaluación becado ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(tipoEvalBecadoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }
    }

    public void delete(Tipoevalbecado tipoevbec) {
        try {
            control.tipoevalbecadoJpa.destroy(tipoevbec.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo de evaluación se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el tipo de evaluación", "Atención"));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Tipoevalbecado> getListTipoEvalBecad() {
        return listTipoEvalBecad;
    }

    public void setListTipoEvalBecad(List<Tipoevalbecado> listTipoEvalBecad) {
        this.listTipoEvalBecad = listTipoEvalBecad;
    }

    public Tipoevalbecado getTipoevalbecado() {
        return tipoevalbecado;
    }

    public void setTipoevalbecado(Tipoevalbecado tipoevalbecado) {
        this.tipoevalbecado = tipoevalbecado;
    }

}
