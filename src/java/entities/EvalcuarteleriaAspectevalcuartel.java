/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "evalcuarteleria_aspectevalcuartel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalcuarteleriaAspectevalcuartel.findAll", query = "SELECT e FROM EvalcuarteleriaAspectevalcuartel e")
    , @NamedQuery(name = "EvalcuarteleriaAspectevalcuartel.findByFecha", query = "SELECT e FROM EvalcuarteleriaAspectevalcuartel e WHERE e.evalcuarteleriaAspectevalcuartelPK.fecha = :fecha")
    , @NamedQuery(name = "EvalcuarteleriaAspectevalcuartel.findByBecadoci", query = "SELECT e FROM EvalcuarteleriaAspectevalcuartel e WHERE e.evalcuarteleriaAspectevalcuartelPK.becadoci = :becadoci")
    , @NamedQuery(name = "EvalcuarteleriaAspectevalcuartel.findByAspectEvalcuartelid", query = "SELECT e FROM EvalcuarteleriaAspectevalcuartel e WHERE e.evalcuarteleriaAspectevalcuartelPK.aspectEvalcuartelid = :aspectEvalcuartelid")
    , @NamedQuery(name = "EvalcuarteleriaAspectevalcuartel.findByValue", query = "SELECT e FROM EvalcuarteleriaAspectevalcuartel e WHERE e.value = :value")})
public class EvalcuarteleriaAspectevalcuartel implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvalcuarteleriaAspectevalcuartelPK evalcuarteleriaAspectevalcuartelPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private int value;
    @JoinColumn(name = "aspect_evalcuartelid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AspectEvalcuartel aspectEvalcuartel;
    @JoinColumns({
        @JoinColumn(name = "fecha", referencedColumnName = "fecha", insertable = false, updatable = false)
        , @JoinColumn(name = "becadoci", referencedColumnName = "becadoci", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Evalcuarteleria evalcuarteleria;

    public EvalcuarteleriaAspectevalcuartel() {
    }

    public EvalcuarteleriaAspectevalcuartel(EvalcuarteleriaAspectevalcuartelPK evalcuarteleriaAspectevalcuartelPK) {
        this.evalcuarteleriaAspectevalcuartelPK = evalcuarteleriaAspectevalcuartelPK;
    }

    public EvalcuarteleriaAspectevalcuartel(EvalcuarteleriaAspectevalcuartelPK evalcuarteleriaAspectevalcuartelPK, int value) {
        this.evalcuarteleriaAspectevalcuartelPK = evalcuarteleriaAspectevalcuartelPK;
        this.value = value;
    }

    public EvalcuarteleriaAspectevalcuartel(Date fecha, int evalcuartelid, String becadoci) {
        this.evalcuarteleriaAspectevalcuartelPK = new EvalcuarteleriaAspectevalcuartelPK(fecha, evalcuartelid, becadoci);
    }

    public EvalcuarteleriaAspectevalcuartelPK getEvalcuarteleriaAspectevalcuartelPK() {
        return evalcuarteleriaAspectevalcuartelPK;
    }

    public void setEvalcuarteleriaAspectevalcuartelPK(EvalcuarteleriaAspectevalcuartelPK evalcuarteleriaAspectevalcuartelPK) {
        this.evalcuarteleriaAspectevalcuartelPK = evalcuarteleriaAspectevalcuartelPK;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public AspectEvalcuartel getAspectEvalcuartel() {
        return aspectEvalcuartel;
    }

    public void setAspectEvalcuartel(AspectEvalcuartel aspectEvalcuartel) {
        this.aspectEvalcuartel = aspectEvalcuartel;
    }

    public Evalcuarteleria getEvalcuarteleria() {
        return evalcuarteleria;
    }

    public void setEvalcuarteleria(Evalcuarteleria evalcuarteleria) {
        this.evalcuarteleria = evalcuarteleria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evalcuarteleriaAspectevalcuartelPK != null ? evalcuarteleriaAspectevalcuartelPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalcuarteleriaAspectevalcuartel)) {
            return false;
        }
        EvalcuarteleriaAspectevalcuartel other = (EvalcuarteleriaAspectevalcuartel) object;
        if ((this.evalcuarteleriaAspectevalcuartelPK == null && other.evalcuarteleriaAspectevalcuartelPK != null) || (this.evalcuarteleriaAspectevalcuartelPK != null && !this.evalcuarteleriaAspectevalcuartelPK.equals(other.evalcuarteleriaAspectevalcuartelPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EvalcuarteleriaAspectevalcuartel[ evalcuarteleriaAspectevalcuartelPK=" + evalcuarteleriaAspectevalcuartelPK + " ]";
    }
    
}
