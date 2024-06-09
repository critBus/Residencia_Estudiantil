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
import javax.persistence.Id;
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
@Table(name = "medico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medico.findAll", query = "SELECT m FROM Medico m")
    , @NamedQuery(name = "Medico.findByCiMedico", query = "SELECT m FROM Medico m WHERE m.ciMedico = :ciMedico")
    , @NamedQuery(name = "Medico.findByNombre", query = "SELECT m FROM Medico m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Medico.findByApellidos", query = "SELECT m FROM Medico m WHERE m.apellidos = :apellidos")
    , @NamedQuery(name = "Medico.findByEspecialidad", query = "SELECT m FROM Medico m WHERE m.especialidad = :especialidad")
    , @NamedQuery(name = "Medico.findByDireccion", query = "SELECT m FROM Medico m WHERE m.direccion = :direccion")})
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ci_medico")
    private String ciMedico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 255)
    @Column(name = "especialidad")
    private String especialidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicociMedico", fetch = FetchType.LAZY)
    private List<Pacientesatendidos> pacientesatendidosList;

    public Medico() {
    }

    public Medico(String ciMedico) {
        this.ciMedico = ciMedico;
    }

    public Medico(String ciMedico, String nombre, String apellidos, String especialidad, String direccion) {
        this.ciMedico = ciMedico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.especialidad = especialidad;
    }

    public String getCiMedico() {
        return ciMedico;
    }

    public void setCiMedico(String ciMedico) {
        this.ciMedico = ciMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public List<Pacientesatendidos> getPacientesatendidosList() {
        return pacientesatendidosList;
    }

    public void setPacientesatendidosList(List<Pacientesatendidos> pacientesatendidosList) {
        this.pacientesatendidosList = pacientesatendidosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciMedico != null ? ciMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medico)) {
            return false;
        }
        Medico other = (Medico) object;
        if ((this.ciMedico == null && other.ciMedico != null) || (this.ciMedico != null && !this.ciMedico.equals(other.ciMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Medico[ ciMedico=" + ciMedico + " ]";
    }
    
}
