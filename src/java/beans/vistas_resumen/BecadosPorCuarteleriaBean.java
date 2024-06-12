/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.vistas_resumen;

import beans.control;
import beans.vistas_resumen.util.Cuarteleria;
import beans.vistas_resumen.util.Guardia;
import beans.vistas_resumen.util.UtilsResumenes;
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

@Named(value = "becadosPorCuarteleriaBean")
@ManagedBean
@SessionScoped
public class BecadosPorCuarteleriaBean {

    public static final String VALOR_EN_BD_ASPECTO_ASISTENCIA = "Asistencia";
    List<Evalcuarteleria> listEvalCuartel = new ArrayList<>();
    List<Cuarteleria> lista_cuarteleria = new ArrayList<>();

    public void cargarList() {

        listEvalCuartel = control.evalCuarteleriaJPA.findEvalcuarteleriaEntities();

        actualizar_cuartalerias();

    }

    public void actualizar_cuartalerias() {
        HashMap<Date, Cuarteleria> map_cuartelerias = new HashMap<Date, Cuarteleria>();
        for (Evalcuarteleria evaluacion : listEvalCuartel) {
            Date fecha = evaluacion.getEvalcuarteleriaPK().getFecha();
            String nombre = nombre_y_apellidos(evaluacion.getBecado());
            Cuarteleria guardia = null;
            if (map_cuartelerias.containsKey(fecha)) {
                guardia = map_cuartelerias.get(fecha);
            } else {
                guardia = new Cuarteleria();
                guardia.fecha = fecha;
                map_cuartelerias.put(fecha, guardia);
            }
            guardia.cantidad++;
            if (el_becado_realizo_cuarteleria(evaluacion)) {
                guardia.cantidad_cumplieron++;
                guardia.lista_cumplieron.add(nombre);
            } else {
                guardia.cantidad_incumplieron++;
                guardia.lista_incumplieron.add(nombre);
            }
        }
        lista_cuarteleria = new ArrayList<>(map_cuartelerias.values());
        for (int i = 0; i < lista_cuarteleria.size(); i++) {
            Cuarteleria guardia = lista_cuarteleria.get(i);
            guardia.porcentaje_cumplieron = guardia.cantidad > 0 && guardia.cantidad_cumplieron > 0 ? ((double) guardia.cantidad_cumplieron / (double) guardia.cantidad) * 100 : 0;
            guardia.porcentaje_incumplieron = guardia.cantidad > 0 && guardia.cantidad_incumplieron > 0 ? ((double) guardia.cantidad_incumplieron / (double) guardia.cantidad) * 100 : 0;
            guardia.porcentaje_cumplieron_str = guardia.porcentaje_cumplieron > 0 ? UtilsResumenes.formatDecimal(guardia.porcentaje_cumplieron) + "%" : "-";
            guardia.porcentaje_incumplieron_str = guardia.porcentaje_incumplieron > 0 ? UtilsResumenes.formatDecimal(guardia.porcentaje_incumplieron) + "%" : "-";
        }
    }

    public boolean el_becado_realizo_cuarteleria(Evalcuarteleria evaluacion) {
        for (EvalcuarteleriaAspectevalcuartel eva : evaluacion.getEvalcuarteleriaAspectevalcuartelList()) {
            if (eva.getAspectEvalcuartel().getName().equals(VALOR_EN_BD_ASPECTO_ASISTENCIA)) {
                return eva.getValue() > 0;
            }
        }

        return false;
    }

    public String realizo_cuarteleria(Evalcuarteleria evaluacion) {
        return el_becado_realizo_cuarteleria(evaluacion) ? "Si" : "No";
//        for(EvalcuarteleriaAspectevalcuartel eva:evaluacion.getEvalcuarteleriaAspectevalcuartelList()){
//            if(eva.getAspectEvalcuartel().getName().equals(VALOR_EN_BD_ASPECTO_ASISTENCIA)){
//                return eva.getValue()>0?"Si":"No";
//            }
//        }
//        
//        return "No";
    }

    public String dateFormat(Date fecha) {
        return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + (fecha.getYear() + 1900);

    }

    public String nombre_y_apellidos(Becado becado) {
        return becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();
    }

    public List<Evalcuarteleria> getListEvalCuartel() {
        return listEvalCuartel;
    }

    public void setListEvalCuartel(List<Evalcuarteleria> listEvalCuartel) {
        this.listEvalCuartel = listEvalCuartel;
    }

    public List<Cuarteleria> getLista_cuarteleria() {
        return lista_cuarteleria;
    }

    public void setLista_cuarteleria(List<Cuarteleria> lista_cuarteleria) {
        this.lista_cuarteleria = lista_cuarteleria;
    }

}
