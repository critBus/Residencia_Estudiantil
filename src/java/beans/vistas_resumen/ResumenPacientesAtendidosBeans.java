
package beans.vistas_resumen;

import beans.*;
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

@Named(value = "resumenPacientesAtendidosBeans")
@ManagedBean
@SessionScoped
public class ResumenPacientesAtendidosBeans implements Serializable {
    
    
    
    List<Pacientesatendidos> listpacientesAtendidos = new ArrayList<>();
    
    
    
    public void cargarList(){
        
        listpacientesAtendidos = control.pacientesatendidosJpa.findPacientesatendidosEntities();
        
        
    }
    
    
    public String dateFormat(Date fecha) {
        return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + (fecha.getYear() + 1900);

    }
    
    public String nombreApellbeca(Becado becado) {

        return becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    } 
    
    public String nombreApellmedico(Medico medico) {

        return  medico.getNombre() + " " + medico.getApellidos();

    }
    
    

    public List<Pacientesatendidos> getListpacientesAtendidos() {
        return listpacientesAtendidos;
    }

    public void setListpacientesAtendidos(List<Pacientesatendidos> listpacientesAtendidos) {
        this.listpacientesAtendidos = listpacientesAtendidos;
    }

    
}
