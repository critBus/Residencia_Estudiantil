
package beans;

import controller.exceptions.NonexistentEntityException;
import entities.Becado;
import entities.Sanciones;
import entities.SancionesPK;
import entities.Incisoreglam;
import entities.IncisoreglamPK;
import entities.Articuloreglam;
import entities.ArticuloreglamPK;
import entities.Capituloreglam;
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

@Named(value = "sancionesBeans")
@ManagedBean
@SessionScoped
public class sancionesBeans implements Serializable{
    
    Date fecha;
    String becadoCi;
    String sancion;
    String tiempo;
    String estado;
    String descripcion;
    String incisoId;
    String articuloId;
    String capituloId;
    
    
    List<Sanciones> listSanciones = new ArrayList<>();
    
    List<Becado> listBecados = new ArrayList<>();
    Map<String, String> map_becado = new HashMap<>();
    
    List<Incisoreglam> listInciso = new ArrayList<>();
    Map<String, String> map_inciso = new HashMap<>();
    
    List<Articuloreglam> listArticulo = new ArrayList<>();
    Map<String, String> map_articulo = new HashMap<>();
    
    List<Capituloreglam> listCapitulo = new ArrayList<>();
    Map<String, String> map_capitulo = new HashMap<>();
    
    Sanciones sanciones;
    Becado becado;
    Incisoreglam incisoreglam;
    Articuloreglam articuloreglam;
    Capituloreglam capituloreglam;
    
    public sancionesBeans(){
        
    }
    
