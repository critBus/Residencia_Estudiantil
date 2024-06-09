package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.Cuarto;
import entities.CuartoPK;
import entities.Piso;
import entities.PisoPK;
import entities.Enfermedades;
import entities.Medicamentos;
import entities.Trabajoprod;
import entities.Edificio;
import entities.Becado;
import entities.Pacientesatendidos;
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
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

@Named(value = "becadoBeans")
@ManagedBean
@SessionScoped
public class becadoBeans implements Serializable {

    String ci;
    String codigo;
    String nombre;
    String segundoNombre;
    String apellidos;
    boolean activo;
    boolean aptoEsfuerzoFisico;
    boolean fumar;
    boolean beber;
    String nucleoFamiliar;
    String carrera;
    int anno = 0;
    boolean sexo;
    String telefono;
    String facultad;
    String cuartoId;
    String pisoId;
    String edificioId;

    List<Becado> listbecad = new ArrayList<>();

    List<Cuarto> listCuart = new ArrayList<>();
    Map<String, String> map_cuartos = new HashMap<>();

    List<Piso> listPiso = new ArrayList<>();
    Map<String, String> map_pisos = new HashMap<>();

    List<Edificio> listEdificio = new ArrayList<>();
    Map<String, String> map_edificios = new HashMap<>();

    Becado becado;
    String ubicacion;

    public void cargarList() {

        listbecad = control.becadoJPA.findBecadoEntities();

        listCuart = control.cuartoJPA.findCuartoEntities();
        listPiso = control.pisoJPA.findPisoEntities();
        listEdificio = control.edificioJPA.findEdificioEntities();

        map_cuartos.clear();
        String aux;
        for (Cuarto c : listCuart) {
            if (c.getCuartoPK().getId() != null) {

                map_cuartos.put(cuarto_str(c), c.getCuartoPK().getId());
            }
        }

        map_pisos.clear();
        String aux1;
        for (Piso p : listPiso) {
            if (p.getPisoPK().getId() != null) {
                aux1 = p.getPisoPK().getId();
                map_pisos.put(aux1, p.getPisoPK().getId());
            }
        }

        map_edificios.clear();
        String aux2;
        for (Edificio e : listEdificio) {
            if (e.getId() != null) {
                aux2 = e.getId() + " : " + e.getNombre();
                map_edificios.put(aux2, e.getId());
            }
        }
    }

    public String[] cortar_cadena(String cadena) {
        String[] completo = cadena.split(" ");

        String[] respuesta = new String[3];

        respuesta[0] = completo[0];
        respuesta[1] = completo[1];
        respuesta[2] = completo[2];

        return respuesta;
    }

