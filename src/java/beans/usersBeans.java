
package beans;

import entities.User;
import entities.Authorities;
import entities.AuthoritiesPK;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
//import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.event.RowEditEvent;
import beans.DigestUtils;

@Named(value = "usersBeans")
@ManagedBean
@SessionScoped
public class usersBeans implements Serializable {
    
    
   ArrayList<User> Users_list = new ArrayList<>();
    ArrayList<User> Users_list_delete = new ArrayList<>();
    ArrayList<User> Filtered_Users = new ArrayList<>();
    
    User user_selected = new User();
    
    String username = "";
    String password = "";
    String password1 = "";
    String ci = "";
    String nombre = "";
    boolean enable;
    
    public void clear_vars() {
        username = "";
        password = "";
        password1 = "";
        ci = "";
        nombre = "";
    }
    
     public usersBeans() {
    }

    public ArrayList<User> getUsers_list() {
        return Users_list;
    }

    public void setUsers_list(ArrayList<User> Users_list) {
        this.Users_list = Users_list;
    }

    public ArrayList<User> getUsers_list_delete() {
        return Users_list_delete;
    }

    public void setUsers_list_delete(ArrayList<User> Users_list_delete) {
        this.Users_list_delete = Users_list_delete;
    }

    public ArrayList<User> getFiltered_Users() {
        return Filtered_Users;
    }

    public void setFiltered_Users(ArrayList<User> Filtered_Users) {
        this.Filtered_Users = Filtered_Users;
    }

    public User getUser_selected() {
        return user_selected;
    }

    public void setUser_selected(User user_selected) {
        this.user_selected = user_selected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
   public void update_list() {
        Users_list = (ArrayList<User>) control.usersJpa.findUsersEntities();
    }
   
    public void add() {
        if (username.equals("") || ci.equals("") || password.equals("") || password1.equals("") || nombre.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, datos inválidos", "ERROR"));
            return;
        }
        if (!password.equals(password1)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, las contraseñas no coinciden", "ERROR"));
            return;
        }
        User newUser = new User(username, DigestUtils.md5Hex(password),true , nombre, ci);
        AuthoritiesPK authid = new AuthoritiesPK(username, "ROLE_USER");
        Authorities auth = new Authorities(authid, newUser);
        try {
            control.usersJpa.create(newUser);
            control.authoritiesJpa.create(auth);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, el elemento ya existe", "ERROR"));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento añadido", "Aviso"));
        update_list();
    }
    
     public void modificar() {
    }
     
     public void eliminar() {

        boolean f = false;
        for (User ust : Users_list) {
            if (ust.isIsSelected()) {
                Users_list_delete.add(ust);
            }
        }
        if (!Users_list_delete.isEmpty()) {
            for (User us1 : Users_list_delete) {
                try {
                    control.usersJpa.destroy(us1.getUsername());
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no es posible eliminar el elemento", "ERROR"));
                    f = true;
                }
            }
            if (f) {
                return;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Aviso"));
            Users_list_delete.clear();
            update_list();
        }
    }
     
     public void actualizar(RowEditEvent event) {
        User us = (User) event.getObject();

        if (!ci.equals("") && !nombre.equals("") && !password.equals("")) {
            us.setCi(ci);
            us.setNombre(nombre);
            us.setEnable(enable);
            us.setPassword(DigestUtils.md5Hex(password));
            try {
                control.usersJpa.edit(us);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no es posible modificar el elemento", "ERROR"));
                return;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento modificado", "Aviso"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, datos inválidos", "ERROR"));
        }
    }
    public void cancelar() {
    }
}
