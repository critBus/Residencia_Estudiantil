/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "piso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Piso.findAll", query = "SELECT p FROM Piso p")
    , @NamedQuery(name = "Piso.findById", query = "SELECT p FROM Piso p WHERE p.pisoPK.id = :id")
    , @NamedQuery(name = "Piso.findByEdificioid", query = "SELECT p FROM Piso p WHERE p.pisoPK.edificioid = :edificioid")})
public class Piso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PisoPK pisoPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "piso", fetch = FetchType.LAZY)
    private List<Cuarto> cuartoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "piso", fetch = FetchType.LAZY)
    private List<Trabajoprod> trabajoprodList;
    @JoinColumn(name = "jefepiso", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Becado jefepiso;
    @JoinColumn(name = "jefelimp", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Becado jefelimp;
    @JoinColumn(name = "edificioid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Edificio edificio;

    public Piso() {
    }

    public Piso(PisoPK pisoPK) {
        this.pisoPK = pisoPK;
    }

    public Piso(PisoPK pisoPK, Becado jefePiso, Becado jefeLimp, Edificio edificio) {
        this.pisoPK = pisoPK;
        this.jefepiso = jefePiso;
        this.jefelimp = jefeLimp;
        this.edificio = edificio;
    }
    
    public Piso(String id, String edificioid) {
        this.pisoPK = new PisoPK(id, edificioid);
    }

    public PisoPK getPisoPK() {
        return pisoPK;
    }

    public void setPisoPK(PisoPK pisoPK) {
        this.pisoPK = pisoPK;
    }

    @XmlTransient
    public List<Cuarto> getCuartoList() {
        return cuartoList;
    }

    public void setCuartoList(List<Cuarto> cuartoList) {
        this.cuartoList = cuartoList;
    }

    @XmlTransient
    public List<Trabajoprod> getTrabajoprodList() {
        return trabajoprodList;
    }

    public void setTrabajoprodList(List<Trabajoprod> trabajoprodList) {
        this.trabajoprodList = trabajoprodList;
    }

    public Becado getJefepiso() {
        return jefepiso;
    }

    public void setJefepiso(Becado jefepiso) {
        this.jefepiso = jefepiso;
    }

    public Becado getJefelimp() {
        return jefelimp;
    }

    public void setJefelimp(Becado jefelimp) {
        this.jefelimp = jefelimp;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pisoPK != null ? pisoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Piso)) {
            return false;
        }
        Piso other = (Piso) object;
        if ((this.pisoPK == null && other.pisoPK != null) || (this.pisoPK != null && !this.pisoPK.equals(other.pisoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Piso[ pisoPK=" + pisoPK + " ]";
    }
    
}
