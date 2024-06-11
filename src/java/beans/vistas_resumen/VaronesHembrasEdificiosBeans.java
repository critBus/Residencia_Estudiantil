
package beans.vistas_resumen;


import beans.control;
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
import entities.Pacientesatendidos;
import java.io.Serializable;
import java.text.DecimalFormat;
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
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

@Named(value = "varonesHembrasEdificiosBeans")
@ManagedBean
@SessionScoped
public class VaronesHembrasEdificiosBeans {
    List<Edificio> listEdificio = new ArrayList<>();
    List<Becado> listbecad = new ArrayList<>();
     public void cargarList() {

        
        listEdificio = control.edificioJPA.findEdificioEntities();
        listbecad = control.becadoJPA.findBecadoEntities();
        
    }
     
     public boolean es_de_este_edificio(Edificio edificio_a_coincidir,Becado becado){
         Cuarto cuarto=becado.getCuarto();
         if (cuarto!=null){
             Piso piso=cuarto.getPiso();
             if (piso!=null){
                 Edificio edificio_del_becado=piso.getEdificio();
                 return edificio_del_becado!=null?edificio_del_becado.getId().equals(edificio_a_coincidir.getId()):false;
             }
         }
         
         return false;
     }
     
     public int obtener_cantidad_hembras(Edificio edificio){
         int cantidad=0;
         for (Becado be : listbecad) {

            if (es_de_este_edificio(edificio,be)&&!be.getSexo()) {
                cantidad++;
            }

        }
         return cantidad;
     }
     
     public int obtener_cantidad_varones(Edificio edificio){
         int cantidad=0;
         for (Becado be : listbecad) {

            if (es_de_este_edificio(edificio,be)&&be.getSexo()) {
                cantidad++;
            }

        }
         return cantidad;
     }
     
     public int obtener_cantidad_becados(Edificio edificio){
        int cantidad=0;
         for (Becado be : listbecad) {

            if (es_de_este_edificio(edificio,be)) {
                cantidad++;
            }

        }
         return cantidad;
     }
     
     public double porcentaje_hembras(Edificio edificio){
         int cantidad_becados =obtener_cantidad_becados(edificio);
         int cantidad_hembras=obtener_cantidad_hembras(edificio);
         return cantidad_becados>0&&cantidad_hembras>0?((double)cantidad_hembras/(double)cantidad_becados)*100:0;
     }
     public double porcentaje_varones(Edificio edificio){
         int cantidad_becados =obtener_cantidad_becados(edificio);
         int cantidad_varones=obtener_cantidad_varones(edificio);
         return cantidad_becados>0&&cantidad_varones>0?((double)cantidad_varones/(double)cantidad_becados)*100:0;
     }
     
     
     public String porcentaje_varones_str(Edificio edificio){
         double porcentaje=porcentaje_varones(edificio);
         return porcentaje>0?UtilsResumenes.formatDecimal(porcentaje)+"%":"";
     }
     public String porcentaje_hembras_str(Edificio edificio){
         double porcentaje=porcentaje_hembras(edificio);
         return porcentaje>0?UtilsResumenes.formatDecimal(porcentaje)+"%":"";
     }

     
    public List<Edificio> getListEdificio() {
        return listEdificio;
    }

    public void setListEdificio(List<Edificio> listEdificio) {
        this.listEdificio = listEdificio;
    }

    public List<Becado> getListbecad() {
        return listbecad;
    }

    public void setListbecad(List<Becado> listbecad) {
        this.listbecad = listbecad;
    }
     
     
}

 