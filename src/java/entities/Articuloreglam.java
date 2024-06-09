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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "articuloreglam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articuloreglam.findAll", query = "SELECT a FROM Articuloreglam a")
    , @NamedQuery(name = "Articuloreglam.findById", query = "SELECT a FROM Articuloreglam a WHERE a.articuloreglamPK.id = :id")
    , @NamedQuery(name = "Articuloreglam.findByCapituloid", query = "SELECT a FROM Articuloreglam a WHERE a.articuloreglamPK.capituloid = :capituloid")
    , @NamedQuery(name = "Articuloreglam.findByDescripcion", query = "SELECT a FROM Articuloreglam a WHERE a.descripcion = :descripcion")})
public class Articuloreglam implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArticuloreglamPK articuloreglamPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "capituloid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Capituloreglam capituloreglam;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articuloreglam", fetch = FetchType.LAZY)
    private List<Incisoreglam> incisoreglamList;

    public Articuloreglam() {
    }

    public Articuloreglam(ArticuloreglamPK articuloreglamPK) {
        this.articuloreglamPK = articuloreglamPK;
    }

    public Articuloreglam(ArticuloreglamPK articuloreglamPK, String descripcion, Capituloreglam capituloreglam) {
        this.articuloreglamPK = articuloreglamPK;
        this.descripcion = descripcion;
        this.capituloreglam = capituloreglam;
    }

    public Articuloreglam(String id, String capituloid) {
        this.articuloreglamPK = new ArticuloreglamPK(id, capituloid);
    }

    public ArticuloreglamPK getArticuloreglamPK() {
        return articuloreglamPK;
    }

    public void setArticuloreglamPK(ArticuloreglamPK articuloreglamPK) {
        this.articuloreglamPK = articuloreglamPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Capituloreglam getCapituloreglam() {
        return capituloreglam;
    }

    public void setCapituloreglam(Capituloreglam capituloreglam) {
        this.capituloreglam = capituloreglam;
    }

    @XmlTransient
    public List<Incisoreglam> getIncisoreglamList() {
        return incisoreglamList;
    }

    public void setIncisoreglamList(List<Incisoreglam> incisoreglamList) {
        this.incisoreglamList = incisoreglamList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articuloreglamPK != null ? articuloreglamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articuloreglam)) {
            return false;
        }
        Articuloreglam other = (Articuloreglam) object;
        if ((this.articuloreglamPK == null && other.articuloreglamPK != null) || (this.articuloreglamPK != null && !this.articuloreglamPK.equals(other.articuloreglamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Articuloreglam[ articuloreglamPK=" + articuloreglamPK + " ]";
    }
    
}
