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
@Table(name = "aspect_evalcuarto_evalcuarto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findAll", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a")
    , @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findByAspevalcuartoid", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a WHERE a.aspectEvalcuartoEvalcuartoPK.aspevalcuartoid = :aspevalcuartoid")
    , @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findByFecha", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a WHERE a.aspectEvalcuartoEvalcuartoPK.fecha = :fecha")
    , @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findByCuartoid", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a WHERE a.aspectEvalcuartoEvalcuartoPK.cuartoid = :cuartoid")
    , @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findByPisoid", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a WHERE a.aspectEvalcuartoEvalcuartoPK.pisoid = :pisoid")
    , @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findByEdificioid", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a WHERE a.aspectEvalcuartoEvalcuartoPK.edificioid = :edificioid")
    , @NamedQuery(name = "AspectEvalcuartoEvalcuarto.findByValue", query = "SELECT a FROM AspectEvalcuartoEvalcuarto a WHERE a.value = :value")})
public class AspectEvalcuartoEvalcuarto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AspectEvalcuartoEvalcuartoPK aspectEvalcuartoEvalcuartoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private int value;
    @JoinColumn(name = "aspevalcuartoid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AspectEvalcuarto aspectEvalcuarto;
    @JoinColumns({
        @JoinColumn(name = "fecha", referencedColumnName = "fecha", insertable = false, updatable = false)
        , @JoinColumn(name = "cuartoid", referencedColumnName = "cuartoid", insertable = false, updatable = false)
        , @JoinColumn(name = "pisoid", referencedColumnName = "pisoid", insertable = false, updatable = false)
        , @JoinColumn(name = "edificioid", referencedColumnName = "edificioid", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Evalcuarto evalcuarto;

    public AspectEvalcuartoEvalcuarto() {
    }

    public AspectEvalcuartoEvalcuarto(AspectEvalcuartoEvalcuartoPK aspectEvalcuartoEvalcuartoPK) {
        this.aspectEvalcuartoEvalcuartoPK = aspectEvalcuartoEvalcuartoPK;
    }

    public AspectEvalcuartoEvalcuarto(AspectEvalcuartoEvalcuartoPK aspectEvalcuartoEvalcuartoPK, int value) {
        this.aspectEvalcuartoEvalcuartoPK = aspectEvalcuartoEvalcuartoPK;
        this.value = value;
    }

    public AspectEvalcuartoEvalcuarto(int aspevalcuartoid, Date fecha, String cuartoid, String pisoid, String edificioid) {
        this.aspectEvalcuartoEvalcuartoPK = new AspectEvalcuartoEvalcuartoPK(aspevalcuartoid, fecha, cuartoid, pisoid, edificioid);
    }

    public AspectEvalcuartoEvalcuarto(AspectEvalcuartoEvalcuartoPK e, AspectEvalcuarto aspEvalCuartoId, int value) {
        this.aspectEvalcuartoEvalcuartoPK = e;
        this.aspectEvalcuarto = aspEvalCuartoId;
        this.value = value;
    }

    public AspectEvalcuartoEvalcuartoPK getAspectEvalcuartoEvalcuartoPK() {
        return aspectEvalcuartoEvalcuartoPK;
    }

    public void setAspectEvalcuartoEvalcuartoPK(AspectEvalcuartoEvalcuartoPK aspectEvalcuartoEvalcuartoPK) {
        this.aspectEvalcuartoEvalcuartoPK = aspectEvalcuartoEvalcuartoPK;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public AspectEvalcuarto getAspectEvalcuarto() {
        return aspectEvalcuarto;
    }

    public void setAspectEvalcuarto(AspectEvalcuarto aspectEvalcuarto) {
        this.aspectEvalcuarto = aspectEvalcuarto;
    }

    public Evalcuarto getEvalcuarto() {
        return evalcuarto;
    }

    public void setEvalcuarto(Evalcuarto evalcuarto) {
        this.evalcuarto = evalcuarto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aspectEvalcuartoEvalcuartoPK != null ? aspectEvalcuartoEvalcuartoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AspectEvalcuartoEvalcuarto)) {
            return false;
        }
        AspectEvalcuartoEvalcuarto other = (AspectEvalcuartoEvalcuarto) object;
        if ((this.aspectEvalcuartoEvalcuartoPK == null && other.aspectEvalcuartoEvalcuartoPK != null) || (this.aspectEvalcuartoEvalcuartoPK != null && !this.aspectEvalcuartoEvalcuartoPK.equals(other.aspectEvalcuartoEvalcuartoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AspectEvalcuartoEvalcuarto[ aspectEvalcuartoEvalcuartoPK=" + aspectEvalcuartoEvalcuartoPK + " ]";
    }
    
}
