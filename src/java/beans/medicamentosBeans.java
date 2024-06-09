
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Medicamentos;
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

@Named(value = "medicamentosBeans")
@ManagedBean
@SessionScoped
public class medicamentosBeans implements Serializable{
    
    int id = 0;
    String nombre;
    String tipoDosis;
    
    List<Medicamentos> listMedicamento = new ArrayList<>();
    
    Medicamentos medicamentos;
    
    public void cargarList() {
        listMedicamento = control.medicamentosJpa.findMedicamentosEntities();
    }
    
    public void insert() {
            

        if (tipoDosis.isEmpty() || nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Medicamentos medi : listMedicamento) {
                    if (nombre.equals(medi.getNombre())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un medicamento con el mismo nombre", "Atención"));
                        return;
                    }
                }
                control.medicamentosJpa.create(new Medicamentos(id, nombre, tipoDosis));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El medicamento ha sido insertado", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    public void edit() {
        boolean flag = false;
        int count = 0;
        
        Medicamentos a = control.medicamentosJpa.findMedicamentos(id);

        if (!nombre.isEmpty() && !nombre.equals(medicamentos.getNombre())) {
            a.setNombre(nombre);
            flag = true;
        } else if (nombre.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'nombre' está vacío", "Atención"));
            count++;
        }
        
        if (!tipoDosis.isEmpty() && !tipoDosis.equals(medicamentos.getTipodosisid())) {
            a.setTipodosisid(tipoDosis);
            flag = true;
        } else if (tipoDosis.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'dosis' está vacío", "Atención"));
            count++;
        }
        if (flag) {
            try {
                control.medicamentosJpa.edit(a);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El medicamento ha sido modificado", "Atención"));
            
            } catch (Exception e) {
                Logger.getLogger(medicamentosBeans.class.getName()).log(Level.SEVERE, null, e);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    /*public void delete(Medicamentos medica){
        try {
            control.medicamentosJpa.destroy(medica.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El medicamento se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el medicamento", "Atención"));

        }
    }
*/
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

    public String getTipoDosis() {
        return tipoDosis;
    }

    public void setTipoDosis(String tipoDosis) {
        this.tipoDosis = tipoDosis;
    }

    public List<Medicamentos> getListMedicamento() {
        return listMedicamento;
    }

    public void setListMedicamento(List<Medicamentos> listMedicamento) {
        this.listMedicamento = listMedicamento;
    }

    public Medicamentos getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Medicamentos medicamentos) {
        this.medicamentos = medicamentos;
    }
}
