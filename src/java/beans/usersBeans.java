
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
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
@Named(value = "usersBeans")
@ManagedBean
@SessionScoped
public class usersBeans implements Serializable {
    
    public String passord_encrip(){
    return DigestUtils.md5Hex("123");
    }
    
    String username;
    String identificacion;
    String nombre;
    String email;
    String password;
    boolean enabled;
    //String descripcion;
    
    List<Users> listusers = new ArrayList<>();

    Users users;

    public void cargarList() {
        listusers = control.usersJpa.findUsersEntities();

    }
    
    public String active(Users users) {
        String active = "";

        if (users.getEnabled()) {
            active = "Activo";
        }
        if (!users.getEnabled()) {
            active = "Inactivo";
        }

        return active;
    }
    public String nombreApell(Users trabjad) {

        return trabjad.getNombre();
    }
    
    public void insert() {

        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || email.isEmpty() || identificacion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
        } else {
            try {
                for (Users m : listusers) {
                    
                    if (m.getUsername().equalsIgnoreCase(username)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe el usuario", "Atención"));
                        return;
                    }
                    if (m.getIdentificacion().equals(identificacion)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un usuario con la misma identificación", "Atención"));
                        return;
                    }
                    if (m.getPassword().equals(password)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un usuario con la misma contraseña", "Atención"));
                        return;
                    }
                    if (m.getEmail().equalsIgnoreCase(email)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un usuario con el mismo email", "Atención"));
                        return;
                    }
                }
                control.usersJpa.create(new Users(username, identificacion, nombre, email, DigestUtils.md5Hex(password), enabled));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario ha sido insertado", "Atención"));
            } catch (Exception e) {
                Logger.getLogger(medicoBeans.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    public void edit(){
        
        boolean flag = false;
        int count = 0;

        Users a = control.usersJpa.findUsers(username);
        
        if (!password.isEmpty() ) {//&& !password.equals(users.getPassword())
            a.setPassword(DigestUtils.md5Hex(password));
            flag = true;
        }
        
        if (!email.isEmpty() && !email.equals(users.getEmail())) {
            a.setEmail(email);
            flag = true;

        } else if (password.isEmpty() || email.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Existen campos vacíos", "Atención"));
            count++;
        }

        if (flag) {
            try {
                control.usersJpa.edit(a);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario ha sido modificado", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(usersBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }   
    }
    
    public void delete(Users u) {
        try {
            control.usersJpa.destroy(u.getUsername());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el usuario", "Atención"));

        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Users> getListusers() {
        return listusers;
    }

    public void setListusers(List<Users> listusers) {
        this.listusers = listusers;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
