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
@Table(name = "trabajador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajador.findAll", query = "SELECT t FROM Trabajador t")
    , @NamedQuery(name = "Trabajador.findByCi", query = "SELECT t FROM Trabajador t WHERE t.ci = :ci")
    , @NamedQuery(name = "Trabajador.findByCodigo", query = "SELECT t FROM Trabajador t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "Trabajador.findByNombre", query = "SELECT t FROM Trabajador t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Trabajador.findByApellidos", query = "SELECT t FROM Trabajador t WHERE t.apellidos = :apellidos")
    , @NamedQuery(name = "Trabajador.findByEnable", query = "SELECT t FROM Trabajador t WHERE t.enable = :enable")})
public class Trabajador implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enable")
    private boolean enable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inspeccionaci", fetch = FetchType.LAZY)
    private List<Evalcuarteleria> evalcuarteleriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajadorci", fetch = FetchType.LAZY)
    private List<Evalguardia> evalguardiaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajadorci", fetch = FetchType.LAZY)
    private List<Evalcuarto> evalcuartoList;
    @OneToMany(mappedBy = "especialista", fetch = FetchType.LAZY)
    private List<Edificio> edificioList;
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private List<Edificio> edificioList1;
    @OneToMany(mappedBy = "trabajadorci", fetch = FetchType.LAZY)
    private List<Planteamientos> planteamientosList;

    public Trabajador() {
    }
    
    public Trabajador(String ci) {
        this.ci = ci;
    }

    public Trabajador(String ci, String codigo, String nombre, String apellidos, boolean enable) {
        this.ci = ci;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.enable = enable;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @XmlTransient
    public List<Evalcuarteleria> getEvalcuarteleriaList() {
        return evalcuarteleriaList;
    }

    public void setEvalcuarteleriaList(List<Evalcuarteleria> evalcuarteleriaList) {
        this.evalcuarteleriaList = evalcuarteleriaList;
    }

    @XmlTransient
    public List<Evalguardia> getEvalguardiaList() {
        return evalguardiaList;
    }

    public void setEvalguardiaList(List<Evalguardia> evalguardiaList) {
        this.evalguardiaList = evalguardiaList;
    }

    @XmlTransient
    public List<Evalcuarto> getEvalcuartoList() {
        return evalcuartoList;
    }

    public void setEvalcuartoList(List<Evalcuarto> evalcuartoList) {
        this.evalcuartoList = evalcuartoList;
    }

    @XmlTransient
    public List<Edificio> getEdificioList() {
        return edificioList;
    }

    public void setEdificioList(List<Edificio> edificioList) {
        this.edificioList = edificioList;
    }

    @XmlTransient
    public List<Edificio> getEdificioList1() {
        return edificioList1;
    }

    public void setEdificioList1(List<Edificio> edificioList1) {
        this.edificioList1 = edificioList1;
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
        if (!(object instanceof Trabajador)) {
            return false;
        }
        Trabajador other = (Trabajador) object;
        if ((this.ci == null && other.ci != null) || (this.ci != null && !this.ci.equals(other.ci))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Trabajador[ ci=" + ci + " ]";
    }
    
}
