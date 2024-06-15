
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Articuloreglam;
import entities.Authorities;
import entities.AuthoritiesPK;
import entities.Capituloreglam;
import entities.Users;
import java.io.Serializable;
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

@Named(value = "authoritiesBeans")
@ManagedBean
@SessionScoped
public class authoritiesBeans implements Serializable{
    
    String username;
    String authority;
    
    List<Authorities> listauthori = new ArrayList<>();
    List<Users> listuser = new ArrayList<>();
    
    Map<String, String> map_usuario = new HashMap<>();
    Map<String, String> map_authoriti = new HashMap<>();
    
    Authorities authorities;
    
    public void cargarList() {
        
        listuser = control.usersJpa.findUsersEntities();
        listauthori = control.authoritiesJpa.findAuthoritiesEntities();
        
        map_usuario.clear();
        for (Users c : listuser) {
            map_usuario.put(c.getUsername(), c.getUsername());
        }
        
        map_authoriti.put("ROLE_ADMIN", "ROLE_ADMIN");
        map_authoriti.put("ROLE_ESPECIALISTA", "ROLE_ESPECIALISTA");
        map_authoriti.put("ROLE_JEFE_RESIDENCIA", "ROLE_JEFE_RESIDENCIA");
        map_authoriti.put("ROLE_MEDICO", "ROLE_MEDICO");

    }
    
    public void insert() {
            
    Users users = control.usersJpa.findUsers(username);
    
        if (username.isEmpty() || authority.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Authorities ar : listauthori) {
                    if (ar.getAuthoritiesPK().getAuthority().equals(authority) && ar.getAuthoritiesPK().getUsername().equals(username)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un usuario con mismo rol", "Atención"));
                        return;
                    }
                }
                
                AuthoritiesPK authoritiesPK = new AuthoritiesPK(username, authority);
                
                control.authoritiesJpa.create(new Authorities(authoritiesPK, users));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El rol ha sido insertado", "Atención"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }
    
    public void delete(Authorities areglam){
        try {
            control.authoritiesJpa.destroy(areglam.getAuthoritiesPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El rol se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el rol", "Atención"));

        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<Authorities> getListauthori() {
        return listauthori;
    }

    public void setListauthori(List<Authorities> listauthori) {
        this.listauthori = listauthori;
    }

    public List<Users> getListuser() {
        return listuser;
    }

    public void setListuser(List<Users> listuser) {
        this.listuser = listuser;
    }

    public Map<String, String> getMap_usuario() {
        return map_usuario;
    }

    public void setMap_usuario(Map<String, String> map_usuario) {
        this.map_usuario = map_usuario;
    }

    public Map<String, String> getMap_authoriti() {
        return map_authoriti;
    }

    public void setMap_authoriti(Map<String, String> map_authoriti) {
        this.map_authoriti = map_authoriti;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }

}
