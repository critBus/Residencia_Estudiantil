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
import javax.persistence.JoinColumns;
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
@Table(name = "cuarto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuarto.findAll", query = "SELECT c FROM Cuarto c")
    , @NamedQuery(name = "Cuarto.findById", query = "SELECT c FROM Cuarto c WHERE c.cuartoPK.id = :id")
    , @NamedQuery(name = "Cuarto.findByPisoid", query = "SELECT c FROM Cuarto c WHERE c.cuartoPK.pisoid = :pisoid")
    , @NamedQuery(name = "Cuarto.findByEdificioid", query = "SELECT c FROM Cuarto c WHERE c.cuartoPK.edificioid = :edificioid")})
public class Cuarto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CuartoPK cuartoPK;
    @JoinColumns({
        @JoinColumn(name = "pisoid", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "edificioid", referencedColumnName = "edificioid", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Piso piso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuarto", fetch = FetchType.LAZY)
    private List<Becado> becadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuarto", fetch = FetchType.LAZY)
    private List<Evalcuarto> evalcuartoList;

    public Cuarto() {
    }

    public Cuarto(CuartoPK cuartoPK) {
        this.cuartoPK = cuartoPK;
    }

    public Cuarto(CuartoPK cuartoPK, Piso piso) {
        this.cuartoPK = cuartoPK;
        this.piso = piso;
    }
    
    public Cuarto(String id, String pisoid, String edificioid) {
        this.cuartoPK = new CuartoPK(id, pisoid, edificioid);
    }
    
    public CuartoPK getCuartoPK() {
        return cuartoPK;
    }

    public void setCuartoPK(CuartoPK cuartoPK) {
        this.cuartoPK = cuartoPK;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    @XmlTransient
    public List<Becado> getBecadoList() {
        return becadoList;
    }

    public void setBecadoList(List<Becado> becadoList) {
        this.becadoList = becadoList;
    }

    @XmlTransient
    public List<Evalcuarto> getEvalcuartoList() {
        return evalcuartoList;
    }

    public void setEvalcuartoList(List<Evalcuarto> evalcuartoList) {
        this.evalcuartoList = evalcuartoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuartoPK != null ? cuartoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuarto)) {
            return false;
        }
        Cuarto other = (Cuarto) object;
        if ((this.cuartoPK == null && other.cuartoPK != null) || (this.cuartoPK != null && !this.cuartoPK.equals(other.cuartoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cuarto[ cuartoPK=" + cuartoPK + " ]";
    }
    
}
