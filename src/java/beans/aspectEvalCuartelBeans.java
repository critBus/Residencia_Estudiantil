/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuartel;
import entities.EvalcuarteleriaAspectevalcuartel;
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

@Named(value = "aspectEvalCuartelBeans")
@ManagedBean
@SessionScoped
public class aspectEvalCuartelBeans implements Serializable{

    int id;
    String name;
    int maxValue;

    List<AspectEvalcuartel> listAspEvalCuart = new ArrayList<>();
    List<EvalcuarteleriaAspectevalcuartel> listAspCuartel_EvCuartel = new ArrayList<>();

    AspectEvalcuartel aspEvalCuartel;

    public void cargarList() {
        listAspEvalCuart = control.aspectevalCuartelJPA.findAspectEvalcuartelEntities();
    }

    public void insert() {

        if (name.isEmpty() || maxValue == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                for (AspectEvalcuartel aec : listAspEvalCuart) {
                    if (id == aec.getId()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un aspecto con el mismo ID", "Atención"));
                        return;
                    }
                    if (name.equals(aec.getName())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un aspecto con el mismo nombre", "Atención"));
                        return;
                    }

                }

                control.aspectevalCuartelJPA.create(new AspectEvalcuartel(id, name, maxValue));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }

    }

    public void edit() {

        boolean flag = false;
        int count = 0;

        AspectEvalcuartel a = control.aspectevalCuartelJPA.findAspectEvalcuartel(id);

        if (!name.isEmpty() && !name.equals(aspEvalCuartel.getName())) {
            a.setName(name);
            flag = true;
        }
        if (maxValue != 0 && maxValue != aspEvalCuartel.getMaxvalue()) {
            a.setMaxvalue(maxValue);
            flag = true;
            
        } else if (name.isEmpty() || maxValue == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                for (AspectEvalcuartel aec : listAspEvalCuart) {

                    if (name.equals(aec.getName()) && !name.equals(aspEvalCuartel.getName())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un aspecto con el mismo nombre", "Atención"));
                        return;
                    }

                }
                control.aspectevalCuartelJPA.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(trabajadorBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    public void delete(AspectEvalcuartel aecuartel) {
        try {
            
            System.out.println("//////////////////////////////////////");
            System.out.println(aecuartel);
            
            eliminando_lista_evaluacion(aecuartel.getId());
            control.aspectevalCuartelJPA.destroy(aecuartel.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El aspecto evaluativo se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el aspecto evaluativo", "Atención"));

        }
    }

    public void eliminando_lista_evaluacion(int aspectoeval) {

        //List<AspectEvalcuartoEvalcuarto> listado=new ArrayList();
        listAspCuartel_EvCuartel = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartelEntities();

        for (EvalcuarteleriaAspectevalcuartel item : listAspCuartel_EvCuartel) {

            if (item.getAspectEvalcuartel().getId() == aspectoeval) {

                try {
                    control.aspEvCuartel_EvCuartelJPA.destroy(item.getEvalcuarteleriaAspectevalcuartelPK());
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

    public List<AspectEvalcuartel> getListAspEvalCuart() {
        return listAspEvalCuart;
    }

    public void setListAspEvalCuart(List<AspectEvalcuartel> listAspEvalCuart) {
        this.listAspEvalCuart = listAspEvalCuart;
    }

    public List<EvalcuarteleriaAspectevalcuartel> getListAspCuartel_EvCuartel() {
        return listAspCuartel_EvCuartel;
    }

    public void setListAspCuartel_EvCuartel(List<EvalcuarteleriaAspectevalcuartel> listAspCuartel_EvCuartel) {
        this.listAspCuartel_EvCuartel = listAspCuartel_EvCuartel;
    }

    public AspectEvalcuartel getAspEvalCuartel() {
        return aspEvalCuartel;
    }

    public void setAspEvalCuartel(AspectEvalcuartel aspEvalCuartel) {
        this.aspEvalCuartel = aspEvalCuartel;
    }

    

    
    
}
