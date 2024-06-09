/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@Named(value = "cuartoBeans")
@ManagedBean
@SessionScoped
public class cuartoBeans implements Serializable{

    String idCuarto;
    String idPiso;
    String idEdif;

    List<Cuarto> listCuart = new ArrayList<>();
    List<Piso> listPiso = new ArrayList<>();
    List<Edificio> listEdif = new ArrayList<>();

    Map<String, String> map_piso = new HashMap<>();
    Map<String, String> map_edif = new HashMap<>();
    String ubicacion;
    Cuarto cuarto;

    /**
     * Creates a new instance of cuartoBeans
     */
    public cuartoBeans() {
    }

    public void cargarList() {
        listCuart = control.cuartoJPA.findCuartoEntities();
        listPiso = control.pisoJPA.findPisoEntities();
        listEdif = control.edificioJPA.findEdificioEntities();

        String aux;
        map_piso.clear();
        for (Piso pi : listPiso) {
            map_piso.put("Piso " + pi.getPisoPK().getId(), pi.getPisoPK().getId());
        }
        
        map_edif.clear();
        for (Edificio p : listEdif) {
            map_edif.put("Edificio " + p.getNombre(), p.getId());
        }
        
        map_edif.clear();
        for (Edificio p : listEdif) {
            map_edif.put("Edificio " + p.getNombre(), p.getId());
        }
    }

    public String[] cortar_cadena(String cadena) {
        String[] completo = cadena.split(" ");

        String[] respuesta = new String[2];

        respuesta[0] = completo[0];
        respuesta[1] = completo[1];

        return respuesta;
    }
    

    public void insert() {

        if (ubicacion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));

        } else {

            try {
                String[] codigos = cortar_cadena(ubicacion);
                CuartoPK h = new CuartoPK(idCuarto, codigos[0], codigos[1]);
                Cuarto c = new Cuarto(h);

                c.setPiso(control.pisoJPA.findPiso(new PisoPK(codigos[0], codigos[1])));
                control.cuartoJPA.create(c);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El cuarto ha sido insertado", "Atención"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

            }

        }
    }

    public void delete(Cuarto cuart) {
        try {
            
            control.cuartoJPA.destroy(cuart.getCuartoPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El cuarto ha sido eliminado", "Infoemación"));
        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pude eliminar el cuarto", "Atención"));
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(cuartoBeans.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pude eliminar el cuarto", "Atención"));
        }
    }

    public String getIdCuarto() {
        return idCuarto;
    }

    public void setIdCuarto(String idCuarto) {
        this.idCuarto = idCuarto;
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

    public List<Edificio> getListEdif() {
        return listEdif;
    }

    public void setListEdif(List<Edificio> listEdif) {
        this.listEdif = listEdif;
    }

    public Map<String, String> getMap_piso() {
        return map_piso;
    }

    public void setMap_piso(Map<String, String> map_piso) {
        this.map_piso = map_piso;
    }

    public Map<String, String> getMap_edif() {
        return map_edif;
    }

    public void setMap_edif(Map<String, String> map_edif) {
        this.map_edif = map_edif;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

}
