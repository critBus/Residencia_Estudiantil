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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "evalbecado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalbecado.findAll", query = "SELECT e FROM Evalbecado e")
    , @NamedQuery(name = "Evalbecado.findByFecha", query = "SELECT e FROM Evalbecado e WHERE e.evalbecadoPK.fecha = :fecha")
    , @NamedQuery(name = "Evalbecado.findByBecadoci", query = "SELECT e FROM Evalbecado e WHERE e.evalbecadoPK.becadoci = :becadoci")
    , @NamedQuery(name = "Evalbecado.findByReglamento", query = "SELECT e FROM Evalbecado e WHERE e.reglamento = :reglamento")})
public class Evalbecado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvalbecadoPK evalbecadoPK;
    @Column(name = "reglamento")
    private String reglamento;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becado;
    @JoinColumn(name = "cualitativa", referencedColumnName = "nombre")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Rangos cualitativa;
    @JoinColumn(name = "tipoevalbecadoid", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipoevalbecado tipoevalbecadoid;
    
    @Column(name = "aft")
    private Integer aft;

    public Evalbecado() {
    }

    public Evalbecado(EvalbecadoPK evalbecadoPK) {
        this.evalbecadoPK = evalbecadoPK;
    }

    public Evalbecado(EvalbecadoPK evalbecadoPK, Becado becado, Tipoevalbecado tipoevalbecado, Rangos rangos, String reglamento) {
        this.evalbecadoPK = evalbecadoPK;
        this.becado = becado;
        this.tipoevalbecadoid = tipoevalbecado;
        this.cualitativa = rangos;
        this.reglamento = reglamento;
        this.aft=0;
    }

    public Evalbecado(Date fecha, String becadoci) {
        this.evalbecadoPK = new EvalbecadoPK(fecha, becadoci);
    }

    public EvalbecadoPK getEvalbecadoPK() {
        return evalbecadoPK;
    }

    public void setEvalbecadoPK(EvalbecadoPK evalbecadoPK) {
        this.evalbecadoPK = evalbecadoPK;
    }

    public String getReglamento() {
        return reglamento;
    }

    public void setReglamento(String reglamento) {
        this.reglamento = reglamento;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public Rangos getCualitativa() {
        return cualitativa;
    }

    public void setCualitativa(Rangos cualitativa) {
        this.cualitativa = cualitativa;
    }

    public Tipoevalbecado getTipoevalbecadoid() {
        return tipoevalbecadoid;
    }

    public void setTipoevalbecadoid(Tipoevalbecado tipoevalbecadoid) {
        this.tipoevalbecadoid = tipoevalbecadoid;
    }

    public Integer getAft() {
        return aft;
    }

    public void setAft(Integer aft) {
        this.aft = aft;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evalbecadoPK != null ? evalbecadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evalbecado)) {
            return false;
        }
        Evalbecado other = (Evalbecado) object;
        if ((this.evalbecadoPK == null && other.evalbecadoPK != null) || (this.evalbecadoPK != null && !this.evalbecadoPK.equals(other.evalbecadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Evalbecado[ evalbecadoPK=" + evalbecadoPK + " ]";
    }
    
}
