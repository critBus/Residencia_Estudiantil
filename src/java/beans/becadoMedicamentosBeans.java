/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.NonexistentEntityException;
import entities.Becado;
import entities.BecadoEnfermedades;
import entities.BecadoMedicamentos;
import entities.BecadoMedicamentosPK;
import entities.Medicamentos;
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

@Named(value = "becadoMedicamentosBeans")
@ManagedBean
@SessionScoped
public class becadoMedicamentosBeans implements Serializable{
    
    String becadoCi;
    int medicamId;
    String descripcion;
    
    List<BecadoMedicamentos> listBecadoMedicamentos = new ArrayList<>();
    
     List<Becado> listBecado = new ArrayList<>();
    Map<String, String> map_becado = new HashMap<>();
    
    List<Medicamentos> listMedicamentos = new ArrayList<>();
    Map<String, Integer> map_Medicamentos = new HashMap<>();
    
    BecadoMedicamentos becadoMedicamentos;
    
    public void cargarList(){
        
        listBecadoMedicamentos = control.becadoMedicamentoJpa.findBecadoMedicamentosEntities();
        
        listMedicamentos = control.medicamentosJpa.findMedicamentosEntities();
        
        listBecado = control.becadoJPA.findBecadoEntities();
        
        map_Medicamentos.clear();
        for (Medicamentos e : listMedicamentos) {
            if (e.getId() != null) {
                map_Medicamentos.put("Medicamento: " + e.getNombre() + " / " +  "Dosis: " + e.getTipodosisid(), e.getId());
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
        Medicamentos medicamentos = control.medicamentosJpa.findMedicamentos(medicamId);
        
        if (becadoCi.isEmpty() || medicamId == 0 || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        }else{
            try {
                
                for (BecadoMedicamentos pa : listBecadoMedicamentos) {
                    if (becadoCi.equals(pa.getBecadoMedicamentosPK().getBecadoci()) && medicamId == pa.getBecadoMedicamentosPK().getMedicamid()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe Becado con medicamento", "Atención"));
                        return;
                    }

                }
                
                BecadoMedicamentosPK b = new BecadoMedicamentosPK(becadoCi, medicamId);
                BecadoMedicamentos eg = new BecadoMedicamentos(b);
                
                control.becadoMedicamentoJpa.create(new BecadoMedicamentos(b, medicamentos, becado, descripcion));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Becado con medicamento ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        BecadoMedicamentos e = control.becadoMedicamentoJpa.findBecadoMedicamentos(becadoMedicamentos.getBecadoMedicamentosPK());

        if (!descripcion.isEmpty() && !descripcion.equals(becadoMedicamentos.getDescripcion())) {
            e.setDescripcion(descripcion);
            flag = true;
        }else if (descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }
        if (flag) {
            try {
                control.becadoMedicamentoJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Becado con medicamento ha sido modificada", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(becadoMedicamentosBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public void delete(BecadoMedicamentos paca) {
        try {
            control.becadoMedicamentoJpa.destroy(paca.getBecadoMedicamentosPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Becado con medicamento se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar Becado con medicamento", "Atención"));
        }
    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    }
    
    public String medicaDosis(Medicamentos medicamentos) {

        String nombmedicaDosi;

        return nombmedicaDosi = medicamentos.getNombre() + " / " + medicamentos.getTipodosisid();

    }
    
    public String getBecadoCi() {
        return becadoCi;
    }

    public void setBecadoCi(String becadoCi) {
        this.becadoCi = becadoCi;
    }

    public int getMedicamId() {
        return medicamId;
    }

    public void setMedicamId(int medicamId) {
        this.medicamId = medicamId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<BecadoMedicamentos> getListBecadoMedicamentos() {
        return listBecadoMedicamentos;
    }

    public void setListBecadoMedicamentos(List<BecadoMedicamentos> listBecadoMedicamentos) {
        this.listBecadoMedicamentos = listBecadoMedicamentos;
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

    public List<Medicamentos> getListMedicamentos() {
        return listMedicamentos;
    }

    public void setListMedicamentos(List<Medicamentos> listMedicamentos) {
        this.listMedicamentos = listMedicamentos;
    }

    public Map<String, Integer> getMap_Medicamentos() {
        return map_Medicamentos;
    }

    public void setMap_Medicamentos(Map<String, Integer> map_Medicamentos) {
        this.map_Medicamentos = map_Medicamentos;
    }

    public BecadoMedicamentos getBecadoMedicamentos() {
        return becadoMedicamentos;
    }

    public void setBecadoMedicamentos(BecadoMedicamentos becadoMedicamentos) {
        this.becadoMedicamentos = becadoMedicamentos;
    }
}