    public void insert() {

        if (ci.isEmpty() || codigo.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()
                || carrera.isEmpty() || anno == 0
                || facultad.isEmpty() //|| edificioId.isEmpty() || pisoId.isEmpty() 

                || cuartoId.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

//                String[] codigos = cortar_cadena(ubicacion);
                for (Becado be : listbecad) {

                    if (ci.equals(be.getCi())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe el Becado", "Atención"));
                        return;
                    }

                }
                Cuarto cuartoSeleccionado = null;
                for (Cuarto c : listCuart) {
                    if (c.getCuartoPK().getId().toString().equals(cuartoId)) {
                        cuartoSeleccionado = c;
                        break;
                    }
                }
                Becado becado_a_agregar = new Becado(ci, codigo, nombre, apellidos, activo, aptoEsfuerzoFisico, fumar, beber, nucleoFamiliar, carrera, anno, sexo, telefono, facultad, cuartoSeleccionado);
                if (segundoNombre != null) {
                    becado_a_agregar.setSegundonombre(segundoNombre);
                }
                control.becadoJPA.create(becado_a_agregar);
                resetear_campos();
                PrimeFaces.current().ajax().update("agregarform");
                hideDialog("dlg1");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El becario ha sido insertado", "Atención"));
            } catch (Exception e) {
                Logger.getLogger(edificioBeans.class.getName()).log(Level.WARNING, null, e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));
            }
        }
    }

    public void resetear_campos() {
        ci = null;
        codigo = null;
        nombre = null;
        segundoNombre = null;
        apellidos = null;
        activo = false;
        aptoEsfuerzoFisico = false;
        fumar = false;
        beber = false;
        nucleoFamiliar = null;
        carrera = null;
        anno = 2024;
        sexo = false;
        telefono = null;
        facultad = null;
        cuartoId = null;
        pisoId = null;
        edificioId = null;
    }

    public void actualizar_entidad() {
        actualizar_entidad(becado);
    }

    public void actualizar_entidad(Becado becado) {
        Cuarto cuartoSeleccionado = null;
        for (Cuarto c : listCuart) {
            if (c.getCuartoPK().getId().toString().equals(cuartoId)) {
                cuartoSeleccionado = c;
                break;
            }
        }
        becado.setActivo(activo);
        becado.setAnno(anno);
        becado.setApellidos(apellidos);
        becado.setAptoesfuerzofisico(aptoEsfuerzoFisico);
        becado.setBeber(beber);
        becado.setCarrera(carrera);
        becado.setCodigo(codigo);
        becado.setCuarto(cuartoSeleccionado);
        becado.setFacultad(facultad);
        becado.setFumar(fumar);
        becado.setNombre(nombre);
        becado.setNucleofamiliar(nucleoFamiliar);
        becado.setSegundonombre(segundoNombre);
        becado.setSexo(sexo);
        becado.setTelefono(telefono);
    }

    public void edit() {

        if (codigo.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()
                || carrera.isEmpty() || anno == 0
                || facultad.isEmpty()
                || cuartoId.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {
            try {

                actualizar_entidad();

                control.becadoJPA.edit(becado);

                for (Becado be : listbecad) {

                    if (ci.equals(be.getCi())) {
                        actualizar_entidad(be);
                        break;
                    }

                }
                hideDialog("dlg2");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El becado ha sido modificado", "Atención"));
            } catch (Exception e) {
                Logger.getLogger(edificioBeans.class.getName()).log(Level.WARNING, null, e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar", "Atención"));
            }
        }
    }

    public void delete(Becado pac) {
        try {
            control.becadoJPA.destroy(pac.getCi());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El Becado se ha eliminado", "Atención"));

        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el Becado", "Atención"));

        }
    }

    public String nombre_y_apellidos(Becado becado) {
        return becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();
    }

    public String cuarto_str_de_becado(Becado becado) {
        Cuarto cuarto = becado.getCuarto();

        return cuarto != null ? cuarto_str(cuarto) : "- sin cuarto -";
    }

    public String cuarto_str(Cuarto c) {
        Piso piso = null;
        Edificio edificio = null;
        piso = c.getPiso();
        if (piso != null) {
            edificio = piso.getEdificio();
        }
        String nombre_piso = piso != null ? piso.getPisoPK().getId() : "sin piso";
        String edificio_nombre = edificio != null ? edificio.getNombre() : "sin edificio";

        return edificio_nombre + " - piso:" + nombre_piso + " - cuarto:" + c.getCuartoPK().getId();
    }

    public static void showDialog(String id) {
        execute("PF('" + id + "').show();");
    }

    public static void hideDialog(String id) {
        execute("PF('" + id + "').hide();");
    }

    public static void execute(String execute) {
        PrimeRequestContext.getCurrentInstance().getScriptsToExecute().add(execute);
    }

    public String sexo_str(Becado becado) {
        return becado.getSexo() ? "Masculino" : "Femenino";
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isAptoEsfuerzoFisico() {
        return aptoEsfuerzoFisico;
    }

    public void setAptoEsfuerzoFisico(boolean aptoEsfuerzoFisico) {
        this.aptoEsfuerzoFisico = aptoEsfuerzoFisico;
    }

    public boolean isFumar() {
        return fumar;
    }

    public void setFumar(boolean fumar) {
        this.fumar = fumar;
    }

    public boolean isBeber() {
        return beber;
    }

    public void setBeber(boolean beber) {
        this.beber = beber;
    }

    public String getNucleoFamiliar() {
        return nucleoFamiliar;
    }

    public void setNucleoFamiliar(String nucleoFamiliar) {
        this.nucleoFamiliar = nucleoFamiliar;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCuartoId() {
        return cuartoId;
    }

    public void setCuartoId(String cuartoId) {
        this.cuartoId = cuartoId;
    }

    public String getPisoId() {
        return pisoId;
    }

    public void setPisoId(String pisoId) {
        this.pisoId = pisoId;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public List<Becado> getListbecad() {
        return listbecad;
    }

    public void setListbecad(List<Becado> listbecad) {
        this.listbecad = listbecad;
    }

    public List<Cuarto> getListCuart() {
        return listCuart;
    }

    public void setListCuart(List<Cuarto> listCuart) {
        this.listCuart = listCuart;
    }

    public Map<String, String> getMap_cuartos() {
        return map_cuartos;
    }

    public void setMap_cuartos(Map<String, String> map_cuartos) {
        this.map_cuartos = map_cuartos;
    }

    public List<Piso> getListPiso() {
        return listPiso;
    }

    public void setListPiso(List<Piso> listPiso) {
        this.listPiso = listPiso;
    }

    public Map<String, String> getMap_pisos() {
        return map_pisos;
    }

    public void setMap_pisos(Map<String, String> map_pisos) {
        this.map_pisos = map_pisos;
    }

    public List<Edificio> getListEdificio() {
        return listEdificio;
    }

    public void setListEdificio(List<Edificio> listEdificio) {
        this.listEdificio = listEdificio;
    }

    public Map<String, String> getMap_edificios() {
        return map_edificios;
    }

    public void setMap_edificios(Map<String, String> map_edificios) {
        this.map_edificios = map_edificios;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
