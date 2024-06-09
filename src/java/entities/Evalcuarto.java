/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "evalcuarto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalcuarto.findAll", query = "SELECT e FROM Evalcuarto e")
    , @NamedQuery(name = "Evalcuarto.findByFecha", query = "SELECT e FROM Evalcuarto e WHERE e.evalcuartoPK.fecha = :fecha")
    , @NamedQuery(name = "Evalcuarto.findByCuartoid", query = "SELECT e FROM Evalcuarto e WHERE e.evalcuartoPK.cuartoid = :cuartoid")
    , @NamedQuery(name = "Evalcuarto.findByPisoid", query = "SELECT e FROM Evalcuarto e WHERE e.evalcuartoPK.pisoid = :pisoid")
    , @NamedQuery(name = "Evalcuarto.findByEdificioid", query = "SELECT e FROM Evalcuarto e WHERE e.evalcuartoPK.edificioid = :edificioid")})
public class Evalcuarto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvalcuartoPK evalcuartoPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalcuarto", fetch = FetchType.LAZY)
    private List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoList;
    @JoinColumns({
        @JoinColumn(name = "cuartoid", referencedColumnName = "id", insertable = false, updatable = false)
        , @JoinColumn(name = "pisoid", referencedColumnName = "pisoid", insertable = false, updatable = false)
        , @JoinColumn(name = "edificioid", referencedColumnName = "edificioid", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cuarto cuarto;
    @JoinColumn(name = "trabajadorci", referencedColumnName = "ci")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajador trabajadorci;

    public Evalcuarto() {
    }

    public Evalcuarto(EvalcuartoPK evalcuartoPK) {
        this.evalcuartoPK = evalcuartoPK;
    }
    
    public Evalcuarto(EvalcuartoPK evalcuartoPK, Trabajador inspecciona, Cuarto cuarto) {
        this.evalcuartoPK = evalcuartoPK;
        this.trabajadorci = inspecciona;
        this.cuarto = cuarto;
    }

    public Evalcuarto(Date fecha, String cuartoid, String pisoid, String edificioid) {
        this.evalcuartoPK = new EvalcuartoPK(fecha, cuartoid, pisoid, edificioid);
    }
    
    public EvalcuartoPK getEvalcuartoPK() {
        return evalcuartoPK;
    }

    public void setEvalcuartoPK(EvalcuartoPK evalcuartoPK) {
        this.evalcuartoPK = evalcuartoPK;
    }

    @XmlTransient
    public List<AspectEvalcuartoEvalcuarto> getAspectEvalcuartoEvalcuartoList() {
        return aspectEvalcuartoEvalcuartoList;
    }

    public void setAspectEvalcuartoEvalcuartoList(List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoList) {
        this.aspectEvalcuartoEvalcuartoList = aspectEvalcuartoEvalcuartoList;
    }

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

    public Trabajador getTrabajadorci() {
        return trabajadorci;
    }

    public void setTrabajadorci(Trabajador trabajadorci) {
        this.trabajadorci = trabajadorci;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evalcuartoPK != null ? evalcuartoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evalcuarto)) {
            return false;
        }
        Evalcuarto other = (Evalcuarto) object;
        if ((this.evalcuartoPK == null && other.evalcuartoPK != null) || (this.evalcuartoPK != null && !this.evalcuartoPK.equals(other.evalcuartoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Evalcuarto[ evalcuartoPK=" + evalcuartoPK + " ]";
    }
    
}
