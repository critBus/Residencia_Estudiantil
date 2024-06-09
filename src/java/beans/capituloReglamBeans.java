/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Capituloreglam;
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

@Named(value = "capituloReglamBeans")
@ManagedBean
@SessionScoped
public class capituloReglamBeans implements Serializable{

    String id;
    String nombre;
    
    List<Capituloreglam> listCapitulos = new ArrayList<>();
    
    Capituloreglam capituloReglam;
    
    
    public void cargarList (){
        listCapitulos = control.capituloReglamJPA.findCapituloreglamEntities();
    }
    
    
    public void insert(){
        
        
        if (nombre.isEmpty() || id.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                for (Capituloreglam cr : listCapitulos) {
                    if (id.equals(cr.getId())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un capítulo registrado con el mismo ID", "Atención"));
                        return;
                    }
                    if (cr.getNombre().equals(nombre)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un capítulo registrado con el mismo nombre", "Atención"));
                        return;
                    }

                }

                control.capituloReglamJPA.create(new Capituloreglam(id, nombre));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El capítulo se ha insertado", "Atención"));

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    
    
    public void edit(){
        
        boolean flag = false;
        int count = 0;

        Capituloreglam a = control.capituloReglamJPA.findCapituloreglam(id);
        System.out.println(nombre);
        

        if (!nombre.isEmpty() && !nombre.equals(capituloReglam.getNombre())) {
            a.setNombre(nombre);
            flag = true;
        
        } else if (nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                for (Capituloreglam cr : listCapitulos) {

                    if (nombre.equals(cr.getNombre()) && !nombre.equals(capituloReglam.getNombre())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un capítulo con el mismo nombre", "Atención"));
                        return;
                    }

                }
                control.capituloReglamJPA.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El capitulo ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(trabajadorBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }   
    }
    
    
    
    public void delete(Capituloreglam capreglam){
        
        try {
            control.capituloReglamJPA.destroy(capreglam.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El capítulo se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el capítulo", "Atención"));

        }
        
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Capituloreglam> getListCapitulos() {
        return listCapitulos;
    }

    public void setListCapitulos(List<Capituloreglam> listCapitulos) {
        this.listCapitulos = listCapitulos;
    }

    public Capituloreglam getCapituloReglam() {
        return capituloReglam;
    }

    public void setCapituloReglam(Capituloreglam capituloReglam) {
        this.capituloReglam = capituloReglam;
    }
    
}
