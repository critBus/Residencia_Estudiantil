/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "trabajoprod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajoprod.findAll", query = "SELECT t FROM Trabajoprod t")
    , @NamedQuery(name = "Trabajoprod.findByFecha", query = "SELECT t FROM Trabajoprod t WHERE t.trabajoprodPK.fecha = :fecha")
    , @NamedQuery(name = "Trabajoprod.findByPisoid", query = "SELECT t FROM Trabajoprod t WHERE t.trabajoprodPK.pisoid = :pisoid")
    , @NamedQuery(name = "Trabajoprod.findByEdificioid", query = "SELECT t FROM Trabajoprod t WHERE t.trabajoprodPK.edificioid = :edificioid")
    , @NamedQuery(name = "Trabajoprod.findByEvaluacion", query = "SELECT t FROM Trabajoprod t WHERE t.evaluacion = :evaluacion")})
public class Trabajoprod implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TrabajoprodPK trabajoprodPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "evaluacion")
    private int evaluacion;
    @ManyToMany(mappedBy = "trabajoprodList", fetch = FetchType.LAZY)
    private List<Becado> becadoList;
    @JoinColumns({
        @JoinColumn(name = "pisoid", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "edificioid", referencedColumnName = "edificioid", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Piso piso;

    public Trabajoprod() {
    }

    public Trabajoprod(TrabajoprodPK trabajoprodPK) {
        this.trabajoprodPK = trabajoprodPK;
    }

    public Trabajoprod(TrabajoprodPK trabajoprodPK, int evaluacion, Piso piso) {
        this.trabajoprodPK = trabajoprodPK;
        this.evaluacion = evaluacion;
        this.piso = piso;
    }

    public Trabajoprod(Date fecha, String pisoid, String edificioid) {
        this.trabajoprodPK = new TrabajoprodPK(fecha, pisoid, edificioid);
    }

    public TrabajoprodPK getTrabajoprodPK() {
        return trabajoprodPK;
    }

    public void setTrabajoprodPK(TrabajoprodPK trabajoprodPK) {
        this.trabajoprodPK = trabajoprodPK;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    @XmlTransient
    public List<Becado> getBecadoList() {
        return becadoList;
    }

    public void setBecadoList(List<Becado> becadoList) {
        this.becadoList = becadoList;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trabajoprodPK != null ? trabajoprodPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajoprod)) {
            return false;
        }
        Trabajoprod other = (Trabajoprod) object;
        if ((this.trabajoprodPK == null && other.trabajoprodPK != null) || (this.trabajoprodPK != null && !this.trabajoprodPK.equals(other.trabajoprodPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Trabajoprod[ trabajoprodPK=" + trabajoprodPK + " ]";
    }
    
}
