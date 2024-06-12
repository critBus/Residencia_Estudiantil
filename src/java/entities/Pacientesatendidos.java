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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "pacientesatendidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pacientesatendidos.findAll", query = "SELECT p FROM Pacientesatendidos p")
    , @NamedQuery(name = "Pacientesatendidos.findById", query = "SELECT p FROM Pacientesatendidos p WHERE p.id = :id")
    , @NamedQuery(name = "Pacientesatendidos.findByFecha", query = "SELECT p FROM Pacientesatendidos p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Pacientesatendidos.findByTipoconsulta", query = "SELECT p FROM Pacientesatendidos p WHERE p.tipoconsulta = :tipoconsulta")})
public class Pacientesatendidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 255)
    @Column(name = "tipoconsulta")
    private String tipoconsulta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacientesatendidos", fetch = FetchType.LAZY)
    private List<PacatendMedicamentos> pacatendMedicamentosList;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becadoci;
    @JoinColumn(name = "medicoci_medico", referencedColumnName = "ci_medico")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Medico medicociMedico;

    public Pacientesatendidos() {
    }

    public Pacientesatendidos(String id) {
        this.id = id;
    }

    public Pacientesatendidos(String id, Date fecha, Becado becado, Medico medico, String tipoConsulta) {
        this.id = id;
        this.fecha = fecha;
        this.becadoci = becado;
        this.medicociMedico = medico;
        this.tipoconsulta = tipoConsulta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoconsulta() {
        return tipoconsulta;
    }

    public void setTipoconsulta(String tipoconsulta) {
        this.tipoconsulta = tipoconsulta;
    }

    @XmlTransient
    public List<PacatendMedicamentos> getPacatendMedicamentosList() {
        return pacatendMedicamentosList;
    }

    public void setPacatendMedicamentosList(List<PacatendMedicamentos> pacatendMedicamentosList) {
        this.pacatendMedicamentosList = pacatendMedicamentosList;
    }

    public Becado getBecadoci() {
        return becadoci;
    }

    public void setBecadoci(Becado becadoci) {
        this.becadoci = becadoci;
    }

    public Medico getMedicociMedico() {
        return medicociMedico;
    }

    public void setMedicociMedico(Medico medicociMedico) {
        this.medicociMedico = medicociMedico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacientesatendidos)) {
            return false;
        }
        Pacientesatendidos other = (Pacientesatendidos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pacientesatendidos[ id=" + id + " ]";
    }
    
}
