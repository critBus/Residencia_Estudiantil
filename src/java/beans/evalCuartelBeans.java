/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuartel;
import entities.Becado;
import entities.Evalcuarteleria;
import entities.EvalcuarteleriaAspectevalcuartel;
import entities.EvalcuarteleriaAspectevalcuartelPK;
import entities.EvalcuarteleriaPK;
import entities.Trabajador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

@Named(value = "evalCuartelBeans")
@ManagedBean
@SessionScoped
public class evalCuartelBeans implements Serializable {

    Date fecha;
    String becadoCi;
    String inspecciona;
    int value;
    int maxvalue;
    int aspEvCuartelId;

    List<Evalcuarteleria> listEvalCuartel = new ArrayList<>();
    List<EvalcuarteleriaAspectevalcuartel> listAspEv_EvCuartel = new ArrayList<>();

    List<Becado> listBecados = new ArrayList<>();
    Map<String, String> map_bec = new HashMap<>();

    List<Trabajador> listTrab = new ArrayList<>();
    Map<String, String> map_trab = new HashMap<>();

    Evalcuarteleria evalCuartel;

    List<EvalcuarteleriaAspectevalcuartel> listAux = new ArrayList<>();

    List<AspectEvalcuartel> listAspEvalCuartel = new ArrayList<>();

    Map<String, Integer> map_aspEvalCuartel = new HashMap<>();
    Map<String, String> map_evalCuartel = new HashMap<>();
    Map<String, String> map_inspect = new HashMap<>();

    EvalcuarteleriaAspectevalcuartel aspEv_EvCuartel;

