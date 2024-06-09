/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "becado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Becado.findAll", query = "SELECT b FROM Becado b")
    , @NamedQuery(name = "Becado.findByCi", query = "SELECT b FROM Becado b WHERE b.ci = :ci")
    , @NamedQuery(name = "Becado.findByCodigo", query = "SELECT b FROM Becado b WHERE b.codigo = :codigo")
    , @NamedQuery(name = "Becado.findByNombre", query = "SELECT b FROM Becado b WHERE b.nombre = :nombre")
    , @NamedQuery(name = "Becado.findBySegundonombre", query = "SELECT b FROM Becado b WHERE b.segundonombre = :segundonombre")
    , @NamedQuery(name = "Becado.findByApellidos", query = "SELECT b FROM Becado b WHERE b.apellidos = :apellidos")
    , @NamedQuery(name = "Becado.findByActivo", query = "SELECT b FROM Becado b WHERE b.activo = :activo")
    , @NamedQuery(name = "Becado.findByAptoesfuerzofisico", query = "SELECT b FROM Becado b WHERE b.aptoesfuerzofisico = :aptoesfuerzofisico")
    , @NamedQuery(name = "Becado.findByFumar", query = "SELECT b FROM Becado b WHERE b.fumar = :fumar")
    , @NamedQuery(name = "Becado.findByBeber", query = "SELECT b FROM Becado b WHERE b.beber = :beber")
    , @NamedQuery(name = "Becado.findByNucleofamiliar", query = "SELECT b FROM Becado b WHERE b.nucleofamiliar = :nucleofamiliar")
    , @NamedQuery(name = "Becado.findByCarrera", query = "SELECT b FROM Becado b WHERE b.carrera = :carrera")
    , @NamedQuery(name = "Becado.findByAnno", query = "SELECT b FROM Becado b WHERE b.anno = :anno")
    , @NamedQuery(name = "Becado.findBySexo", query = "SELECT b FROM Becado b WHERE b.sexo = :sexo")
    , @NamedQuery(name = "Becado.findByTelefono", query = "SELECT b FROM Becado b WHERE b.telefono = :telefono")
    , @NamedQuery(name = "Becado.findByFacultad", query = "SELECT b FROM Becado b WHERE b.facultad = :facultad")})

public class Becado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "ci")
    private String ci;
