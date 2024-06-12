/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import entities.Cuarto;
import entities.CuartoPK;
import entities.Edificio;
import entities.Piso;
import entities.PisoPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@Named(value = "cuartoBeans")
@ManagedBean
@SessionScoped
public class cuartoBeans implements Serializable{

    String id;
    String idPiso;
    String idEdif;

    List<Cuarto> listCuart = new ArrayList<>();
    
    List<Piso> listPiso = new ArrayList<>();
    Map<String, String> map_piso = new HashMap<>();

    List<Edificio> listEdif = new ArrayList<>();
    Map<String, String> map_edif = new HashMap<>();
    
    Cuarto cuarto;

    public cuartoBeans() {
    }

    public void cargarList() {
        listCuart = control.cuartoJPA.findCuartoEntities();
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
        
        Edificio ed = control.edificioJPA.findEdificio(idEdif);
        
        if (idPiso.isEmpty() || id == null || idEdif.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {


                for (Cuarto trab : listCuart) {
                    if (trab.getCuartoPK().getId().equals(id) && trab.getCuartoPK().getPisoid().equals(idPiso) && trab.getCuartoPK().getEdificioid().equals(idEdif)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe el cuarto", "Atención"));
                        return;
                    }
                }
                CuartoPK t = new CuartoPK(id, idPiso, idEdif);

                PisoPK pPK = new PisoPK(idPiso,idEdif);
                Piso p = control.pisoJPA.findPiso(pPK);
                
                control.cuartoJPA.create(new Cuarto(t, p));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El Cuarto ha sido insertado", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }

    /*public void delete(Cuarto cuart) {
        try {
            control.cuartoJPA.destroy(cuart.getCuartoPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El trabajo productivo se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el trabajo productivo", "Atención"));
        }
    }
*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(String idPiso) {
        this.idPiso = idPiso;
    }

    public String getIdEdif() {
        return idEdif;
    }

    public void setIdEdif(String idEdif) {
        this.idEdif = idEdif;
    }

    public List<Cuarto> getListCuart() {
        return listCuart;
    }

    public void setListCuart(List<Cuarto> listCuart) {
        this.listCuart = listCuart;
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

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }
   
}
