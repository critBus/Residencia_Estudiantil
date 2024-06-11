/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuarto;
import entities.AspectEvalcuartoEvalcuarto;
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

/**
 *
 * @author neylis
 */
@Named(value = "aspectEvalCuartBeans")
@ManagedBean
@SessionScoped
public class aspectEvalCuartBeans implements Serializable {

    int id = 0;
    String name;
    int maxValue;

    List<AspectEvalcuarto> listAspEvalCuart = new ArrayList<>();
    List<AspectEvalcuartoEvalcuarto> listAspEv_EvCuart = new ArrayList<>();

    AspectEvalcuarto aspEvalCuart;

    public void cargarList() {
        listAspEvalCuart = control.aspectevalCuartoJPA.findAspectEvalcuartoEntities();
    }

    public void insert() {

        if (name.isEmpty() || maxValue < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                for (AspectEvalcuarto aec : listAspEvalCuart) {
                    if (name.equals(aec.getName())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un aspecto con el mismo nombre", "Atención"));
                        return;
                    }

                }

                control.aspectevalCuartoJPA.create(new AspectEvalcuarto(id, name, maxValue));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto se ha insertado", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }

    }

    public void edit() {

        boolean flag = false;
        int count = 0;

        AspectEvalcuarto a = control.aspectevalCuartoJPA.findAspectEvalcuarto(id);

        if (!name.isEmpty() && !name.equals(aspEvalCuart.getName())) {
            a.setName(name);
            flag = true;
        }
        if (maxValue != 0 && maxValue != aspEvalCuart.getMaxvalue()) {
            a.setMaxvalue(maxValue);
            flag = true;
        } else if (name.isEmpty() || maxValue < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                for (AspectEvalcuarto aec : listAspEvalCuart) {

                    if (name.equals(aec.getName()) && !name.equals(aspEvalCuart.getName())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un aspecto con el mismo nombre", "Atención"));
                        return;
                    }

                }
                control.aspectevalCuartoJPA.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(trabajadorBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    /*public void delete(AspectEvalcuarto aecu) {
        try {
            
            eliminando_lista_evaluacion(aecu.getId());
            control.aspectevalCuartoJPA.destroy(aecu.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto evaluativo se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el aspecto evaluativo", "Atención"));

        }
    }
     */
    public void eliminando_lista_evaluacion(int aspectoeval) {

        //List<AspectEvalcuartoEvalcuarto> listado=new ArrayList();
        listAspEv_EvCuart = control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuartoEntities();

        for (AspectEvalcuartoEvalcuarto item : listAspEv_EvCuart) {

            if (item.getAspectEvalcuarto().getId() == aspectoeval) {

                try {
                    control.aspEv_EvCuartoJPA.destroy(item.getAspectEvalcuartoEvalcuartoPK());
                } catch (NonexistentEntityException ex) {
                    //Logger.getLogger(aspectEvalCuartBeans.class.getName()).log(Level.SEVERE, null, ex);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el aspecto evaluativo", "Atención"));

                }

            }
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public List<AspectEvalcuarto> getListAspEvalCuart() {
        return listAspEvalCuart;
    }

    public void setListAspEvalCuart(List<AspectEvalcuarto> listAspEvalCuart) {
        this.listAspEvalCuart = listAspEvalCuart;
    }

    public AspectEvalcuarto getAspEvalCuart() {
        return aspEvalCuart;
    }

    public void setAspEvalCuart(AspectEvalcuarto aspEvalCuart) {
        this.aspEvalCuart = aspEvalCuart;
    }

}
