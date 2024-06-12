/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "medicamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicamentos.findAll", query = "SELECT m FROM Medicamentos m")
    , @NamedQuery(name = "Medicamentos.findById", query = "SELECT m FROM Medicamentos m WHERE m.id = :id")
    , @NamedQuery(name = "Medicamentos.findByNombre", query = "SELECT m FROM Medicamentos m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Medicamentos.findByTipodosisid", query = "SELECT m FROM Medicamentos m WHERE m.tipodosisid = :tipodosisid")})
public class Medicamentos implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicamentos", fetch = FetchType.LAZY)
    private List<BecadoMedicamentos> becadoMedicamentosList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tipodosisid")
    private String tipodosisid;
    @JoinTable(name = "becado_medicamentos", joinColumns = {
        @JoinColumn(name = "medicamid", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "becadoci", referencedColumnName = "ci")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Becado> becadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicamentos", fetch = FetchType.LAZY)
    private List<PacatendMedicamentos> pacatendMedicamentosList;
    
    public Medicamentos() {
    }

    public Medicamentos(Integer id) {
        this.id = id;
    }

    public Medicamentos(Integer id, String nombre, String tipodosisid) {
        this.id = id;
        this.nombre = nombre;
        this.tipodosisid = tipodosisid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipodosisid() {
        return tipodosisid;
    }

    public void setTipodosisid(String tipodosisid) {
        this.tipodosisid = tipodosisid;
    }

    @XmlTransient
    public List<Becado> getBecadoList() {
        return becadoList;
    }

    public void setBecadoList(List<Becado> becadoList) {
        this.becadoList = becadoList;
    }

    @XmlTransient
    public List<PacatendMedicamentos> getPacatendMedicamentosList() {
        return pacatendMedicamentosList;
    }

    public void setPacatendMedicamentosList(List<PacatendMedicamentos> pacatendMedicamentosList) {
        this.pacatendMedicamentosList = pacatendMedicamentosList;
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
        if (!(object instanceof Medicamentos)) {
            return false;
        }
        Medicamentos other = (Medicamentos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Medicamentos[ id=" + id + " ]";
    }

    @XmlTransient
    public List<BecadoMedicamentos> getBecadoMedicamentosList() {
        return becadoMedicamentosList;
    }

    public void setBecadoMedicamentosList(List<BecadoMedicamentos> becadoMedicamentosList) {
        this.becadoMedicamentosList = becadoMedicamentosList;
    }
    
}
