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
@Table(name = "pacatend_medicamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PacatendMedicamentos.findAll", query = "SELECT p FROM PacatendMedicamentos p")
    , @NamedQuery(name = "PacatendMedicamentos.findByPacatendid", query = "SELECT p FROM PacatendMedicamentos p WHERE p.pacatendMedicamentosPK.pacatendid = :pacatendid")
    , @NamedQuery(name = "PacatendMedicamentos.findByMedicamid", query = "SELECT p FROM PacatendMedicamentos p WHERE p.pacatendMedicamentosPK.medicamid = :medicamid")
    , @NamedQuery(name = "PacatendMedicamentos.findByCantadim", query = "SELECT p FROM PacatendMedicamentos p WHERE p.cantadim = :cantadim")})
public class PacatendMedicamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PacatendMedicamentosPK pacatendMedicamentosPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cantadim")
    private String cantadim;
    @JoinColumn(name = "medicamid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Medicamentos medicamentos;
    @JoinColumn(name = "pacatendid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pacientesatendidos pacientesatendidos;

    public PacatendMedicamentos() {
    }

    public PacatendMedicamentos(PacatendMedicamentosPK pacatendMedicamentosPK) {
        this.pacatendMedicamentosPK = pacatendMedicamentosPK;
    }

    public PacatendMedicamentos(PacatendMedicamentosPK pacatendMedicamentosPK, Pacientesatendidos pacientesatendidos, Medicamentos medicamentos, String cantadim) {
        this.pacatendMedicamentosPK = pacatendMedicamentosPK;
        this.pacientesatendidos = pacientesatendidos;
        this.medicamentos = medicamentos;
        this.cantadim = cantadim;
    }

    public PacatendMedicamentos(String pacatendid, int medicamid) {
        this.pacatendMedicamentosPK = new PacatendMedicamentosPK(pacatendid, medicamid);
    }

    public PacatendMedicamentosPK getPacatendMedicamentosPK() {
        return pacatendMedicamentosPK;
    }

    public void setPacatendMedicamentosPK(PacatendMedicamentosPK pacatendMedicamentosPK) {
        this.pacatendMedicamentosPK = pacatendMedicamentosPK;
    }

    public String getCantadim() {
        return cantadim;
    }

    public void setCantadim(String cantadim) {
        this.cantadim = cantadim;
    }

    public Medicamentos getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Medicamentos medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Pacientesatendidos getPacientesatendidos() {
        return pacientesatendidos;
    }

    public void setPacientesatendidos(Pacientesatendidos pacientesatendidos) {
        this.pacientesatendidos = pacientesatendidos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pacatendMedicamentosPK != null ? pacatendMedicamentosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PacatendMedicamentos)) {
            return false;
        }
        PacatendMedicamentos other = (PacatendMedicamentos) object;
        if ((this.pacatendMedicamentosPK == null && other.pacatendMedicamentosPK != null) || (this.pacatendMedicamentosPK != null && !this.pacatendMedicamentosPK.equals(other.pacatendMedicamentosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PacatendMedicamentos[ pacatendMedicamentosPK=" + pacatendMedicamentosPK + " ]";
    }
    
}
