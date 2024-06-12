
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Medico;
import entities.Trabajador;
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

@Named(value = "medicoBeans")
@ManagedBean
@SessionScoped
public class medicoBeans implements Serializable {

    String ciMedico;
    String nombre;
    String apellidos;
    String especialidad;
    String direccion;

    List<Medico> listmedico = new ArrayList<>();

    Medico medico;

    public void cargarList() {
        listmedico = control.medicoJpa.findMedicoEntities();

    }

    public void insert() {

        if (especialidad.isEmpty() || direccion.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || ciMedico.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Medico m : listmedico) {
                    
                    if (m.getCiMedico().equals(ciMedico)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un médico con la misma Identificación", "Atención"));
                        return;
                    }
                }
                control.medicoJpa.create(new Medico(ciMedico, nombre, apellidos, especialidad, direccion));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El médico ha sido insertado", "Atención"));

            } catch (Exception e) {
                Logger.getLogger(medicoBeans.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }
    
    public String nombreApell(Medico medico) {

        String nombApell;

        return nombApell = medico.getNombre() + " " + medico.getApellidos();

    }
    
    public void edit(){
        
        boolean flag = false;
        int count = 0;

        Medico a = control.medicoJpa.findMedico(ciMedico);
        
        if (!especialidad.isEmpty() && !especialidad.equals(medico.getEspecialidad())) {
            a.setEspecialidad(especialidad);
            flag = true;
        }
        
        if (!direccion.isEmpty() && !direccion.equals(medico.getDireccion())) {
            a.setDireccion(direccion);
            flag = true;

        } else if (especialidad.isEmpty() || direccion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.medicoJpa.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El medico ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(medicoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }   
    }
    
    public void delete(Medico medic) {
        try {
            control.medicoJpa.destroy(medic.getCiMedico());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El medico se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el medico", "Atención"));

        }
    }

    public String getCiMedico() {
        return ciMedico;
    }

    public void setCiMedico(String ciMedico) {
        this.ciMedico = ciMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Medico> getListmedico() {
        return listmedico;
    }

    public void setListmedico(List<Medico> listmedico) {
        this.listmedico = listmedico;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
}
