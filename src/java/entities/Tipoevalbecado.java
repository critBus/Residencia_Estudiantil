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
@Table(name = "tipoevalbecado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoevalbecado.findAll", query = "SELECT t FROM Tipoevalbecado t")
    , @NamedQuery(name = "Tipoevalbecado.findById", query = "SELECT t FROM Tipoevalbecado t WHERE t.id = :id")
    , @NamedQuery(name = "Tipoevalbecado.findByTipo", query = "SELECT t FROM Tipoevalbecado t WHERE t.tipo = :tipo")
    , @NamedQuery(name = "Tipoevalbecado.findByDescripcion", query = "SELECT t FROM Tipoevalbecado t WHERE t.descripcion = :descripcion")})
public class Tipoevalbecado implements Serializable {

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
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoevalbecadoid", fetch = FetchType.LAZY)
    private List<Evalbecado> evalbecadoList;

    public Tipoevalbecado() {
    }

    public Tipoevalbecado(String id) {
        this.id = id;
    }

    public Tipoevalbecado(String id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoevalbecado)) {
            return false;
        }
        Tipoevalbecado other = (Tipoevalbecado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tipoevalbecado[ id=" + id + " ]";
    }
    
}
