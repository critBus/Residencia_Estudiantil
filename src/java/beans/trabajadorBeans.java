
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Trabajador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@Named(value = "trabajadorBeans")
@ManagedBean
@SessionScoped
public class trabajadorBeans implements Serializable{

    String codigo;
    String nombre;
    String apellidos;
    String ci;
    boolean enable;

    List<Trabajador> listTrab = new ArrayList<>();

    Trabajador trabajador;

    public void cargarList() {
        listTrab = control.trabajadorJPA.findTrabajadorEntities();
    }

    public String active(Trabajador trabjad) {
        String active = "";

        if (trabjad.getEnable()) {
            active = "Activo";
        }
        if (!trabjad.getEnable()) {
            active = "Inactivo";
        }

        return active;
    }

    public void insert() {

        if (codigo.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || ci.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Trabajador t : listTrab) {
                    
                    if (t.getCi().equals(ci)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un trabajador con la misma Identificación", "Atención"));
                        return;
                    }
                    if (t.getCodigo().equals(codigo)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un trabajador con el mismo código", "Atención"));
                        return;
                    }
                    if (t.getNombre().equals(nombre) && t.getApellidos().equals(apellidos)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un trabajador con el mismo nombre", "Atención"));
                        return;
                    }
                }
                control.trabajadorJPA.create(new Trabajador(ci, codigo, nombre, apellidos, enable));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El trabajador ha sido insertado", "Atención"));

            } catch (Exception e) {
                Logger.getLogger(trabajadorBeans.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    public String nombreApell(Trabajador trabjad) {

        String nombApell;

        return nombApell = trabjad.getNombre() + " " + trabjad.getApellidos();

    }

    public void edit() {

        boolean flag = false;
        int count = 0;

        Trabajador t = control.trabajadorJPA.findTrabajador(trabajador.getCi());

        if (!ci.isEmpty() && !ci.equals(trabajador.getCi())) {
            t.setCi(ci);
            flag = true;
        }
        if (!codigo.isEmpty() && !codigo.equals(trabajador.getCodigo())) {
            t.setCodigo(codigo);
            flag = true;
        }
        if (!nombre.isEmpty() && !nombre.equals(trabajador.getNombre())) {
            t.setNombre(nombre);
            flag = true;

        }
        if (!apellidos.isEmpty() && !apellidos.equals(trabajador.getApellidos())) {
            t.setApellidos(apellidos);
            flag = true;

        }
        if (enable) {
            t.setEnable(enable);
            flag = true;

        }
        if (!enable) {
            t.setEnable(enable);
            flag = true;

        } else if (codigo.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                for (Trabajador trab : listTrab) {

                    if (trab.getCodigo() == codigo && trabajador.getCodigo() != codigo) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un trabajador con ese código", "Atención"));
                        return;
                    }
                    if (trab.getNombre() == nombre && trab.getApellidos() == apellidos && trabajador.getNombre() != nombre && trabajador.getApellidos() != apellidos) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un trabajador con ese nombre", "Atención"));
                        return;
                    }
                }
                control.trabajadorJPA.edit(t);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El trabajador ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(trabajadorBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    public void delete(Trabajador trabaj) {
        try {
            control.trabajadorJPA.destroy(trabaj.getCi());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El trabajador se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el trabajador", "Atención"));

        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public List<Trabajador> getListTrab() {
        return listTrab;
    }

    public void setListTrab(List<Trabajador> listTrab) {
        this.listTrab = listTrab;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
