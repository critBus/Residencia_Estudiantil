/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entities.AspectEvalcuarto;
import entities.AspectEvalcuartoEvalcuarto;
import entities.AspectEvalcuartoEvalcuartoPK;
import entities.Cuarto;
import entities.CuartoPK;
import entities.Edificio;
import entities.Evalcuarto;
import entities.EvalcuartoPK;
import entities.Piso;
import entities.PisoPK;
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

@Named(value = "evalCuartBeans")
@ManagedBean
@SessionScoped
public class evalCuartBeans implements Serializable{

    Date fecha;
    String cuartoId;
    String pisoId;
    String edificioId;
    String inspecciona;

    List<Evalcuarto> listEvalCuart = new ArrayList<>();
    List<AspectEvalcuartoEvalcuarto> listAspEv_EvCuart = new ArrayList<>();

    List<Cuarto> listCuart = new ArrayList<>();
    Map<String, String> map_cuart = new HashMap<>();

    List<Piso> listPiso = new ArrayList<>();
    Map<String, String> map_piso = new HashMap<>();

    List<Edificio> listEdif = new ArrayList<>();
    Map<String, String> map_edif = new HashMap<>();

    List<Trabajador> listTrab = new ArrayList<>();
    Map<String, String> map_trab = new HashMap<>();

    String ubicacion;
    Evalcuarto evalCuart;

