
package beans;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Edificio;
import entities.Piso;
import entities.PisoPK;
import entities.Trabajoprod;
import entities.TrabajoprodPK;
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

/**
 *
 * @author Euclides
 */

@Named(value = "trabajoProductivoBeans")
@ManagedBean
@SessionScoped
public class trabajoProductivoBeans implements Serializable{
    Date fecha;
    String pisoId;
    String edificioId;
    int evaluacion;

    List<Trabajoprod> listTrabProd = new ArrayList<>();

    List<Piso> listPiso = new ArrayList<>();
    Map<String, String> map_piso = new HashMap<>();

    List<Edificio> listEdif = new ArrayList<>();
    Map<String, String> map_edif = new HashMap<>();

    Trabajoprod trabajoprod;

    public void cargarList() {

        listTrabProd = control.trabProdJPA.findTrabajoprodEntities();
        listPiso = control.pisoJPA.findPisoEntities();
        listEdif = control.edificioJPA.findEdificioEntities();

        map_edif.clear();
        for (Edificio e : listEdif) {
            map_edif.put(e.getNombre(), e.getId());
        }

        String aux;
        map_piso.clear();
        for (Piso p : listPiso) {
            aux = "Piso: " + p.getPisoPK().getId();
            map_piso.put(aux, p.getPisoPK().getId());
        }

    }

    public void insert() {
        
        Edificio ed = control.edificioJPA.findEdificio(edificioId);
        
        if (pisoId.isEmpty() || fecha == null || edificioId.isEmpty() || evaluacion == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {


                for (Trabajoprod trab : listTrabProd) {
                    if (trab.getTrabajoprodPK().getFecha().equals(fecha) && trab.getTrabajoprodPK().getPisoid().equals(pisoId) && trab.getTrabajoprodPK().getEdificioid().equals(edificioId)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe el trabajo productivo", "Atención"));
                        return;
                    }
                }
                TrabajoprodPK t = new TrabajoprodPK(fecha, pisoId, edificioId);

                PisoPK pPK = new PisoPK(pisoId,edificioId);
                Piso p = control.pisoJPA.findPiso(pPK);
                
                control.trabProdJPA.create(new Trabajoprod(t, evaluacion, p));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El Trabajo Productivo ha sido insertado", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        TrabajoprodPK t = new TrabajoprodPK(fecha, pisoId, edificioId);
        Trabajoprod tra = control.trabProdJPA.findTrabajoprod(t);
        
        if (evaluacion != 0 && evaluacion != trabajoprod.getEvaluacion()) {
            tra.setEvaluacion(evaluacion);
            flag = true;
        } else if (evaluacion == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'Evaluacion' está vacío", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.trabProdJPA.edit(tra);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El Trabajo Productivo ha sido modificado", "Atención"));
            } catch (Exception e) {
                Logger.getLogger(edificioBeans.class.getName()).log(Level.SEVERE, null, e);
            }

        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    public void delete(Trabajoprod trabpro) {
        try {
            control.trabProdJPA.destroy(trabpro.getTrabajoprodPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El trabajo productivo se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el trabajo productivo", "Atención"));
        }
    }

    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPisoId() {
        return pisoId;
    }

    public void setPisoId(String pisoId) {
        this.pisoId = pisoId;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<Trabajoprod> getListTrabProd() {
        return listTrabProd;
    }

    public void setListTrabProd(List<Trabajoprod> listTrabProd) {
        this.listTrabProd = listTrabProd;
    }

    public List<Piso> getListPiso() {
        return listPiso;
    }

    public void setListPiso(List<Piso> listPiso) {
        this.listPiso = listPiso;
    }

    public Map<String, String> getMap_piso() {
        return map_piso;
    }

    public void setMap_piso(Map<String, String> map_piso) {
        this.map_piso = map_piso;
    }

    public List<Edificio> getListEdif() {
        return listEdif;
    }

    public void setListEdif(List<Edificio> listEdif) {
        this.listEdif = listEdif;
    }

    public Map<String, String> getMap_edif() {
        return map_edif;
    }

    public void setMap_edif(Map<String, String> map_edif) {
        this.map_edif = map_edif;
    }

    public Trabajoprod getTrabajoprod() {
        return trabajoprod;
    }

    public void setTrabajoprod(Trabajoprod trabajoprod) {
        this.trabajoprod = trabajoprod;
    }

}
