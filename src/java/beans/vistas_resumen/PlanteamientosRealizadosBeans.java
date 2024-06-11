/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.vistas_resumen;


import beans.control;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuartel;
import entities.Cuarto;
import entities.CuartoPK;
import entities.Piso;
import entities.PisoPK;
import entities.Enfermedades;
import entities.Medicamentos;
import entities.Trabajoprod;
import entities.Edificio;
import entities.Becado;
import entities.Evalcuarteleria;
import entities.EvalcuarteleriaAspectevalcuartel;
import entities.Evalguardia;
import entities.Pacientesatendidos;
import entities.Planteamientos;
import entities.Trabajador;
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
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

@Named(value = "planteamientosRealizadosBeans")
@ManagedBean
@SessionScoped
public class PlanteamientosRealizadosBeans {
    List<Planteamientos> listPlantea = new ArrayList<>();
    public void cargarList() {
        listPlantea = control.planteamientosJpa.findPlanteamientosEntities();
        

    }
    public String dateFormat(Date fecha) {
        return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + (fecha.getYear() + 1900);

    }
    public String nombre_y_apellidos(Becado becado) {
        return becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();
    }

    public List<Planteamientos> getListPlantea() {
        return listPlantea;
    }

    public void setListPlantea(List<Planteamientos> listPlantea) {
        this.listPlantea = listPlantea;
    }
    
}
