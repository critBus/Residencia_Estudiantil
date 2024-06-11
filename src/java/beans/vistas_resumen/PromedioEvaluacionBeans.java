/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.vistas_resumen;

import beans.control;
import beans.vistas_resumen.util.Evaluacion;
import beans.vistas_resumen.util.UtilsResumenes;
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
import entities.Evalbecado;
import entities.Pacientesatendidos;
import entities.Tipoevalbecado;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

@Named(value = "promedioEvaluacionBeans")
@ManagedBean
@SessionScoped
public class PromedioEvaluacionBeans {

    public static final String VALOR_EN_BD_EXELENTE = "E";
    public static final String VALOR_EN_BD_BIEN = "B";
    public static final String VALOR_EN_BD_REGULAR = "R";
    public static final String VALOR_EN_BD_MAL = "M";

    List<Becado> listbecad = new ArrayList<>();
    List<Tipoevalbecado> listTipoEvalBecad = new ArrayList<>();
    List<Evalbecado> listEvalBecado = new ArrayList<>();

    List<String> tipos_permitidos = Arrays.asList(
            VALOR_EN_BD_EXELENTE, VALOR_EN_BD_BIEN, VALOR_EN_BD_REGULAR, VALOR_EN_BD_MAL
    );

    int cantidad_total_becados = 0;

    List<Evaluacion> listRepresentacionDeEvaluacion = new ArrayList<>();

    public void cargarList() {

        listTipoEvalBecad = control.tipoevalbecadoJpa.findTipoevalbecadoEntities();
        listbecad = control.becadoJPA.findBecadoEntities();
        listEvalBecado = control.evalbecadoJpa.findEvalbecadoEntities();
        cantidad_total_becados = listbecad.size();

        actualizar_lista_de_representacion_de_evaluaciones();

    }

    private void actualizar_lista_de_representacion_de_evaluaciones() {
        listRepresentacionDeEvaluacion = new ArrayList<>();
        int cantidad_total = 0;
        for (String tipo : tipos_permitidos) {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.cantidad = obtener_cantidad_evaluaciones(tipo);
            evaluacion.nombre = tipo;
            evaluacion.nombre_completo = obtener_nombre_completo_evaluacion(tipo);
            listRepresentacionDeEvaluacion.add(evaluacion);
            cantidad_total += evaluacion.cantidad;
        }
        for (int i = 0; i < listRepresentacionDeEvaluacion.size(); i++) {
            Evaluacion evaluacion = listRepresentacionDeEvaluacion.get(i);
            evaluacion.porcentaje = ((double) evaluacion.cantidad / (double) cantidad_total) * 100;
            evaluacion.porcentaje_str = evaluacion.porcentaje > 0 ? UtilsResumenes.formatDecimal(evaluacion.porcentaje) + "%" : "";
        }
    }

    public int obtener_cantidad_evaluaciones(String tipo) {
        int cantidad = 0;
        for (Becado becado : listbecad) {
            String nombre_tipo = obtener_evaluacion_becado(becado);
            if (nombre_tipo.equals(tipo)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public String obtener_evaluacion_becado(Becado b) {
        List<Evalbecado> evalbecadoList = b.getEvalbecadoList();
        if (evalbecadoList != null && !evalbecadoList.isEmpty()) {
            Evalbecado evaluacion_mas_actual_anterior = null;
            for (Evalbecado evaluacion : evalbecadoList) {
                if (evaluacion_mas_actual_anterior == null) {
                    evaluacion_mas_actual_anterior = evaluacion;
                    continue;
                }
                if (es_evaluacion_a_mas_actual_que_b(evaluacion, evaluacion_mas_actual_anterior)) {
                    evaluacion_mas_actual_anterior = evaluacion;
                }

            }

            return obtener_evaluacion(evaluacion_mas_actual_anterior);
        }
        return VALOR_EN_BD_MAL;
    }

    public String obtener_evaluacion(Evalbecado evaluacion) {
        Tipoevalbecado tipo = evaluacion.getTipoevalbecadoid();
        if (tipo != null) {
            for (String nombre_tipo_permitido : tipos_permitidos) {
                if (tipo.getTipo().equals(nombre_tipo_permitido)) {
                    return nombre_tipo_permitido;
                }
            }
        }
        return VALOR_EN_BD_MAL;
    }

    public boolean es_evaluacion_a_mas_actual_que_b(Evalbecado a, Evalbecado b) {
        Date fecha_a = a.getEvalbecadoPK().getFecha();
        Date fecha_b = b.getEvalbecadoPK().getFecha();
        return es_feccha_a_mas_actual_que_b(fecha_a, fecha_b);
    }

    public boolean es_feccha_a_mas_actual_que_b(Date a, Date b) {
        return a.after(b);
    }

    public String obtener_nombre_completo_evaluacion(String evaluacion) {
        if (evaluacion.equals(VALOR_EN_BD_EXELENTE)) {
            return "Exelente";
        }
        if (evaluacion.equals(VALOR_EN_BD_BIEN)) {
            return "Bien";
        }
        if (evaluacion.equals(VALOR_EN_BD_REGULAR)) {
            return "Regular";
        }
        return "Mal";
    }

    public List<Becado> getListbecad() {
        return listbecad;
    }

    public void setListbecad(List<Becado> listbecad) {
        this.listbecad = listbecad;
    }

    public List<Tipoevalbecado> getListTipoEvalBecad() {
        return listTipoEvalBecad;
    }

    public void setListTipoEvalBecad(List<Tipoevalbecado> listTipoEvalBecad) {
        this.listTipoEvalBecad = listTipoEvalBecad;
    }

    public List<Evalbecado> getListEvalBecado() {
        return listEvalBecado;
    }

    public void setListEvalBecado(List<Evalbecado> listEvalBecado) {
        this.listEvalBecado = listEvalBecado;
    }

    public List<String> getTipos_permitidos() {
        return tipos_permitidos;
    }

    public void setTipos_permitidos(List<String> tipos_permitidos) {
        this.tipos_permitidos = tipos_permitidos;
    }

    public int getCantidad_total_becados() {
        return cantidad_total_becados;
    }

    public void setCantidad_total_becados(int cantidad_total_becados) {
        this.cantidad_total_becados = cantidad_total_becados;
    }

    public List<Evaluacion> getListRepresentacionDeEvaluacion() {
        return listRepresentacionDeEvaluacion;
    }

    public void setListRepresentacionDeEvaluacion(List<Evaluacion> listRepresentacionDeEvaluacion) {
        this.listRepresentacionDeEvaluacion = listRepresentacionDeEvaluacion;
    }

}