    public void cargarList(){
        
        listSanciones = control.sancionesJpa.findSancionesEntities();
        
        listInciso = control.incisoReglamJPA.findIncisoreglamEntities();
        listBecados = control.becadoJPA.findBecadoEntities();
        listArticulo = control.articuloReglamJPA.findArticuloreglamEntities();
        listCapitulo = control.capituloReglamJPA.findCapituloreglamEntities();
        
        map_becado.clear();
        for (Becado t : listBecados) {
            if (t.getSegundonombre() == null) {
                map_becado.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_becado.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }
        
        map_inciso.clear();
        String aux1;
        for (Incisoreglam i : listInciso) {
            if (i.getIncisoreglamPK().getId() != null) {
                aux1 = i.getIncisoreglamPK().getId();
                map_inciso.put(aux1, i.getIncisoreglamPK().getId());
            }
        }
        
        map_articulo.clear();
        String aux2;
        for (Articuloreglam a : listArticulo) {
            if (a.getArticuloreglamPK().getId() != null) {
                aux1 = a.getArticuloreglamPK().getId();
                map_articulo.put(aux1, a.getArticuloreglamPK().getId());
            }
        }
        
        map_capitulo.clear();
        for (Capituloreglam c : listCapitulo) {
            if (c.getId() != null) {
                map_capitulo.put(c.getNombre(), c.getId());
            }
        
    }
  }

        public void insert() {
        
        Capituloreglam cr = control.capituloReglamJPA.findCapituloreglam(capituloId);
        Becado becado = control.becadoJPA.findBecado(becadoCi);
        
        if (becadoCi.isEmpty() || fecha == null || articuloId.isEmpty() || capituloId.isEmpty() || incisoId.isEmpty() || sancion.isEmpty() || tiempo.isEmpty() || estado.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));

        } else {
            
            try {
                
                for (Sanciones in : listSanciones) {
                    if (in.getSancionesPK().getFecha().equals(fecha) && in.getSancionesPK().getBecadoci().equals(becadoCi)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe la sanción", "Atención"));
                        return;
                    }
                }
                
                SancionesPK s = new SancionesPK(fecha, becadoCi);
                
                IncisoreglamPK ipk = new IncisoreglamPK(incisoId, articuloId, capituloId);
                Incisoreglam inc = control.incisoReglamJPA.findIncisoreglam(ipk);
                
                control.sancionesJpa.create(new Sanciones(s, sancion, tiempo, estado, descripcion, inc));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La sanción ha sido insertado", "Atención"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

            }

        }
    }
    
    public void edit() {
        boolean flag = false;
        int count = 0;

        SancionesPK e = new SancionesPK(fecha, becadoCi);
        Sanciones san = new Sanciones(e);

        if (!sancion.isEmpty() && !sancion.equals(sanciones.getSancion())) {
            san.setSancion(sancion);
            flag = true;
        
        if (tiempo.isEmpty() && !tiempo.equals(sanciones.getTiempo())) {
            san.setTiempo(tiempo);
            flag = true;
        }
        if (estado.isEmpty() && !estado.equals(sanciones.getEstado())) {
            san.setEstado(estado);
            flag = true;
        }
        if (descripcion.isEmpty() && !descripcion.equals(sanciones.getDescripcion())) {
            san.setDescripcion(descripcion);
            flag = true;
        }
        }else if (sancion.isEmpty() || tiempo.isEmpty() || estado.isEmpty() || descripcion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.sancionesJpa.edit(san);             
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Sancion ha sido modificada", "Atención"));
            
            } catch (Exception ex) {
                Logger.getLogger(sancionesBeans.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (count == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }
    
    public void delete(Sanciones sanc) {
        try {
            control.sancionesJpa.destroy(sanc.getSancionesPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Sancion se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación de la guardia", "Atención"));
        }
    }

    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBecadoCi() {
        return becadoCi;
    }

    public void setBecadoCi(String becadoCi) {
        this.becadoCi = becadoCi;
    }

    public String getSancion() {
        return sancion;
    }

    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIncisoId() {
        return incisoId;
    }

    public void setIncisoId(String incisoId) {
        this.incisoId = incisoId;
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

    public List<Sanciones> getListSanciones() {
        return listSanciones;
    }

    public void setListSanciones(List<Sanciones> listSanciones) {
        this.listSanciones = listSanciones;
    }

    public List<Becado> getListBecados() {
        return listBecados;
    }

    public void setListBecados(List<Becado> listBecados) {
        this.listBecados = listBecados;
    }

    public Map<String, String> getMap_becado() {
        return map_becado;
    }

    public void setMap_becado(Map<String, String> map_becado) {
        this.map_becado = map_becado;
    }

    public List<Incisoreglam> getListInciso() {
        return listInciso;
    }

    public void setListInciso(List<Incisoreglam> listInciso) {
        this.listInciso = listInciso;
    }

    public Map<String, String> getMap_inciso() {
        return map_inciso;
    }

    public void setMap_inciso(Map<String, String> map_inciso) {
        this.map_inciso = map_inciso;
    }

    public List<Articuloreglam> getListArticulo() {
        return listArticulo;
    }

    public void setListArticulo(List<Articuloreglam> listArticulo) {
        this.listArticulo = listArticulo;
    }

    public Map<String, String> getMap_articulo() {
        return map_articulo;
    }

    public void setMap_articulo(Map<String, String> map_articulo) {
        this.map_articulo = map_articulo;
    }

    public List<Capituloreglam> getListCapitulo() {
        return listCapitulo;
    }

    public void setListCapitulo(List<Capituloreglam> listCapitulo) {
        this.listCapitulo = listCapitulo;
    }

    public Map<String, String> getMap_capitulo() {
        return map_capitulo;
    }

    public void setMap_capitulo(Map<String, String> map_capitulo) {
        this.map_capitulo = map_capitulo;
    }

    public Sanciones getSanciones() {
        return sanciones;
    }

    public void setSanciones(Sanciones sanciones) {
        this.sanciones = sanciones;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public Incisoreglam getIncisoreglam() {
        return incisoreglam;
    }

    public void setIncisoreglam(Incisoreglam incisoreglam) {
        this.incisoreglam = incisoreglam;
    }

    public Articuloreglam getArticuloreglam() {
        return articuloreglam;
    }

    public void setArticuloreglam(Articuloreglam articuloreglam) {
        this.articuloreglam = articuloreglam;
    }

    public Capituloreglam getCapituloreglam() {
        return capituloreglam;
    }

    public void setCapituloreglam(Capituloreglam capituloreglam) {
        this.capituloreglam = capituloreglam;
    }
}
