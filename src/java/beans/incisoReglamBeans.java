
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Articuloreglam;
import entities.ArticuloreglamPK;
import entities.Capituloreglam;
import entities.Incisoreglam;
import entities.IncisoreglamPK;
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

@Named(value = "incisoReglamBeans")
@ManagedBean
@SessionScoped
public class incisoReglamBeans implements Serializable{

    String id;
    String articuloId;
    String capituloId;
    String descripcion;
    
    List<Incisoreglam> listInc = new ArrayList<>();
    
    List<Articuloreglam> listArt = new ArrayList<>();
    Map<String, String> map_articulos = new HashMap<>();
    
    List<Capituloreglam> listCap = new ArrayList<>();
    Map<String, String> map_capitulos = new HashMap<>();

    Incisoreglam incReglam;
    
    public void cargarList() {

        listInc = control.incisoReglamJPA.findIncisoreglamEntities();
        
        listArt = control.articuloReglamJPA.findArticuloreglamEntities();
        listCap = control.capituloReglamJPA.findCapituloreglamEntities();

        map_capitulos.clear();
        for (Capituloreglam c : listCap) {
            map_capitulos.put(c.getNombre(), c.getId());
        }
        
        String aux;
        map_articulos.clear();
        for (Articuloreglam c : listArt) {
            aux = " Artículo: " + c.getArticuloreglamPK().getId();
            map_articulos.put(aux, c.getArticuloreglamPK().getId());
        }
    }
    
    
    public void insert() {
        
        Capituloreglam cr = control.capituloReglamJPA.findCapituloreglam(capituloId);
        
        if (articuloId.isEmpty() || id.isEmpty() || capituloId.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));

        } else {
            
            try {
                
                for (Incisoreglam in : listInc) {
                    if (in.getIncisoreglamPK().getId().equals(id) && in.getIncisoreglamPK().getArticuloid().equals(articuloId) && in.getIncisoreglamPK().getCapituloid().equals(capituloId)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya está registrado este inciso en el reglamento", "Atención"));
                        return;
                    }
                }
                
                IncisoreglamPK i = new IncisoreglamPK(id, articuloId, capituloId);

                ArticuloreglamPK arPK = new ArticuloreglamPK(articuloId, capituloId);
                Articuloreglam artic = control.articuloReglamJPA.findArticuloreglam(arPK);
                
                control.incisoReglamJPA.create(new Incisoreglam(i, descripcion, artic));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El inciso ha sido insertado", "Atención"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

            }

        }
    }
    
    
    public void edit() {
        boolean flag = false;
        int count = 0;
        
        IncisoreglamPK inPK = new IncisoreglamPK(id, articuloId, capituloId);
        Incisoreglam a = control.incisoReglamJPA.findIncisoreglam(inPK);       
        
        if (!descripcion.isEmpty() && !descripcion.equals(incReglam.getDescripcion())) {
            a.setDescripcion(descripcion);
            flag = true;
        } else if (descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'Descripción' está vacío", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.incisoReglamJPA.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El inciso ha sido modificado", "Atención"));
            
            } catch (Exception e) {
                Logger.getLogger(incisoReglamBeans.class.getName()).log(Level.SEVERE, null, e);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public void delete(Incisoreglam ineglam){
        try {
            control.incisoReglamJPA.destroy(ineglam.getIncisoreglamPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El inciso se ha eliminado", "Atención"));

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

    public String getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(String articuloId) {
        this.articuloId = articuloId;
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

    public List<Incisoreglam> getListInc() {
        return listInc;
    }

    public void setListInc(List<Incisoreglam> listInc) {
        this.listInc = listInc;
    }

    public List<Articuloreglam> getListArt() {
        return listArt;
    }

    public void setListArt(List<Articuloreglam> listArt) {
        this.listArt = listArt;
    }

    public Map<String, String> getMap_articulos() {
        return map_articulos;
    }

    public void setMap_articulos(Map<String, String> map_articulos) {
        this.map_articulos = map_articulos;
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

    public Incisoreglam getIncReglam() {
        return incReglam;
    }

    public void setIncReglam(Incisoreglam incReglam) {
        this.incReglam = incReglam;
    }
    
}
