
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Authorities;
import entities.AuthoritiesPK;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import entities.Users;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.event.RowEditEvent;

@Named(value = "usersBeans")
@ManagedBean
@SessionScoped
public class usersBeans implements Serializable {
    
    ArrayList<Users> Users_list = new ArrayList<>();
    ArrayList<Users> Users_list_delete = new ArrayList<>();
    ArrayList<Users> Filtered_Users = new ArrayList<>();
    
    Users user_selected = new Users();
    
    String username = "";
    String password = "";
    String password1 = "";
    boolean enable;
    String nombre = "";
    String email = "";
    
    public void clear_vars() {
        username = "";
        password = "";
        password1 = "";
        nombre = "";
        email = "";
        
    }

    public ArrayList<Users> getUsers_list() {
        return Users_list;
    }

    public void setUsers_list(ArrayList<Users> Users_list) {
        this.Users_list = Users_list;
    }

    public ArrayList<Users> getUsers_list_delete() {
        return Users_list_delete;
    }

    public void setUsers_list_delete(ArrayList<Users> Users_list_delete) {
        this.Users_list_delete = Users_list_delete;
    }

    public ArrayList<Users> getFiltered_Users() {
        return Filtered_Users;
    }

    public void setFiltered_Users(ArrayList<Users> Filtered_Users) {
        this.Filtered_Users = Filtered_Users;
    }

    public Users getUser_selected() {
        return user_selected;
    }

    public void setUser_selected(Users user_selected) {
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update_list() {
        Users_list = (ArrayList<Users>) control.usersJpa.findUsersEntities();
    }
    
    /*List<User> listUser = new ArrayList<>();
    
    User user;
    
    public void cargarList() {
        listUser = control.usersJpa.findUsersEntities();
    }
    
    public String active(User u) {
        String active = "";

        if (u.getEnable()) {
            active = "Activo";
        }
        if (!u.getEnable()) {
            active = "Inactivo";
        }

        return active;
    }
    
    public String nombreApell(User u) {

        String nombApell;

        return nombApell = u.getNombre();

    }
    */
    public void add() {
        if (username.equals("") || email.equals("") || password.equals("") || password1.equals("") || nombre.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, datos inválidos", "ERROR"));
            return;
        }
        if (!password.equals(password1)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, las contraseñas no coinciden", "ERROR"));
            return;
        }
        Users newUser = new Users(username, DigestUtils.md5Hex(password), true, nombre, email);
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
        for (Users ust : Users_list) {
            if (ust.getEnable()) {
                Users_list_delete.add(ust);
            }
        }
        if (!Users_list_delete.isEmpty()) {
            for (Users us1 : Users_list_delete) {
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
        Users us = (Users) event.getObject();

        if (!email.equals("") && !nombre.equals("") && !password.equals("")) {
            us.setEmail(email);
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
