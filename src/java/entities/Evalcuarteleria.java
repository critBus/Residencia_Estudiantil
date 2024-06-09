/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "evalcuarteleria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalcuarteleria.findAll", query = "SELECT e FROM Evalcuarteleria e")
    , @NamedQuery(name = "Evalcuarteleria.findByFecha", query = "SELECT e FROM Evalcuarteleria e WHERE e.evalcuarteleriaPK.fecha = :fecha")
    , @NamedQuery(name = "Evalcuarteleria.findByBecadoci", query = "SELECT e FROM Evalcuarteleria e WHERE e.evalcuarteleriaPK.becadoci = :becadoci")})
public class Evalcuarteleria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvalcuarteleriaPK evalcuarteleriaPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalcuarteleria", fetch = FetchType.LAZY)
    private List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelList;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becado;
    @JoinColumn(name = "inspeccionaci", referencedColumnName = "ci")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajador inspeccionaci;

    public Evalcuarteleria() {
    }

    public Evalcuarteleria(EvalcuarteleriaPK evalcuarteleriaPK) {
        this.evalcuarteleriaPK = evalcuarteleriaPK;
    }
    
    public Evalcuarteleria(EvalcuarteleriaPK evalcuarteleriaPK, Becado becado, Trabajador trabajador) {
        this.evalcuarteleriaPK = evalcuarteleriaPK;
        this.becado = becado;
        this.inspeccionaci = trabajador;
    }

    public Evalcuarteleria(Date fecha, String becadoci) {
        this.evalcuarteleriaPK = new EvalcuarteleriaPK(fecha, becadoci);
    }

    public EvalcuarteleriaPK getEvalcuarteleriaPK() {
        return evalcuarteleriaPK;
    }

    public void setEvalcuarteleriaPK(EvalcuarteleriaPK evalcuarteleriaPK) {
        this.evalcuarteleriaPK = evalcuarteleriaPK;
    }

    @XmlTransient
    public List<EvalcuarteleriaAspectevalcuartel> getEvalcuarteleriaAspectevalcuartelList() {
        return evalcuarteleriaAspectevalcuartelList;
    }

    public void setEvalcuarteleriaAspectevalcuartelList(List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelList) {
        this.evalcuarteleriaAspectevalcuartelList = evalcuarteleriaAspectevalcuartelList;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public Trabajador getInspeccionaci() {
        return inspeccionaci;
    }

    public void setInspeccionaci(Trabajador inspeccionaci) {
        this.inspeccionaci = inspeccionaci;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evalcuarteleriaPK != null ? evalcuarteleriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evalcuarteleria)) {
            return false;
        }
        Evalcuarteleria other = (Evalcuarteleria) object;
        if ((this.evalcuarteleriaPK == null && other.evalcuarteleriaPK != null) || (this.evalcuarteleriaPK != null && !this.evalcuarteleriaPK.equals(other.evalcuarteleriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Evalcuarteleria[ evalcuarteleriaPK=" + evalcuarteleriaPK + " ]";
    }
    
}
