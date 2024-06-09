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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "edificio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Edificio.findAll", query = "SELECT e FROM Edificio e")
    , @NamedQuery(name = "Edificio.findById", query = "SELECT e FROM Edificio e WHERE e.id = :id")
    , @NamedQuery(name = "Edificio.findByNombre", query = "SELECT e FROM Edificio e WHERE e.nombre = :nombre")})
public class Edificio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "edificio", fetch = FetchType.LAZY)
    private List<Piso> pisoList;
    @JoinColumn(name = "jefeedif", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Becado jefeedif;
    @JoinColumn(name = "especialista", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Trabajador especialista;
    @JoinColumn(name = "instructor", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Trabajador instructor;

    public Edificio() {
    }

    public Edificio(String id) {
        this.id = id;
    }

   public Edificio(String id, String nombre, Trabajador especialista, Trabajador instructor, Becado jefeEdif) {
        this.id = id;
        this.nombre = nombre;
        this.especialista = especialista;
        this.instructor = instructor;
        this.jefeedif = jefeEdif;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Piso> getPisoList() {
        return pisoList;
    }

    public void setPisoList(List<Piso> pisoList) {
        this.pisoList = pisoList;
    }

    public Becado getJefeedif() {
        return jefeedif;
    }

    public void setJefeedif(Becado jefeedif) {
        this.jefeedif = jefeedif;
    }

    public Trabajador getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Trabajador especialista) {
        this.especialista = especialista;
    }

    public Trabajador getInstructor() {
        return instructor;
    }

    public void setInstructor(Trabajador instructor) {
        this.instructor = instructor;
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
        if (!(object instanceof Edificio)) {
            return false;
        }
        Edificio other = (Edificio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Edificio[ id=" + id + " ]";
    }
    
}
