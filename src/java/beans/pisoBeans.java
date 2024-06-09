
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Becado;
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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@Named(value = "pisoBeans")
@ManagedBean
@SessionScoped
public class pisoBeans implements Serializable{

    String idPiso;
    String jefePiso;
    String jefeLimp;
    String idEdif;

    List<Piso> listPiso = new ArrayList<>();
    List<Edificio> listEdif = new ArrayList<>();
    List<Becado> listBec = new ArrayList<>();

    Map<String, String> map_piso = new HashMap<>();
    Map<String, String> map_edif = new HashMap<>();
    Map<String, String> map_bec = new HashMap<>();

    Piso piso;

    String pisoEdif;

    /**
     * Creates a new instance of pisoBeans
     */
    public pisoBeans() {
    }

    public void cargarList() {
        listPiso = control.pisoJPA.findPisoEntities();
        listEdif = control.edificioJPA.findEdificioEntities();
        listBec = control.becadoJPA.findBecadoEntities();

        map_piso.clear();
        for (Piso pi : listPiso) {
            map_piso.put("Piso " + pi.getPisoPK().getId(), pi.getPisoPK().getId());
        }

        map_edif.clear();
        for (Edificio p : listEdif) {
            map_edif.put("Edificio " + p.getNombre(), p.getId());
        }

        map_bec.clear();
        for (Becado t : listBec) {
            if (t.getSegundonombre() == null) {
                map_bec.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_bec.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }

        for (Piso pi : listPiso) {
            pisoEdif = pi.getEdificio().getNombre() + pi.getPisoPK().getId();
        }

    }

    public String pisoPKBeans(Piso pisopk) {
        String p;

        return p = piso.getEdificio().getNombre() + piso.getPisoPK().getId();

    }

    public void insert() {
        Becado becjefePiso = control.becadoJPA.findBecado(jefePiso);
        Becado becjefeLimp = control.becadoJPA.findBecado(jefeLimp);

        if (idPiso.isEmpty() || idEdif.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                PisoPK p = new PisoPK(idPiso, idEdif);

                for (Piso pis : listPiso) {
                    if (pis.getPisoPK().getEdificioid().equals(idEdif) && pis.getPisoPK().getId().equals(idPiso)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya está registrado este piso en el edificio " + pis.getEdificio().getNombre(), "Atención"));
                        return;
                    }

                    if (!jefePiso.isEmpty() && pis.getJefepiso() != null) {
                        if (pis.getJefepiso().getCi().equals(jefePiso)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un piso con el jefe de piso", "Atención"));
                            return;
                        }
                    }

                    if (!jefeLimp.isEmpty() && pis.getJefelimp() != null) {
                        if (pis.getJefelimp().getCi().equals(jefeLimp)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un piso con el jefe de limpieza", "Atención"));
                            return;
                        }
                    }

                    if (!jefePiso.isEmpty() && !jefeLimp.isEmpty() && jefePiso.equals(jefeLimp)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de piso y el jefe de limpieza que desea insertar, son iguales", "Atención"));
                        return;
                    }

                    if (!jefePiso.isEmpty() && pis.getJefepiso() != null && pis.getJefelimp() != null) {
                        if (pis.getJefelimp().getCi().equals(jefePiso)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de piso está registrado como jefe de limpieza", "Atención"));
                            return;
                        }
                    }

                    if (!jefeLimp.isEmpty() && pis.getJefelimp() != null && pis.getJefepiso() != null) {
                        if (pis.getJefepiso().getCi().equals(jefeLimp)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de limpieza está registrado como jefe de piso", "Atención"));
                            return;
                        }
                    }

                }
                Edificio e = control.edificioJPA.findEdificio(idEdif);
                control.pisoJPA.create(new Piso(p, becjefePiso, becjefeLimp, e));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El piso ha sido insertado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(Piso.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

            }

        }

    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        Becado becjefePiso = control.becadoJPA.findBecado(jefePiso);
        Becado becjefeLimp = control.becadoJPA.findBecado(jefeLimp);

        Piso p = control.pisoJPA.findPiso(piso.getPisoPK());

        if (!jefePiso.isEmpty()) {
            if (piso.getJefepiso() != null && !jefePiso.equals(piso.getJefepiso().getCi())) {
                p.setJefepiso(becjefePiso);
                flag = true;

            } else if (piso.getJefepiso() == null) {
                p.setJefepiso(becjefePiso);
                flag = true;

            }
        } else if (jefePiso.isEmpty() && piso.getJefepiso() != null) {
            p.setJefepiso(becjefePiso);
            flag = true;
        }

        if (!jefeLimp.isEmpty()) {
            if (piso.getJefelimp() != null && !jefeLimp.equals(piso.getJefelimp().getCi())) {
                p.setJefelimp(becjefeLimp);
                flag = true;

            } else if (piso.getJefelimp() == null) {
                p.setJefelimp(becjefeLimp);
                flag = true;

            }
        } else if (jefeLimp.isEmpty() && piso.getJefelimp() != null) {
            p.setJefelimp(becjefeLimp);
            flag = true;
        }

        if (flag) {
            try {
                for (Piso pis : listPiso) {

                    
                    if (!jefePiso.isEmpty() && pis.getJefepiso() != null) {
                        if (pis.getJefepiso().getCi().equals(jefePiso) && !pis.getJefepiso().equals(piso.getJefepiso())) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un piso con el jefe de piso", "Atención"));
                            return;
                        }
                    }

                    if (!jefeLimp.isEmpty() && pis.getJefelimp() != null) {
                        if (pis.getJefelimp().getCi().equals(jefeLimp) && !pis.getJefelimp().equals(piso.getJefelimp())) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un piso con el jefe de limpieza", "Atención"));
                            return;
                        }
                    }

                    if (!jefePiso.isEmpty() && !jefeLimp.isEmpty() && jefePiso.equals(jefeLimp)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de piso y el jefe de limpieza que desea insertar, son iguales", "Atención"));
                        return;
                    }

                    if (!jefePiso.isEmpty() && pis.getJefepiso() != null && pis.getJefelimp() != null) {
                        if (pis.getJefelimp().getCi().equals(jefePiso)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de piso está registrado como jefe de limpieza", "Atención"));
                            return;
                        }
                    }

                    if (!jefeLimp.isEmpty() && pis.getJefelimp() != null && pis.getJefepiso() != null) {
                        if (pis.getJefepiso().getCi().equals(jefeLimp)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El jefe de limpieza está registrado como jefe de piso", "Atención"));
                            return;
                        }
                    }

                }
                control.pisoJPA.edit(p);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El piso ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(pisoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningun cambio", "Atención"));
        }
    }

    /*public void delete(Piso pis) {
        try {
            control.pisoJPA.destroy(pis.getPisoPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El piso se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el piso", "Atención"));

        }

    }*/

    public String getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(String idPiso) {
        this.idPiso = idPiso;
    }

    public String getJefePiso() {
        return jefePiso;
    }

    public void setJefePiso(String jefePiso) {
        this.jefePiso = jefePiso;
    }

    public String getJefeLimp() {
        return jefeLimp;
    }

    public void setJefeLimp(String jefeLimp) {
        this.jefeLimp = jefeLimp;
    }

    public String getIdEdif() {
        return idEdif;
    }

    public void setIdEdif(String idEdif) {
        this.idEdif = idEdif;
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

    public Map<String, String> getMap_edif() {
        return map_edif;
    }

    public void setMap_edif(Map<String, String> map_edif) {
        this.map_edif = map_edif;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    public List<Becado> getListBec() {
        return listBec;
    }

    public void setListBec(List<Becado> listBec) {
        this.listBec = listBec;
    }

    public Map<String, String> getMap_bec() {
        return map_bec;
    }

    public void setMap_bec(Map<String, String> map_bec) {
        this.map_bec = map_bec;
    }

    public Map<String, String> getMap_piso() {
        return map_piso;
    }

    public void setMap_piso(Map<String, String> map_piso) {
        this.map_piso = map_piso;
    }

    public String getPisoEdif() {
        return pisoEdif;
    }

    public void setPisoEdif(String pisoEdif) {
        this.pisoEdif = pisoEdif;
    }

}
