
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Enfermedades;
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

@Named(value = "enfermedadesBeans")
@ManagedBean
@SessionScoped
public class enfermedadesBeans implements Serializable {

    int id = 0;
    String nombre;

    List<Enfermedades> listenfermedades = new ArrayList<>();

    Enfermedades enfermedades;

    public void cargarList() {

        listenfermedades = control.enfermedadesJpa.findEnfermedadesEntities();
    }

    public void insert() {
        if (nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                for (Enfermedades ef : listenfermedades) {
                    if (nombre.equals(ef.getNombre())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe una enfermedad con el mismo nombre", "Atención"));
                        return;
                    }
                }
                control.enfermedadesJpa.create(new Enfermedades(id, nombre));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El enfermedad se ha insertado", "Atención"));

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        Enfermedades a = control.enfermedadesJpa.findEnfermedades(id);

        if (!nombre.isEmpty() && !nombre.equals(enfermedades.getNombre())) {
            a.setNombre(nombre);
            flag = true;
        } else if (nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                for (Enfermedades ef : listenfermedades) {

                    if (nombre.equals(ef.getNombre()) && !nombre.equals(enfermedades.getNombre())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe una enfermedad con el mismo nombre", "Atención"));
                        return;
                    }

                }
                control.enfermedadesJpa.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El enfermedad ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(enfermedadesBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    public void delete(Enfermedades enferm) {
        try {
            control.enfermedadesJpa.destroy(enferm.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El enfermedad se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el enfermedad", "Atención"));

        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Enfermedades> getListenfermedades() {
        return listenfermedades;
    }

    public void setListenfermedades(List<Enfermedades> listenfermedades) {
        this.listenfermedades = listenfermedades;
    }

    public Enfermedades getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(Enfermedades enfermedades) {
        this.enfermedades = enfermedades;
    }

}