    public void cargarList() {
        listAspEv_EvCuartel = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartelEntities();
        listEvalCuartel = control.evalCuarteleriaJPA.findEvalcuarteleriaEntities();

        listBecados = control.becadoJPA.findBecadoEntities();
        listTrab = control.trabajadorJPA.findTrabajadorEntities();

        map_bec.clear();
        for (Becado t : listBecados) {
            if (t.getSegundonombre() == null) {
                map_bec.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_bec.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }

        map_trab.clear();
        String aux1;
        for (Trabajador t : listTrab) {
            if (t.getEnable()) {
                aux1 = t.getNombre() + " " + t.getApellidos();
                map_trab.put(aux1, t.getCi());
            }
        }
    }

    public int suma(Evalcuarteleria a) {
        EvalcuarteleriaAspectevalcuartelPK b = new EvalcuarteleriaAspectevalcuartelPK(a.getEvalcuarteleriaPK().getFecha(), a.getBecado().getCi());

        int suma = 0;
        listAspEv_EvCuartel = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartelEntities();

        for (int i = 0; i < listAspEv_EvCuartel.size(); i++) {
            if (listAspEv_EvCuartel.get(i).getEvalcuarteleriaAspectevalcuartelPK().getFecha().equals(b.getFecha())) {
                if (listAspEv_EvCuartel.get(i).getEvalcuarteleriaAspectevalcuartelPK().getBecadoci().equals(b.getBecadoci())) {
                    suma += listAspEv_EvCuartel.get(i).getValue();
                }
            }
        }
        return suma;

    }

    public void insert() {

        Becado bec = control.becadoJPA.findBecado(becadoCi);
        Trabajador traIsnpecciona = control.trabajadorJPA.findTrabajador(inspecciona);
        
        System.out.println("Fecha ..." +fecha);
        System.out.println("Aspecto ID ..." +becadoCi);
        System.out.println("Inspecciona ..." +inspecciona);

        if (fecha == null || becadoCi.isEmpty() || inspecciona.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {

            try {

                for (Evalcuarteleria evc : listEvalCuartel) {
                    if (fecha.equals(evc.getEvalcuarteleriaPK().getFecha()) && becadoCi.equals(evc.getEvalcuarteleriaPK().getBecadoci())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe la evaluación", "Atención"));
                        return;
                    }

                }

                EvalcuarteleriaPK e = new EvalcuarteleriaPK(fecha, becadoCi);
                Evalcuarteleria ec = new Evalcuarteleria(e);

                control.evalCuarteleriaJPA.create(new Evalcuarteleria(e, bec, traIsnpecciona));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido insertada", "Atención"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

            }
        }
    }

    public void edit() {
        boolean flag = false;
        int count = 0;

        Trabajador traInspecciona = control.trabajadorJPA.findTrabajador(inspecciona);

        Evalcuarteleria e = control.evalCuarteleriaJPA.findEvalcuarteleria(evalCuartel.getEvalcuarteleriaPK());

        if (!inspecciona.isEmpty() && !inspecciona.equals(evalCuartel.getInspeccionaci().getCi())) {
            e.setInspeccionaci(traInspecciona);
            flag = true;
        } else if (inspecciona.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'Inspecciona' está vacío", "Atención"));
            count++;
        }

        if (flag) {
            try {

                control.evalCuarteleriaJPA.edit(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido modificada", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(evalCuartelBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    public void delete(Evalcuarteleria eval) {
        try {

            eliminando_lista_evaluacion(eval.getEvalcuarteleriaPK());
            control.evalCuarteleriaJPA.destroy(eval.getEvalcuarteleriaPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación de la cuartelería", "Atención"));

        } catch (IllegalOrphanException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación del cuartelería", "Atención"));
        }
    }
    
    public String nombreApell(Trabajador trabajador) {

        String nombApell;

        return nombApell = trabajador.getNombre() + " " + trabajador.getApellidos();

    }
    
    public String nombreApellbeca(Becado becado) {

        String nombApellbecado;

        return nombApellbecado = becado.getNombre() + " " + becado.getSegundonombre() + " " + becado.getApellidos();

    }
    
    public String cargarList2() {

        listAspEvalCuartel = control.aspectevalCuartelJPA.findAspectEvalcuartelEntities();
        listEvalCuartel = control.evalCuarteleriaJPA.findEvalcuarteleriaEntities();

        map_bec.clear();
        for (Becado t : listBecados) {
            if (t.getSegundonombre() == null) {
                map_bec.put(t.getNombre() + " " + t.getApellidos(), t.getCi());
            }
            if (t.getSegundonombre() != null) {
                map_bec.put(t.getNombre() + " " + t.getSegundonombre() + " " + t.getApellidos(), t.getCi());
            }
        }

        map_aspEvalCuartel.clear();
        for (AspectEvalcuartel p : listAspEvalCuartel) {
            map_aspEvalCuartel.put(p.getName(), p.getId());
        }

        map_inspect.clear();
        String aux1;
        inspecciona = evalCuartel.getInspeccionaci().getCi();
        for (Evalcuarteleria p : listEvalCuartel) {
            aux1 = p.getInspeccionaci().getNombre() + " " + p.getInspeccionaci().getApellidos();
            map_inspect.put(aux1, p.getInspeccionaci().getCi());
        }

        listAspEv_EvCuartel = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartelEntities();

//actualizacion de los valores por defecto        
        fecha = evalCuartel.getEvalcuarteleriaPK().getFecha();
        becadoCi = evalCuartel.getEvalcuarteleriaPK().getBecadoci();

        //if (listAux.size()==0) {
        listAux = new ArrayList<>();
        eval_date_cuarteleria(new EvalcuarteleriaPK(evalCuartel.getEvalcuarteleriaPK().getFecha(), evalCuartel.getEvalcuarteleriaPK().getBecadoci()));

        completar_listaux();
        // }

        return "insert_evalcuartel";

    }

    public void eval_date_cuarteleria(EvalcuarteleriaPK apk) {

        for (EvalcuarteleriaAspectevalcuartel item : listAspEv_EvCuartel) {
            Date fechaaux = apk.getFecha();
            String becadoaux = apk.getBecadoci();

            if (fechaaux.equals(item.getEvalcuarteleriaAspectevalcuartelPK().getFecha())
                    && becadoaux.equals(item.getEvalcuarteleriaAspectevalcuartelPK().getBecadoci())) {
                listAux.add(item);
            }
        }
    }

    public void completar_listaux() {

        listAspEv_EvCuartel = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartelEntities();
        EvalcuarteleriaAspectevalcuartel nuevo;
        Evalcuarteleria ec = control.evalCuarteleriaJPA.findEvalcuarteleria(new EvalcuarteleriaPK(fecha, becadoCi));

        for (AspectEvalcuartel asp : listAspEvalCuartel) {

            if (aspectRepeat(asp.getId()) == false) {

                EvalcuarteleriaAspectevalcuartelPK evalPK = new EvalcuarteleriaAspectevalcuartelPK(fecha, asp.getId(), becadoCi);

                nuevo = new EvalcuarteleriaAspectevalcuartel(evalPK, 0);
                nuevo.setAspectEvalcuartel(asp);
                nuevo.setEvalcuarteleria(ec);

                listAux.add(nuevo);
            }

        }

    }

    public int sumaAux() {
        int suma = 0;

        for (int i = 0; i < listAux.size(); i++) {
            suma += listAux.get(i).getValue();

        }

        return suma;

    }

    public boolean aspectRepeat(int id) {

        for (EvalcuarteleriaAspectevalcuartel asp : listAux) {
            if (asp.getAspectEvalcuartel().getId() == id) {
                return true;
            }
        }

        return false;
    }

    public void editar() {

        AspectEvalcuartel c = control.aspectevalCuartelJPA.findAspectEvalcuartel(aspEvCuartelId);

        if (value > c.getMaxvalue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El valor del aspecto excede su valor máximo", "Atención"));
        } else {

            try {
                EvalcuarteleriaAspectevalcuartel nuevo = new EvalcuarteleriaAspectevalcuartel();
                int index = 0;

                for (int i = 0; i < listAux.size(); i++) {

                    if (listAux.get(i).getAspectEvalcuartel().getId() == aspEvCuartelId) {

                        nuevo = listAux.get(i);
                        index = i;
                        break;
                    }

                }

                nuevo.setValue(value);
                listAux.remove(index);
                listAux.add(nuevo);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido insertada", "Atención"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

            }
        }

    }

    public String insertAll() throws Exception {

        if (listAux.size() != listAspEvalCuartel.size()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha evaluado todos los aspectos", "Atención"));
        } else {

            try {

                eliminando_lista_evaluacion(new EvalcuarteleriaPK(fecha, becadoCi));

                for (EvalcuarteleriaAspectevalcuartel asp : listAux) {
                    control.aspEvCuartel_EvCuartelJPA.create(asp);

                }

                listAux.removeAll(listAux);
                return "evalCuartel";
            } catch (Exception e) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar", "Atención"));

                return "";
            }
        }
        return "";
    }

    public String navegar() {
        return "insertaspect";

    }

    public void eliminando_lista_evaluacion(EvalcuarteleriaPK apk) {

        List<EvalcuarteleriaAspectevalcuartel> listado = new ArrayList();

        listAspEv_EvCuartel = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartelEntities();

        Date fechaaux = apk.getFecha();
        String becadoaux = apk.getBecadoci();
        for (EvalcuarteleriaAspectevalcuartel item : listAspEv_EvCuartel) {

            if (fechaaux.equals(item.getEvalcuarteleriaAspectevalcuartelPK().getFecha())
                    && becadoaux.equals(item.getEvalcuarteleriaAspectevalcuartelPK().getBecadoci())) {
                listado.add(item);
            }
        }

        for (EvalcuarteleriaAspectevalcuartel item : listado) {
            EvalcuarteleriaAspectevalcuartel prob = control.aspEvCuartel_EvCuartelJPA.findEvalcuarteleriaAspectevalcuartel(item.getEvalcuarteleriaAspectevalcuartelPK());
            if (prob != null) {
                try {
                    control.aspEvCuartel_EvCuartelJPA.destroy(prob.getEvalcuarteleriaAspectevalcuartelPK());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(evalCuartBeans.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

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

    public String getInspecciona() {
        return inspecciona;
    }

    public void setInspecciona(String inspecciona) {
        this.inspecciona = inspecciona;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public int getAspEvCuartelId() {
        return aspEvCuartelId;
    }

    public void setAspEvCuartelId(int aspEvCuartelId) {
        this.aspEvCuartelId = aspEvCuartelId;
    }

    public List<Evalcuarteleria> getListEvalCuartel() {
        return listEvalCuartel;
    }

    public void setListEvalCuartel(List<Evalcuarteleria> listEvalCuartel) {
        this.listEvalCuartel = listEvalCuartel;
    }

    public List<EvalcuarteleriaAspectevalcuartel> getListAspEv_EvCuartel() {
        return listAspEv_EvCuartel;
    }

    public void setListAspEv_EvCuartel(List<EvalcuarteleriaAspectevalcuartel> listAspEv_EvCuartel) {
        this.listAspEv_EvCuartel = listAspEv_EvCuartel;
    }

    public List<Becado> getListBecados() {
        return listBecados;
    }

    public void setListBecados(List<Becado> listBecados) {
        this.listBecados = listBecados;
    }

    public Map<String, String> getMap_bec() {
        return map_bec;
    }

    public void setMap_bec(Map<String, String> map_bec) {
        this.map_bec = map_bec;
    }

    public List<Trabajador> getListTrab() {
        return listTrab;
    }

    public void setListTrab(List<Trabajador> listTrab) {
        this.listTrab = listTrab;
    }

    public Map<String, String> getMap_trab() {
        return map_trab;
    }

    public void setMap_trab(Map<String, String> map_trab) {
        this.map_trab = map_trab;
    }

    public Evalcuarteleria getEvalCuartel() {
        return evalCuartel;
    }

    public void setEvalCuartel(Evalcuarteleria evalCuartel) {
        this.evalCuartel = evalCuartel;
    }

    public List<EvalcuarteleriaAspectevalcuartel> getListAux() {
        return listAux;
    }

    public void setListAux(List<EvalcuarteleriaAspectevalcuartel> listAux) {
        this.listAux = listAux;
    }

    public List<AspectEvalcuartel> getListAspEvalCuartel() {
        return listAspEvalCuartel;
    }

    public void setListAspEvalCuartel(List<AspectEvalcuartel> listAspEvalCuartel) {
        this.listAspEvalCuartel = listAspEvalCuartel;
    }

    public Map<String, Integer> getMap_aspEvalCuartel() {
        return map_aspEvalCuartel;
    }

    public void setMap_aspEvalCuartel(Map<String, Integer> map_aspEvalCuartel) {
        this.map_aspEvalCuartel = map_aspEvalCuartel;
    }

    public Map<String, String> getMap_evalCuartel() {
        return map_evalCuartel;
    }

    public void setMap_evalCuartel(Map<String, String> map_evalCuartel) {
        this.map_evalCuartel = map_evalCuartel;
    }

    public Map<String, String> getMap_inspect() {
        return map_inspect;
    }

    public void setMap_inspect(Map<String, String> map_inspect) {
        this.map_inspect = map_inspect;
    }

    public EvalcuarteleriaAspectevalcuartel getAspEv_EvCuartel() {
        return aspEv_EvCuartel;
    }

    public void setAspEv_EvCuartel(EvalcuarteleriaAspectevalcuartel aspEv_EvCuartel) {
        this.aspEv_EvCuartel = aspEv_EvCuartel;
    }

    

}
