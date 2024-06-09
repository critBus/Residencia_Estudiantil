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
import javax.persistence.JoinColumns;
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
@Table(name = "incisoreglam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incisoreglam.findAll", query = "SELECT i FROM Incisoreglam i")
    , @NamedQuery(name = "Incisoreglam.findById", query = "SELECT i FROM Incisoreglam i WHERE i.incisoreglamPK.id = :id")
    , @NamedQuery(name = "Incisoreglam.findByArticuloid", query = "SELECT i FROM Incisoreglam i WHERE i.incisoreglamPK.articuloid = :articuloid")
    , @NamedQuery(name = "Incisoreglam.findByCapituloid", query = "SELECT i FROM Incisoreglam i WHERE i.incisoreglamPK.capituloid = :capituloid")
    , @NamedQuery(name = "Incisoreglam.findByDescripcion", query = "SELECT i FROM Incisoreglam i WHERE i.descripcion = :descripcion")})
public class Incisoreglam implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IncisoreglamPK incisoreglamPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumns({
        @JoinColumn(name = "articuloid", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "capituloid", referencedColumnName = "capituloid", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Articuloreglam articuloreglam;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incisoreglam", fetch = FetchType.LAZY)
    private List<Sanciones> sancionesList;

    public Incisoreglam() {
    }

    public Incisoreglam(IncisoreglamPK incisoreglamPK) {
        this.incisoreglamPK = incisoreglamPK;
    }

    public Incisoreglam(IncisoreglamPK incisoreglamPK, String descripcion, Articuloreglam articuloreglam) {
        this.incisoreglamPK = incisoreglamPK;
        this.descripcion = descripcion;
        this.articuloreglam = articuloreglam;
    }

    public Incisoreglam(String id, String articuloid, String capituloid) {
        this.incisoreglamPK = new IncisoreglamPK(id, articuloid, capituloid);
    }

    public IncisoreglamPK getIncisoreglamPK() {
        return incisoreglamPK;
    }

    public void setIncisoreglamPK(IncisoreglamPK incisoreglamPK) {
        this.incisoreglamPK = incisoreglamPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Articuloreglam getArticuloreglam() {
        return articuloreglam;
    }

    public void setArticuloreglam(Articuloreglam articuloreglam) {
        this.articuloreglam = articuloreglam;
    }

    @XmlTransient
    public List<Sanciones> getSancionesList() {
        return sancionesList;
    }

    public void setSancionesList(List<Sanciones> sancionesList) {
        this.sancionesList = sancionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incisoreglamPK != null ? incisoreglamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incisoreglam)) {
            return false;
        }
        Incisoreglam other = (Incisoreglam) object;
        if ((this.incisoreglamPK == null && other.incisoreglamPK != null) || (this.incisoreglamPK != null && !this.incisoreglamPK.equals(other.incisoreglamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Incisoreglam[ incisoreglamPK=" + incisoreglamPK + " ]";
    }
    
}
