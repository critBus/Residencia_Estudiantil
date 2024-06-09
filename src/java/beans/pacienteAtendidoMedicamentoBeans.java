
package beans;

import controller.exceptions.NonexistentEntityException;
import entities.Pacientesatendidos;
import entities.PacatendMedicamentos;
import entities.PacatendMedicamentosPK;
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

@Named(value = "pacienteAtendidoMedicamentoBeans")
@ManagedBean
@SessionScoped
public class pacienteAtendidoMedicamentoBeans implements Serializable{
    
    String pacAtendId;
    int medicamId;
    String cantAdmi;
    
    List<PacatendMedicamentos> listPacAtendidoMedicamento = new ArrayList<>();
    
     List<Pacientesatendidos> listPacAtendidos = new ArrayList<>();
    Map<String, String> map_pacAtendidos = new HashMap<>();
    
    List<Medicamentos> listMedicamento = new ArrayList<>();
    Map<String, Integer> map_Medicamento = new HashMap<>();
    
    PacatendMedicamentos pacatendMedicamentos;
    
    public void cargarList(){
        
        listPacAtendidoMedicamento = control.pacatendMedicamentosJpa.findPacatendMedicamentosEntities();
        
        listMedicamento = control.medicamentosJpa.findMedicamentosEntities();
        listPacAtendidos = control.pacientesatendidosJpa.findPacientesatendidosEntities();
        
        map_Medicamento.clear();
        for (Medicamentos m : listMedicamento) {
            if (m.getId() != null) {
                map_Medicamento.put(m.getNombre(), m.getId());
            }
        }
        
        map_pacAtendidos.clear();
        String aux1;
        for (Pacientesatendidos p : listPacAtendidos) {
            if (p.getId() != null) {
                aux1 = p.getId();
                map_pacAtendidos.put(aux1, p.getId());
            }
        }
    }
    
    public void insert(){
        
        Pacientesatendidos pacientesatendidos = control.pacientesatendidosJpa.findPacientesatendidos(pacAtendId);
        Medicamentos medicamentos = control.medicamentosJpa.findMedicamentos(medicamId);
        
        if (pacAtendId.isEmpty() || medicamId == 0 || cantAdmi.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        }else{
            try {
                
                for (PacatendMedicamentos pa : listPacAtendidoMedicamento) {
                    if (pacAtendId.equals(pa.getPacatendMedicamentosPK().getPacatendid()) && medicamId == pa.getPacatendMedicamentosPK().getMedicamid()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe paciente con medicamento", "Atención"));
                        return;
                    }

                }
                
                PacatendMedicamentosPK e = new PacatendMedicamentosPK(pacAtendId, medicamId);
                PacatendMedicamentos eg = new PacatendMedicamentos(e);
                
                control.pacatendMedicamentosJpa.create(new PacatendMedicamentos(e, pacientesatendidos, medicamentos, cantAdmi));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con medicamento ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        PacatendMedicamentos e = control.pacatendMedicamentosJpa.findPacatendMedicamentos(pacatendMedicamentos.getPacatendMedicamentosPK());

        if (!cantAdmi.isEmpty() && !cantAdmi.equals(pacatendMedicamentos.getCantadim())) {
            e.setCantadim(cantAdmi);
            flag = true;
        }else if (cantAdmi.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }
        if (flag) {
            try {
                control.pacatendMedicamentosJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con medicamento ha sido modificada", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(pacienteAtendidoMedicamentoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    public void delete(PacatendMedicamentos paca) {
        try {
            control.pacatendMedicamentosJpa.destroy(paca.getPacatendMedicamentosPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con medicamento se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar paciente con medicamento", "Atención"));
        }
    }

    public String getPacAtendId() {
        return pacAtendId;
    }

    public void setPacAtendId(String pacAtendId) {
        this.pacAtendId = pacAtendId;
    }

    public int getMedicamId() {
        return medicamId;
    }

    public void setMedicamId(int medicamId) {
        this.medicamId = medicamId;
    }

    public String getCantAdmi() {
        return cantAdmi;
    }

    public void setCantAdmi(String cantAdmi) {
        this.cantAdmi = cantAdmi;
    }

    public List<PacatendMedicamentos> getListPacAtendidoMedicamento() {
        return listPacAtendidoMedicamento;
    }

    public void setListPacAtendidoMedicamento(List<PacatendMedicamentos> listPacAtendidoMedicamento) {
        this.listPacAtendidoMedicamento = listPacAtendidoMedicamento;
    }

    public List<Pacientesatendidos> getListPacAtendidos() {
        return listPacAtendidos;
    }

    public void setListPacAtendidos(List<Pacientesatendidos> listPacAtendidos) {
        this.listPacAtendidos = listPacAtendidos;
    }

    public Map<String, String> getMap_pacAtendidos() {
        return map_pacAtendidos;
    }

    public void setMap_pacAtendidos(Map<String, String> map_pacAtendidos) {
        this.map_pacAtendidos = map_pacAtendidos;
    }

    public List<Medicamentos> getListMedicamento() {
        return listMedicamento;
    }

    public void setListMedicamento(List<Medicamentos> listMedicamento) {
        this.listMedicamento = listMedicamento;
    }

    public Map<String, Integer> getMap_Medicamento() {
        return map_Medicamento;
    }

    public void setMap_Medicamento(Map<String, Integer> map_Medicamento) {
        this.map_Medicamento = map_Medicamento;
    }

    public PacatendMedicamentos getPacatendMedicamentos() {
        return pacatendMedicamentos;
    }

    public void setPacatendMedicamentos(PacatendMedicamentos pacatendMedicamentos) {
        this.pacatendMedicamentos = pacatendMedicamentos;
    }
}
