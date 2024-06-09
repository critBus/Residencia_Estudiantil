
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Becado;
import entities.Pacientesatendidos;
import entities.Medico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

@Named(value = "pacientesAtendidosBeans")
@ManagedBean
@SessionScoped
public class pacientesAtendidosBeans implements Serializable {
    
    String id;
    Date fecha;
    String becadoCi;
    String tipoConsulta;
    String medicoCiMedico;
    
    List<Pacientesatendidos> listpacientesAtendidos = new ArrayList<>();
    
    List<Becado> listBecados = new ArrayList<>();
    Map<String, String> map_becado = new HashMap<>();
    
    List<Medico> listMedicos = new ArrayList<>();
    Map<String, String> map_medicos = new HashMap<>();
    
    Pacientesatendidos pacientesatendidos;
    
    public void cargarList(){
        
        listpacientesAtendidos = control.pacientesatendidosJpa.findPacientesatendidosEntities();
        
        listMedicos = control.medicoJpa.findMedicoEntities();
        listBecados = control.becadoJPA.findBecadoEntities();
        
        map_becado.clear();
        for (Becado t : listBecados) {
            if (t.getSegundonombre() == null) {
                map_becado.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_becado.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }
        
        map_medicos.clear();
        String aux1;
        for (Medico m : listMedicos) {
            if (m.getNombre() != null) {
                aux1 = m.getNombre() + " " + m.getApellidos();
                map_medicos.put(aux1, m.getCiMedico());
            }
        }
    }
    
    public void insert(){
        
        Becado bec = control.becadoJPA.findBecado(becadoCi);
        Medico atiende = control.medicoJpa.findMedico(medicoCiMedico);
        
        if (id.isEmpty() || fecha == null || becadoCi.isEmpty() || tipoConsulta.isEmpty() || medicoCiMedico.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        }else{
            try {
                
                for (Pacientesatendidos pa : listpacientesAtendidos) {
                    if (id.equals(pa.getId())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe el paciente", "Atención"));
                        return;
                    }

                }
                control.pacientesatendidosJpa.create(new Pacientesatendidos(id, fecha, bec, atiende, tipoConsulta));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El paciente ha sido insertada", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        Medico atiende = control.medicoJpa.findMedico(medicoCiMedico);
        
        Becado becado = control.becadoJPA.findBecado(becadoCi);

        Pacientesatendidos e = control.pacientesatendidosJpa.findPacientesatendidos(id);

        if (!becadoCi.isEmpty() && !becadoCi.equals(pacientesatendidos.getBecadoci().getCi())) {
            e.setBecadoci(becado);
            flag = true;
        
        if (!medicoCiMedico.isEmpty() && !medicoCiMedico.equals(pacientesatendidos.getMedicociMedico().getCiMedico())) {
            e.setMedicociMedico(atiende);
            flag = true;
        }
        if (tipoConsulta.isEmpty() && tipoConsulta.equals(pacientesatendidos.getTipoconsulta())) {
            e.setTipoconsulta(tipoConsulta);
            flag = true;
        }
        }else if (becadoCi.isEmpty() || medicoCiMedico.isEmpty() || tipoConsulta.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.pacientesatendidosJpa.edit(e);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El paciente ha sido modificada", "Atención"));
            
            } catch (Exception ex) {
                Logger.getLogger(pacientesAtendidosBeans.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public void delete(Pacientesatendidos pac){
        try {
            control.pacientesatendidosJpa.destroy(pac.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El paciente se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el paciente", "Atención"));

        }
    }
    
    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    } 
    
    public String nombreApellmedico(Medico medico) {

        String nombApellmedico;

        return nombApellmedico = medico.getNombre() + " " + medico.getApellidos();

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBecadoCi() {
        return becadoCi;
    }

    public void setBecadoCi(String becadoCi) {
        this.becadoCi = becadoCi;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getMedicoCiMedico() {
        return medicoCiMedico;
    }

    public void setMedicoCiMedico(String medicoCiMedico) {
        this.medicoCiMedico = medicoCiMedico;
    }

    public List<Pacientesatendidos> getListpacientesAtendidos() {
        return listpacientesAtendidos;
    }

    public void setListpacientesAtendidos(List<Pacientesatendidos> listpacientesAtendidos) {
        this.listpacientesAtendidos = listpacientesAtendidos;
    }

    public List<Becado> getListBecados() {
        return listBecados;
    }

    public void setListBecados(List<Becado> listBecados) {
        this.listBecados = listBecados;
    }

    public Map<String, String> getMap_becado() {
        return map_becado;
    }

    public void setMap_becado(Map<String, String> map_becado) {
        this.map_becado = map_becado;
    }

    public List<Medico> getListMedicos() {
        return listMedicos;
    }

    public void setListMedicos(List<Medico> listMedicos) {
        this.listMedicos = listMedicos;
    }

    public Map<String, String> getMap_medicos() {
        return map_medicos;
    }

    public void setMap_medicos(Map<String, String> map_medicos) {
        this.map_medicos = map_medicos;
    }

    public Pacientesatendidos getPacientesatendidos() {
        return pacientesatendidos;
    }

    public void setPacientesatendidos(Pacientesatendidos pacientesatendidos) {
        this.pacientesatendidos = pacientesatendidos;
    }
}
