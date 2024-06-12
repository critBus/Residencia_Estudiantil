/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
@Table(name = "becado_enfermedades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BecadoEnfermedades.findAll", query = "SELECT b FROM BecadoEnfermedades b")
    , @NamedQuery(name = "BecadoEnfermedades.findByBecadoci", query = "SELECT b FROM BecadoEnfermedades b WHERE b.becadoEnfermedadesPK.becadoci = :becadoci")
    , @NamedQuery(name = "BecadoEnfermedades.findByEnfermid", query = "SELECT b FROM BecadoEnfermedades b WHERE b.becadoEnfermedadesPK.enfermid = :enfermid")
    , @NamedQuery(name = "BecadoEnfermedades.findByDescripcion", query = "SELECT b FROM BecadoEnfermedades b WHERE b.descripcion = :descripcion")})
public class BecadoEnfermedades implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BecadoEnfermedadesPK becadoEnfermedadesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becado;
    @JoinColumn(name = "enfermid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Enfermedades enfermedades;

    public BecadoEnfermedades() {
    }

    public BecadoEnfermedades(BecadoEnfermedadesPK becadoEnfermedadesPK) {
        this.becadoEnfermedadesPK = becadoEnfermedadesPK;
    }

    public BecadoEnfermedades(BecadoEnfermedadesPK becadoEnfermedadesPK, Enfermedades enfermedades, Becado becado, String descripcion) {
        this.becadoEnfermedadesPK = becadoEnfermedadesPK;
        this.enfermedades = enfermedades;
        this.becado = becado;
        this.descripcion = descripcion;
    }

    public BecadoEnfermedades(String becadoci, int enfermid) {
        this.becadoEnfermedadesPK = new BecadoEnfermedadesPK(becadoci, enfermid);
    }

    public BecadoEnfermedadesPK getBecadoEnfermedadesPK() {
        return becadoEnfermedadesPK;
    }

    public void setBecadoEnfermedadesPK(BecadoEnfermedadesPK becadoEnfermedadesPK) {
        this.becadoEnfermedadesPK = becadoEnfermedadesPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public Enfermedades getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(Enfermedades enfermedades) {
        this.enfermedades = enfermedades;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (becadoEnfermedadesPK != null ? becadoEnfermedadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BecadoEnfermedades)) {
            return false;
        }
        BecadoEnfermedades other = (BecadoEnfermedades) object;
        if ((this.becadoEnfermedadesPK == null && other.becadoEnfermedadesPK != null) || (this.becadoEnfermedadesPK != null && !this.becadoEnfermedadesPK.equals(other.becadoEnfermedadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BecadoEnfermedades[ becadoEnfermedadesPK=" + becadoEnfermedadesPK + " ]";
    }
    
}
