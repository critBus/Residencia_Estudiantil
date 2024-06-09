
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Articuloreglam;
import entities.ArticuloreglamPK;
import entities.Capituloreglam;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@Named(value = "articuloReglamBeans")
@ManagedBean
@SessionScoped
public class articuloReglamBeans implements Serializable{

    String id;
    String capituloId;
    String descripcion;

    List<Articuloreglam> listArt = new ArrayList<>();

    List<Capituloreglam> listCap = new ArrayList<>();
    Map<String, String> map_capitulos = new HashMap<>();

    Articuloreglam artReglam;

    public void cargarList() {

        listArt = control.articuloReglamJPA.findArticuloreglamEntities();
        listCap = control.capituloReglamJPA.findCapituloreglamEntities();

        map_capitulos.clear();
        for (Capituloreglam c : listCap) {
            map_capitulos.put(c.getNombre(), c.getId());
        }
    }

    public void insert() {
            
        Capituloreglam cr = control.capituloReglamJPA.findCapituloreglam(capituloId);

        if (capituloId.isEmpty() || id.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Articuloreglam ar : listArt) {
                    if (ar.getArticuloreglamPK().getId().equals(id) && ar.getArticuloreglamPK().getCapituloid().equals(capituloId)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya está registrado este artículo en el reglamento", "Atención"));
                        return;
                    }
                }

                ArticuloreglamPK arPK = new ArticuloreglamPK(id, capituloId);
                
                control.articuloReglamJPA.create(new Articuloreglam(arPK, descripcion, cr));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El artículo ha sido insertado", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        ArticuloreglamPK artiPK = new ArticuloreglamPK(id, capituloId);
        Articuloreglam a = control.articuloReglamJPA.findArticuloreglam(artiPK);


        if (!descripcion.isEmpty() && !descripcion.equals(artReglam.getDescripcion())) {
            a.setDescripcion(descripcion);
            flag = true;
        } else if (descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'Descripción' está vacío", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.articuloReglamJPA.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El artículo ha sido modificado", "Atención"));
            
            } catch (Exception e) {
                Logger.getLogger(edificioBeans.class.getName()).log(Level.SEVERE, null, e);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    
    public void delete(Articuloreglam areglam){
        try {
            control.articuloReglamJPA.destroy(areglam.getArticuloreglamPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El artículo se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el artículo", "Atención"));

        }
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapituloId() {
        return capituloId;
    }

    public void setCapituloId(String capituloId) {
        this.capituloId = capituloId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Articuloreglam> getListArt() {
        return listArt;
    }

    public void setListArt(List<Articuloreglam> listArt) {
        this.listArt = listArt;
    }

    public List<Capituloreglam> getListCap() {
        return listCap;
    }

    public void setListCap(List<Capituloreglam> listCap) {
        this.listCap = listCap;
    }

    public Map<String, String> getMap_capitulos() {
        return map_capitulos;
    }

    public void setMap_capitulos(Map<String, String> map_capitulos) {
        this.map_capitulos = map_capitulos;
    }

    public Articuloreglam getArtReglam() {
        return artReglam;
    }

    public void setArtReglam(Articuloreglam artReglam) {
        this.artReglam = artReglam;
    }

}