    public void cargarList() {
        
        listAspEv_EvCuart = control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuartoEntities();
          
        listEvalCuart = control.evalCuartoJPA.findEvalcuartoEntities();
        listCuart = control.cuartoJPA.findCuartoEntities();
        listPiso = control.pisoJPA.findPisoEntities();
        listEdif = control.edificioJPA.findEdificioEntities();
        listTrab = control.trabajadorJPA.findTrabajadorEntities();

        map_cuart.clear();
        String aux;

        for (Cuarto p : listCuart) {
            aux = "Cuarto: " + p.getCuartoPK().getId() + ", Piso: " + p.getPiso().getPisoPK().getId() + ", Edificio: " + p.getPiso().getEdificio().getNombre();
            map_cuart.put(aux, p.getCuartoPK().getId() + " " + p.getCuartoPK().getPisoid() + " " + p.getCuartoPK().getEdificioid());

        }

        map_trab.clear();
        String aux1;
        for (Trabajador t : listTrab) {
            if (t.getEnable()){
            aux1 = t.getNombre() + " " + t.getApellidos();
            map_trab.put(aux1, t.getCi());
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

    public int suma(Evalcuarto a) {
        AspectEvalcuartoEvalcuartoPK b = new AspectEvalcuartoEvalcuartoPK(a.getEvalcuartoPK().getFecha(), a.getEvalcuartoPK().getCuartoid(), a.getEvalcuartoPK().getPisoid(), a.getEvalcuartoPK().getEdificioid());

        int suma = 0;
        listAspEv_EvCuart = control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuartoEntities();

        for (int i = 0; i < listAspEv_EvCuart.size(); i++) {
            if (listAspEv_EvCuart.get(i).getAspectEvalcuartoEvalcuartoPK().getFecha().equals(b.getFecha())) {
                if (listAspEv_EvCuart.get(i).getAspectEvalcuartoEvalcuartoPK().getCuartoid().equals(b.getCuartoid())) {
                    if (listAspEv_EvCuart.get(i).getAspectEvalcuartoEvalcuartoPK().getPisoid().equals(b.getPisoid())) {
                        if (listAspEv_EvCuart.get(i).getAspectEvalcuartoEvalcuartoPK().getEdificioid().equals(b.getEdificioid())) {
                            suma += listAspEv_EvCuart.get(i).getValue();
                        }
                    }
                }
            }
        }
        return suma;

    }

    public void insert() {
        

        Trabajador traIsnpecciona = control.trabajadorJPA.findTrabajador(inspecciona);

        if (ubicacion.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hay campos vacíos", "Atención"));
        } else {

            try {

                String[] codigos = cortar_cadena(ubicacion);

                for (Evalcuarto evc : listEvalCuart) {
                    if (fecha.equals(evc.getEvalcuartoPK().getFecha()) && codigos[0].equals(evc.getEvalcuartoPK().getCuartoid()) && codigos[1].equals(evc.getEvalcuartoPK().getPisoid()) && codigos[2].equals(evc.getEvalcuartoPK().getEdificioid())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe la evaluación", "Atención"));
                        return;
                    }

                }

                EvalcuartoPK e = new EvalcuartoPK(fecha, codigos[0], codigos[1], codigos[2]);

                Evalcuarto ec = new Evalcuarto(e);

                Edificio ed = control.edificioJPA.findEdificio(codigos[2]);

                PisoPK pPK = new PisoPK(codigos[1], codigos[2]);
                Piso p = control.pisoJPA.findPiso(pPK);

                CuartoPK hPK = new CuartoPK(codigos[0], codigos[1], codigos[2]);
                Cuarto h = control.cuartoJPA.findCuarto(hPK);

                control.evalCuartoJPA.create(new Evalcuarto(e, traIsnpecciona, h));
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
          Evalcuarto e = control.evalCuartoJPA.findEvalcuarto(evalCuart.getEvalcuartoPK());

        if (!inspecciona.isEmpty() && !inspecciona.equals(evalCuart.getTrabajadorci().getCi())) {
            e.setTrabajadorci(traInspecciona);
            flag = true;
        } else if (inspecciona.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El campo 'Inspecciona' está vacío", "Atención"));
            count++;
        }

        if (flag) {
            try {

                control.evalCuartoJPA.edit(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación ha sido modificada", "Atención"));
            } catch (Exception ex) {
                Logger.getLogger(evalCuartBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (count == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha realizado ningún cambio", "Atención"));
        }

    }

    public void delete(Evalcuarto eval) {
        try {
            
            eliminando_lista_evaluacion(eval.getEvalcuartoPK());
            control.evalCuartoJPA.destroy(eval.getEvalcuartoPK());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La evaluación se ha eliminado", "Atención"));

        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación del cuarto", "Atención"));

        } catch (IllegalOrphanException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar la evaluación del cuarto", "Atención"));
        }
    }

    public String dateFormat(Date fecha) {
        return fecha.getDate() + " / " + (fecha.getMonth() + 1) + " / " + (fecha.getYear() + 1900);

    }
    
    public String nombreApell(Trabajador trabajador) {

        String nombApell;

        return nombApell = trabajador.getNombre() + " " + trabajador.getApellidos();

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getInspecciona() {
        return inspecciona;
    }

    public void setInspecciona(String inspecciona) {
        this.inspecciona = inspecciona;
    }

    public List<Evalcuarto> getListEvalCuart() {
        return listEvalCuart;
    }

    public void setListEvalCuart(List<Evalcuarto> listEvalCuart) {
        this.listEvalCuart = listEvalCuart;
    }

    public List<Cuarto> getListCuart() {
        return listCuart;
    }

    public void setListCuart(List<Cuarto> listCuart) {
        this.listCuart = listCuart;
    }

    public Map<String, String> getMap_cuart() {
        return map_cuart;
    }

    public void setMap_cuart(Map<String, String> map_cuart) {
        this.map_cuart = map_cuart;
    }

    public List<Piso> getListPiso() {
        return listPiso;
    }

    public void setListPiso(List<Piso> listPiso) {
        this.listPiso = listPiso;
    }

    public Map<String, String> getMap_piso() {
        return map_piso;
    }

    public void setMap_piso(Map<String, String> map_piso) {
        this.map_piso = map_piso;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Evalcuarto getEvalCuart() {
        return evalCuart;
    }

    public void setEvalCuart(Evalcuarto evalCuart) {
        this.evalCuart = evalCuart;
    }
    
     int cf;
    
    int value;
    int maxvalue;
int aspEvCuartId;


    List<AspectEvalcuartoEvalcuarto> listAux = new ArrayList<>();

    List<AspectEvalcuarto> listAspEvalCuart = new ArrayList<>();
    

    Map<String, Integer> map_aspEvalCuart = new HashMap<>();
    Map<String, String> map_evalCuart = new HashMap<>();
    Map<String, String> map_inspect = new HashMap<>();

    AspectEvalcuartoEvalcuarto aspEv_EvCuarto;
    

    public String cargarList2() {
        
      
        listAspEvalCuart = control.aspectevalCuartoJPA.findAspectEvalcuartoEntities();
        listEvalCuart = control.evalCuartoJPA.findEvalcuartoEntities();

        map_evalCuart.clear();
        String aux;
        
        ubicacion=evalCuart.getEvalcuartoPK().getCuartoid() + " " + evalCuart.getEvalcuartoPK().getPisoid() + " " + evalCuart.getEvalcuartoPK().getEdificioid();

        for (Evalcuarto p : listEvalCuart) {
            aux = "Cuarto: " + p.getEvalcuartoPK().getCuartoid() + ", Piso: " + p.getEvalcuartoPK().getPisoid() + ", Edificio: " + p.getCuarto().getPiso().getEdificio().getNombre();
            map_evalCuart.put(aux, p.getEvalcuartoPK().getCuartoid() + " " + p.getEvalcuartoPK().getPisoid() + " " + p.getEvalcuartoPK().getEdificioid());
        }

        map_aspEvalCuart.clear();
        for (AspectEvalcuarto p : listAspEvalCuart) {
            map_aspEvalCuart.put(p.getName(), p.getId());
        }

        map_inspect.clear();
        String aux1;
        inspecciona=evalCuart.getTrabajadorci().getCi();
        for (Evalcuarto p : listEvalCuart) {
            
            aux1 = p.getTrabajadorci().getNombre() + " " + p.getTrabajadorci().getApellidos();
            map_inspect.put(aux1, p.getTrabajadorci().getCi());
        }
        
        
        listAspEv_EvCuart = control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuartoEntities();
         
        
//actualizacion de los valores por defecto        
        fecha=evalCuart.getEvalcuartoPK().getFecha();
        pisoId=evalCuart.getEvalcuartoPK().getPisoid();
        cuartoId=evalCuart.getEvalcuartoPK().getCuartoid();
        edificioId=evalCuart.getEvalcuartoPK().getEdificioid();
         
         
      //if (listAux.size()==0) {
     listAux=new ArrayList<>();
     eval_date_cuarto(new EvalcuartoPK(evalCuart.getEvalcuartoPK().getFecha(), evalCuart.getEvalcuartoPK().getCuartoid(), evalCuart.getEvalcuartoPK().getPisoid(), evalCuart.getEvalcuartoPK().getEdificioid()));
       
        completar_listaux();
    // }

       return "evalinsert";
       
    }
    
    
    public void eval_date_cuarto(EvalcuartoPK apk) {
        
        for (AspectEvalcuartoEvalcuarto item :listAspEv_EvCuart) {
            Date fechaaux = apk.getFecha();
            String cuartoaux = apk.getCuartoid();
            String pisoaux = apk.getPisoid();
            String edificioaux = apk.getEdificioid();
            
            if (fechaaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getFecha()) 
                    && cuartoaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getCuartoid()) 
                    && pisoaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getPisoid()) 
                    && edificioaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getEdificioid()) 
                    ) {
              
                
                listAux.add(item);
              
                
            }
        }

    }
    
    public void completar_listaux(){
        
    listAspEv_EvCuart = control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuartoEntities();
    AspectEvalcuartoEvalcuarto nuevo;
     Evalcuarto ec = control.evalCuartoJPA.findEvalcuarto(new EvalcuartoPK(fecha, cuartoId, pisoId, edificioId));
          
        for (AspectEvalcuarto asp:listAspEvalCuart) {
           
            if (aspectRepeat(asp.getId())==false) {
              
                AspectEvalcuartoEvalcuartoPK evalPK = new AspectEvalcuartoEvalcuartoPK(asp.getId(), fecha, cuartoId, pisoId, edificioId);

            nuevo=new AspectEvalcuartoEvalcuarto(evalPK, 0);
            nuevo.setAspectEvalcuarto(asp);
            nuevo.setEvalcuarto(ec);
              
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

        for (AspectEvalcuartoEvalcuarto asp : listAux) {
            if (asp.getAspectEvalcuarto().getId() == id) {
                return true;
            }
        }

        return false;
    }

    public void editar() {

        AspectEvalcuarto c = control.aspectevalCuartoJPA.findAspectEvalcuarto(aspEvCuartId);
      
        if (value > c.getMaxvalue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El valor del aspecto excede su valor máximo", "Atención"));
        } else {

            try {
              AspectEvalcuartoEvalcuarto nuevo = new AspectEvalcuartoEvalcuarto();
              int index=0;

                for(int i=0; i<listAux.size();i++){    
                    
                    if (listAux.get(i).getAspectEvalcuarto().getId()==aspEvCuartId) {
                       
                      nuevo= listAux.get(i);
                      index=i;
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

        if (listAux.size() != listAspEvalCuart.size()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha evaluado todos los aspectos", "Atención"));
        } else {

            try {
            
                
               eliminando_lista_evaluacion(new EvalcuartoPK(fecha, cuartoId, pisoId, edificioId));
                
                for (AspectEvalcuartoEvalcuarto asp : listAux) {
                    control.aspEv_EvCuartoJPA.create(asp);
                    
                }
                
               listAux.removeAll(listAux);
                return "evalcuarto";
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
    
      public void eliminando_lista_evaluacion(EvalcuartoPK apk) {
      
        
        List<AspectEvalcuartoEvalcuarto> listado=new ArrayList();
 
        listAspEv_EvCuart=control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuartoEntities();
        
         Date fechaaux = apk.getFecha();
            String cuartoaux = apk.getCuartoid();
            String pisoaux = apk.getPisoid();
            String edificioaux = apk.getEdificioid();
        for (AspectEvalcuartoEvalcuarto item :listAspEv_EvCuart) {
           

            if (fechaaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getFecha()) 
                    && cuartoaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getCuartoid()) 
                    && pisoaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getPisoid()) 
                    && edificioaux.equals(item.getAspectEvalcuartoEvalcuartoPK().getEdificioid()) 
                    ) {
              
                
                listado.add(item);
                
            }
        }
        
          for (AspectEvalcuartoEvalcuarto item :listado) {
            AspectEvalcuartoEvalcuarto prob=  control.aspEv_EvCuartoJPA.findAspectEvalcuartoEvalcuarto(item.getAspectEvalcuartoEvalcuartoPK());
              if (prob!=null) {
                 try {
                control.aspEv_EvCuartoJPA.destroy(prob.getAspectEvalcuartoEvalcuartoPK());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(evalCuartBeans.class.getName()).log(Level.SEVERE, null, ex);
            }  
              }
           
          }

    }

    public List<AspectEvalcuartoEvalcuarto> getListAspEv_EvCuart() {
        return listAspEv_EvCuart;
    }

    public void setListAspEv_EvCuart(List<AspectEvalcuartoEvalcuarto> listAspEv_EvCuart) {
        this.listAspEv_EvCuart = listAspEv_EvCuart;
    }

    public int getCf() {
        return cf;
    }

    public void setCf(int cf) {
        this.cf = cf;
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

    public int getAspEvCuartId() {
        return aspEvCuartId;
    }

    public void setAspEvCuartId(int aspEvCuartId) {
        this.aspEvCuartId = aspEvCuartId;
    }

    public List<AspectEvalcuartoEvalcuarto> getListAux() {
        return listAux;
    }

    public void setListAux(List<AspectEvalcuartoEvalcuarto> listAux) {
        this.listAux = listAux;
    }

    public List<AspectEvalcuarto> getListAspEvalCuart() {
        return listAspEvalCuart;
    }

    public void setListAspEvalCuart(List<AspectEvalcuarto> listAspEvalCuart) {
        this.listAspEvalCuart = listAspEvalCuart;
    }

    public Map<String, Integer> getMap_aspEvalCuart() {
        return map_aspEvalCuart;
    }

    public void setMap_aspEvalCuart(Map<String, Integer> map_aspEvalCuart) {
        this.map_aspEvalCuart = map_aspEvalCuart;
    }

    public Map<String, String> getMap_evalCuart() {
        return map_evalCuart;
    }

    public void setMap_evalCuart(Map<String, String> map_evalCuart) {
        this.map_evalCuart = map_evalCuart;
    }

    public Map<String, String> getMap_inspect() {
        return map_inspect;
    }

    public void setMap_inspect(Map<String, String> map_inspect) {
        this.map_inspect = map_inspect;
    }

    public AspectEvalcuartoEvalcuarto getAspEv_EvCuarto() {
        return aspEv_EvCuarto;
    }

    public void setAspEv_EvCuarto(AspectEvalcuartoEvalcuarto aspEv_EvCuarto) {
        this.aspEv_EvCuarto = aspEv_EvCuarto;
    }

}
