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
@Table(name = "becado_medicamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BecadoMedicamentos.findAll", query = "SELECT b FROM BecadoMedicamentos b")
    , @NamedQuery(name = "BecadoMedicamentos.findByBecadoci", query = "SELECT b FROM BecadoMedicamentos b WHERE b.becadoMedicamentosPK.becadoci = :becadoci")
    , @NamedQuery(name = "BecadoMedicamentos.findByMedicamid", query = "SELECT b FROM BecadoMedicamentos b WHERE b.becadoMedicamentosPK.medicamid = :medicamid")
    , @NamedQuery(name = "BecadoMedicamentos.findByDescripcion", query = "SELECT b FROM BecadoMedicamentos b WHERE b.descripcion = :descripcion")})
public class BecadoMedicamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BecadoMedicamentosPK becadoMedicamentosPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becado;
    @JoinColumn(name = "medicamid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Medicamentos medicamentos;

    public BecadoMedicamentos() {
    }

    public BecadoMedicamentos(BecadoMedicamentosPK becadoMedicamentosPK) {
        this.becadoMedicamentosPK = becadoMedicamentosPK;
    }

    public BecadoMedicamentos(BecadoMedicamentosPK becadoMedicamentosPK, Medicamentos medicamentos, Becado becado, String descripcion) {
        this.becadoMedicamentosPK = becadoMedicamentosPK;
        this.medicamentos = medicamentos;
        this.becado = becado;
        this.descripcion = descripcion;
    }

    public BecadoMedicamentos(String becadoci, int medicamid) {
        this.becadoMedicamentosPK = new BecadoMedicamentosPK(becadoci, medicamid);
    }

    public BecadoMedicamentosPK getBecadoMedicamentosPK() {
        return becadoMedicamentosPK;
    }

    public void setBecadoMedicamentosPK(BecadoMedicamentosPK becadoMedicamentosPK) {
        this.becadoMedicamentosPK = becadoMedicamentosPK;
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

    public Medicamentos getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Medicamentos medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (becadoMedicamentosPK != null ? becadoMedicamentosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BecadoMedicamentos)) {
            return false;
        }
        BecadoMedicamentos other = (BecadoMedicamentos) object;
        if ((this.becadoMedicamentosPK == null && other.becadoMedicamentosPK != null) || (this.becadoMedicamentosPK != null && !this.becadoMedicamentosPK.equals(other.becadoMedicamentosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BecadoMedicamentos[ becadoMedicamentosPK=" + becadoMedicamentosPK + " ]";
    }
    
}
