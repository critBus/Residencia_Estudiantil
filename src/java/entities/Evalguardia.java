/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "evalguardia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalguardia.findAll", query = "SELECT e FROM Evalguardia e")
    , @NamedQuery(name = "Evalguardia.findByFecha", query = "SELECT e FROM Evalguardia e WHERE e.evalguardiaPK.fecha = :fecha")
    , @NamedQuery(name = "Evalguardia.findByBecadoci", query = "SELECT e FROM Evalguardia e WHERE e.evalguardiaPK.becadoci = :becadoci")
    , @NamedQuery(name = "Evalguardia.findByEvaluacion", query = "SELECT e FROM Evalguardia e WHERE e.evaluacion = :evaluacion")})
public class Evalguardia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvalguardiaPK evalguardiaPK;
    @Column(name = "evaluacion")
    private Integer evaluacion;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becado;
    @JoinColumn(name = "trabajadorci", referencedColumnName = "ci")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajador trabajadorci;

    public Evalguardia() {
    }

    public Evalguardia(EvalguardiaPK evalguardiaPK) {
        this.evalguardiaPK = evalguardiaPK;
    }
    
    public Evalguardia(EvalguardiaPK evalguardiaPK, Becado becado, Trabajador trabajador, int evaluacion) {
        this.evalguardiaPK = evalguardiaPK;
        this.becado = becado;
        this.trabajadorci = trabajador;
        this.evaluacion = evaluacion;
    }
    
    public Evalguardia(Date fecha, String becadoci) {
        this.evalguardiaPK = new EvalguardiaPK(fecha, becadoci);
    }

    public EvalguardiaPK getEvalguardiaPK() {
        return evalguardiaPK;
    }

    public void setEvalguardiaPK(EvalguardiaPK evalguardiaPK) {
        this.evalguardiaPK = evalguardiaPK;
    }

    public Integer getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Integer evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public Trabajador getTrabajadorci() {
        return trabajadorci;
    }

    public void setTrabajadorci(Trabajador trabajadorci) {
        this.trabajadorci = trabajadorci;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evalguardiaPK != null ? evalguardiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evalguardia)) {
            return false;
        }
        Evalguardia other = (Evalguardia) object;
        if ((this.evalguardiaPK == null && other.evalguardiaPK != null) || (this.evalguardiaPK != null && !this.evalguardiaPK.equals(other.evalguardiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Evalguardia[ evalguardiaPK=" + evalguardiaPK + " ]";
    }
    
}
