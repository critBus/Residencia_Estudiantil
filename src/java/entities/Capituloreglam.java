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
@Table(name = "capituloreglam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capituloreglam.findAll", query = "SELECT c FROM Capituloreglam c")
    , @NamedQuery(name = "Capituloreglam.findById", query = "SELECT c FROM Capituloreglam c WHERE c.id = :id")
    , @NamedQuery(name = "Capituloreglam.findByNombre", query = "SELECT c FROM Capituloreglam c WHERE c.nombre = :nombre")})
public class Capituloreglam implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capituloreglam", fetch = FetchType.LAZY)
    private List<Articuloreglam> articuloreglamList;

    public Capituloreglam() {
    }

    public Capituloreglam(String id) {
        this.id = id;
    }

    public Capituloreglam(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    public List<Articuloreglam> getArticuloreglamList() {
        return articuloreglamList;
    }

    public void setArticuloreglamList(List<Articuloreglam> articuloreglamList) {
        this.articuloreglamList = articuloreglamList;
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
        if (!(object instanceof Capituloreglam)) {
            return false;
        }
        Capituloreglam other = (Capituloreglam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Capituloreglam[ id=" + id + " ]";
    }
    
}
