
package beans;

import entities.Authorities;
import entities.AuthoritiesPK;
import entities.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

@Named(value = "authoritiesBeans")
@ManagedBean
@SessionScoped
public class authoritiesBeans implements Serializable{
    
    ArrayList<Authorities> Authorities_list = new ArrayList<>();
    ArrayList<Users> Users_list = new ArrayList<>();
    ArrayList<Authorities> Authorities_list_delete = new ArrayList<>();
    ArrayList<Authorities> Filtered_roles;
    private Map<String, String> list_users = new HashMap<>();
    private Map<String, String> list_authorities = new HashMap<>();
    
    public void update_users_autorities() {
        Users_list = (ArrayList<Users>) control.usersJpa.findUsersEntities();
//        Clasificadores_list = (ArrayList<Clasificador>) Controllers.clasificadorJpaController.findClasificadorEntities();
        list_authorities.put("ROLE_ADMIN", "ROLE_ADMIN");
        list_authorities.put("ROLE_USER", "ROLE_USER");
//        for (int i = 0; i < Clasificadores_list.size(); i++) {
//            if(Clasificadores_list.get(i).getIdClasificador()==0) //saltando el NO CLASIFICADO
//                continue;
//            String clas = "ROLE_"+Clasificadores_list.get(i).getClasificador();
//            clas=clas.toUpperCase();
//            list_authorities.put(clas, clas);
//        }
        for (int i = 0; i < Users_list.size(); i++) {
            list_users.put(Users_list.get(i).getUsername(), Users_list.get(i).getUsername());
        }
    }

    public ArrayList<Authorities> getAuthorities_list() {
        return Authorities_list;
    }

    public void setAuthorities_list(ArrayList<Authorities> Authorities_list) {
        this.Authorities_list = Authorities_list;
    }

    public ArrayList<Users> getUsers_list() {
        return Users_list;
    }

    public void setUsers_list(ArrayList<Users> Users_list) {
        this.Users_list = Users_list;
    }

    public ArrayList<Authorities> getAuthorities_list_delete() {
        return Authorities_list_delete;
    }

    public void setAuthorities_list_delete(ArrayList<Authorities> Authorities_list_delete) {
        this.Authorities_list_delete = Authorities_list_delete;
    }

    public ArrayList<Authorities> getFiltered_roles() {
        return Filtered_roles;
    }

    public void setFiltered_roles(ArrayList<Authorities> Filtered_roles) {
        this.Filtered_roles = Filtered_roles;
    }

    public Map<String, String> getList_users() {
        return list_users;
    }

    public void setList_users(Map<String, String> list_users) {
        this.list_users = list_users;
    }

    public Map<String, String> getList_authorities() {
        return list_authorities;
    }

    public void setList_authorities(Map<String, String> list_authorities) {
        this.list_authorities = list_authorities;
    }
    
    String user = "";
    String authority_label = "";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthority_label() {
        return authority_label;
    }

    public void setAuthority_label(String authority_label) {
        this.authority_label = authority_label;
    }
    
    //updagrade de la lista de elementos desde la base de datos
    public void update_List() {
        Authorities_list = (ArrayList<Authorities>) control.authoritiesJpa.findAuthoritiesEntities();
    }
    
    //eliminar los elementos seleccionados en la base de datos
    //hay q revisar la posibilidad de que el elementos no pueda ser eliminado porque tenga alguna dependencia de llave foranea
    public void eliminar() {
        for (Authorities auth : Authorities_list) {
            if (auth.equals("")) {
                Authorities_list_delete.add(auth);
            }
        }
        if (!Authorities_list_delete.isEmpty()) {
            for (Authorities auth1 : Authorities_list_delete) {
                try {
                    control.authoritiesJpa.destroy(auth1.getAuthoritiesPK());
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no ha saido posible eliminar el elemento", "Eliminado"));
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Eliminado"));
            Authorities_list_delete.clear();
            update_List();
        }
    }
    
    //añadir un nuevo rol a un usuario ya existente, de no existir o encontrarse duplicado el rol se devolvera error
    //debe tenerse en cuenta que se permite a un mismo usuario poseer varios roles.
    public void añadir() {
        if (authority_label.equals("") || user.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, debe introducir correctamente los datos", "Eliminado"));
            return;
        }
        AuthoritiesPK auth_id = new AuthoritiesPK(user, authority_label);
        if (control.usersJpa.findUsers(user) == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, el usuario no existe", "Eliminado"));
            return;
        }
        if (control.authoritiesJpa.findAuthorities(auth_id) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, ya existe un usuario con dichas características", "Eliminado"));
            return;
        }
        Authorities auth = new Authorities(auth_id, control.usersJpa.findUsers(user));

        try {
            control.authoritiesJpa.create(auth);
        } catch (Exception ex) {
            Logger.getLogger(authoritiesBeans.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento añadido correctamente", "Aviso"));
        update_List();

    }

    public void cancelar(RowEditEvent event) {
    }
}
