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
@Table(name = "rangos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rangos.findAll", query = "SELECT r FROM Rangos r")
    , @NamedQuery(name = "Rangos.findByNombre", query = "SELECT r FROM Rangos r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "Rangos.findByValormax", query = "SELECT r FROM Rangos r WHERE r.valormax = :valormax")
    , @NamedQuery(name = "Rangos.findByValormin", query = "SELECT r FROM Rangos r WHERE r.valormin = :valormin")})
public class Rangos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "valormax")
    private String valormax;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "valormin")
    private String valormin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cualitativa", fetch = FetchType.LAZY)
    private List<Evalbecado> evalbecadoList;

    public Rangos() {
    }

    public Rangos(String nombre) {
        this.nombre = nombre;
    }

    public Rangos(String nombre, String valormax, String valormin) {
        this.nombre = nombre;
        this.valormax = valormax;
        this.valormin = valormin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValormax() {
        return valormax;
    }

    public void setValormax(String valormax) {
        this.valormax = valormax;
    }

    public String getValormin() {
        return valormin;
    }

    public void setValormin(String valormin) {
        this.valormin = valormin;
    }

    @XmlTransient
    public List<Evalbecado> getEvalbecadoList() {
        return evalbecadoList;
    }

    public void setEvalbecadoList(List<Evalbecado> evalbecadoList) {
        this.evalbecadoList = evalbecadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rangos)) {
            return false;
        }
        Rangos other = (Rangos) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rangos[ nombre=" + nombre + " ]";
    }
    
}