@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "segundonombre")
    private String segundonombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aptoesfuerzofisico")
    private boolean aptoesfuerzofisico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fumar")
    private boolean fumar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "beber")
    private boolean beber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nucleofamiliar")
    private String nucleofamiliar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "carrera")
    private String carrera;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anno")
    private int anno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sexo")
    private boolean sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "facultad")
    private String facultad;
    @ManyToMany(mappedBy = "becadoList", fetch = FetchType.LAZY)
    private List<Medicamentos> medicamentosList;
    @JoinTable(name = "becado_trabajoprod", joinColumns = {
        @JoinColumn(name = "becadoci", referencedColumnName = "ci")}, inverseJoinColumns = {
        @JoinColumn(name = "fecha", referencedColumnName = "fecha")
        , @JoinColumn(name = "pisoid", referencedColumnName = "pisoid")
        , @JoinColumn(name = "edificioid", referencedColumnName = "edificioid")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Trabajoprod> trabajoprodList;
    @ManyToMany(mappedBy = "becadoList", fetch = FetchType.LAZY)
    private List<Enfermedades> enfermedadesList;
    @JoinColumns({
          @JoinColumn(name = "cuartoid", referencedColumnName = "id")
        , @JoinColumn(name = "pisoid", referencedColumnName = "pisoid")
        , @JoinColumn(name = "edificioid", referencedColumnName = "edificioid")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cuarto cuarto;
    @OneToMany(mappedBy = "jefepiso", fetch = FetchType.LAZY)
    private List<Piso> pisoList;
    @OneToMany(mappedBy = "jefelimp", fetch = FetchType.LAZY)
    private List<Piso> pisoList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "becado", fetch = FetchType.LAZY)
    private List<Evalcuarteleria> evalcuarteleriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "becadoci", fetch = FetchType.LAZY)
    private List<Pacientesatendidos> pacientesatendidosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "becado", fetch = FetchType.LAZY)
    private List<Evalguardia> evalguardiaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "becado", fetch = FetchType.LAZY)
    private List<Evalbecado> evalbecadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "becado", fetch = FetchType.LAZY)
    private List<Sanciones> sancionesList;
    @OneToMany(mappedBy = "jefeedif", fetch = FetchType.LAZY)
    private List<Edificio> edificioList;
    @OneToMany(mappedBy = "becadoci", fetch = FetchType.LAZY)
    private List<Planteamientos> planteamientosList;

    public Becado() {
        
    }

    public Becado(String ci) {
        this.ci = ci;
    }

    public Becado(String ci, String codigo, String nombre, String apellidos, boolean activo, boolean aptoesfuerzofisico, boolean fumar, boolean beber, String nucleofamiliar, String carrera, int anno, boolean sexo, String telefono, String facultad, Cuarto cuarto) {
        this.ci = ci;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.activo = activo;
        this.aptoesfuerzofisico = aptoesfuerzofisico;
        this.fumar = fumar;
        this.beber = beber;
        this.nucleofamiliar = nucleofamiliar;
        this.carrera = carrera;
        this.anno = anno;
        this.sexo = sexo;
        this.telefono = telefono;
        this.facultad = facultad;
        this.cuarto = cuarto;
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

    public String getSegundonombre() {
        return segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getAptoesfuerzofisico() {
        return aptoesfuerzofisico;
    }

    public void setAptoesfuerzofisico(boolean aptoesfuerzofisico) {
        this.aptoesfuerzofisico = aptoesfuerzofisico;
    }

    public boolean getFumar() {
        return fumar;
    }

    public void setFumar(boolean fumar) {
        this.fumar = fumar;
    }

    public boolean getBeber() {
        return beber;
    }

    public void setBeber(boolean beber) {
        this.beber = beber;
    }

    public String getNucleofamiliar() {
        return nucleofamiliar;
    }

    public void setNucleofamiliar(String nucleofamiliar) {
        this.nucleofamiliar = nucleofamiliar;
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

    public boolean getSexo() {
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

    @XmlTransient
    public List<Medicamentos> getMedicamentosList() {
        return medicamentosList;
    }

    public void setMedicamentosList(List<Medicamentos> medicamentosList) {
        this.medicamentosList = medicamentosList;
    }

    @XmlTransient
    public List<Trabajoprod> getTrabajoprodList() {
        return trabajoprodList;
    }

    public void setTrabajoprodList(List<Trabajoprod> trabajoprodList) {
        this.trabajoprodList = trabajoprodList;
    }

    @XmlTransient
    public List<Enfermedades> getEnfermedadesList() {
        return enfermedadesList;
    }

    public void setEnfermedadesList(List<Enfermedades> enfermedadesList) {
        this.enfermedadesList = enfermedadesList;
    }

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

    @XmlTransient
    public List<Piso> getPisoList() {
        return pisoList;
    }

    public void setPisoList(List<Piso> pisoList) {
        this.pisoList = pisoList;
    }

    @XmlTransient
    public List<Piso> getPisoList1() {
        return pisoList1;
    }

    public void setPisoList1(List<Piso> pisoList1) {
        this.pisoList1 = pisoList1;
    }

    @XmlTransient
    public List<Evalcuarteleria> getEvalcuarteleriaList() {
        return evalcuarteleriaList;
    }

    public void setEvalcuarteleriaList(List<Evalcuarteleria> evalcuarteleriaList) {
        this.evalcuarteleriaList = evalcuarteleriaList;
    }

    @XmlTransient
    public List<Pacientesatendidos> getPacientesatendidosList() {
        return pacientesatendidosList;
    }

    public void setPacientesatendidosList(List<Pacientesatendidos> pacientesatendidosList) {
        this.pacientesatendidosList = pacientesatendidosList;
    }

    @XmlTransient
    public List<Evalguardia> getEvalguardiaList() {
        return evalguardiaList;
    }

    public void setEvalguardiaList(List<Evalguardia> evalguardiaList) {
        this.evalguardiaList = evalguardiaList;
    }

    @XmlTransient
    public List<Evalbecado> getEvalbecadoList() {
        return evalbecadoList;
    }

    public void setEvalbecadoList(List<Evalbecado> evalbecadoList) {
        this.evalbecadoList = evalbecadoList;
    }

    @XmlTransient
    public List<Sanciones> getSancionesList() {
        return sancionesList;
    }

    public void setSancionesList(List<Sanciones> sancionesList) {
        this.sancionesList = sancionesList;
    }

    @XmlTransient
    public List<Edificio> getEdificioList() {
        return edificioList;
    }

    public void setEdificioList(List<Edificio> edificioList) {
        this.edificioList = edificioList;
    }

    @XmlTransient
    public List<Planteamientos> getPlanteamientosList() {
        return planteamientosList;
    }

    public void setPlanteamientosList(List<Planteamientos> planteamientosList) {
        this.planteamientosList = planteamientosList;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ci != null ? ci.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Becado)) {
            return false;
        }
        Becado other = (Becado) object;
        if ((this.ci == null && other.ci != null) || (this.ci != null && !this.ci.equals(other.ci))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Becado[ ci=" + ci + " ]";
    }
    
}
