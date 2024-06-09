
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Rangos;
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

@Named(value = "rangosBeans")
@ManagedBean
@SessionScoped
public class rangosBeans implements Serializable{
    
    String nombre;
    String valorMax;
    String valorMin;
    
    List<Rangos> listRangos = new ArrayList<>();
    
    Rangos rangos;
    
    public void cargarList() {
        listRangos = control.rangosJpa.findRangosEntities();
    }
    
    public void insert() {

        if (nombre.isEmpty() || valorMax.isEmpty() || valorMin.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                for (Rangos ra : listRangos) {
                    if (nombre.equals(ra.getNombre())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un rango registrado con el mismo nombre", "Atención"));
                        return;
                    }
                }

                control.rangosJpa.create(new Rangos(nombre, valorMax, valorMin));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El rango se ha insertado", "Atención"));

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        Rangos a = control.rangosJpa.findRangos(nombre);
        
        if (!valorMax.isEmpty() && !valorMax.equals(rangos.getValormax())) {
            a.setValormax(valorMax);
            flag = true;   
        }
        if (!valorMin.isEmpty() && !valorMin.equals(rangos.getValormin())) {
            a.setValormin(valorMin);
            flag = true;   
        }else if (valorMax.isEmpty() || valorMin.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.rangosJpa.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El rango ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(rangosBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public void delete(Rangos rang) {
        try {
            control.rangosJpa.destroy(rang.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El rango se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el rango", "Atención"));

        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorMax() {
        return valorMax;
    }

    public void setValorMax(String valorMax) {
        this.valorMax = valorMax;
    }

    public String getValorMin() {
        return valorMin;
    }

    public void setValorMin(String valorMin) {
        this.valorMin = valorMin;
    }

    public List<Rangos> getListRangos() {
        return listRangos;
    }

    public void setListRangos(List<Rangos> listRangos) {
        this.listRangos = listRangos;
    }

    public Rangos getRangos() {
        return rangos;
    }

    public void setRangos(Rangos rangos) {
        this.rangos = rangos;
    }
    
    
}
