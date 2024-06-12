/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.vistas_resumen;

import beans.control;
import beans.vistas_resumen.util.Guardia;
import beans.vistas_resumen.util.UtilsResumenes;
import com.sun.faces.util.CollectionsUtils;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Cuarto;
import entities.CuartoPK;
import entities.Piso;
import entities.PisoPK;
import entities.Enfermedades;
import entities.Medicamentos;
import entities.Trabajoprod;
import entities.Edificio;
import entities.Becado;
import entities.Evalguardia;
import entities.Pacientesatendidos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

@Named(value = "becadosPorGuardiaBean")
@ManagedBean
@SessionScoped
public class BecadosPorGuardiaBean {

    List<Evalguardia> listEvalGuardia = new ArrayList<>();
    List<Guardia> lista_guardias = new ArrayList<>();

    public void cargarList() {

        listEvalGuardia = control.evalguardiaJpa.findEvalguardiaEntities();
        actualizar_guardia();

    }

    public void actualizar_guardia() {
        HashMap<Date, Guardia> map_guardias = new HashMap<Date, Guardia>();
        for (Evalguardia evaluacion : listEvalGuardia) {
            Date fecha = evaluacion.getEvalguardiaPK().getFecha();
            String nombre=nombre_y_apellidos(evaluacion.getBecado());
            Guardia guardia = null;
            if (map_guardias.containsKey(fecha)) {
                guardia = map_guardias.get(fecha);
            } else {
                guardia = new Guardia();
                guardia.fecha = fecha;
                map_guardias.put(fecha, guardia);
            }
            guardia.cantidad++;
            if (el_becado_realizo_guardia(evaluacion)) {
                guardia.cantidad_cumplieron++;
                guardia.lista_cumplieron.add(nombre);
            } else {
                guardia.cantidad_incumplieron++;
                guardia.lista_incumplieron.add(nombre);
            }
        }
        lista_guardias = new ArrayList<>(map_guardias.values());
        for (int i = 0; i < lista_guardias.size(); i++) {
            Guardia guardia = lista_guardias.get(i);
            guardia.porcentaje_cumplieron = guardia.cantidad > 0 && guardia.cantidad_cumplieron > 0 ? ((double) guardia.cantidad_cumplieron / (double) guardia.cantidad) * 100 : 0;
            guardia.porcentaje_incumplieron = guardia.cantidad > 0 && guardia.cantidad_incumplieron > 0 ? ((double) guardia.cantidad_incumplieron / (double) guardia.cantidad) * 100 : 0;
            guardia.porcentaje_cumplieron_str = guardia.porcentaje_cumplieron > 0 ? UtilsResumenes.formatDecimal(guardia.porcentaje_cumplieron) + "%" : "-";
            guardia.porcentaje_incumplieron_str = guardia.porcentaje_incumplieron > 0 ? UtilsResumenes.formatDecimal(guardia.porcentaje_incumplieron) + "%" : "-";
        }
    }

    public boolean el_becado_realizo_guardia(Evalguardia evaluacion) {
        return evaluacion.getEvaluacion() > 0;
    }

    public String realizo_guardia(Evalguardia evaluacion) {
        return el_becado_realizo_guardia(evaluacion) ? "Si" : "No";
    }

    public String dateFormat(Date fecha) {
        return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + (fecha.getYear() + 1900);

    }

    public String nombre_y_apellidos(Becado becado) {
        return becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();
    }

    public List<Evalguardia> getListEvalGuardia() {
        return listEvalGuardia;
    }

    public void setListEvalGuardia(List<Evalguardia> listEvalGuardia) {
        this.listEvalGuardia = listEvalGuardia;
    }

    public List<Guardia> getLista_guardias() {
        return lista_guardias;
    }

    public void setLista_guardias(List<Guardia> lista_guardias) {
        this.lista_guardias = lista_guardias;
    }

    

}
