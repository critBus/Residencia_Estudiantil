/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Euclides
 */
@Embeddable
public class AspectEvalcuartoEvalcuartoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "aspevalcuartoid")
    private int aspevalcuartoid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cuartoid")
    private String cuartoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pisoid")
    private String pisoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "edificioid")
    private String edificioid;

    public AspectEvalcuartoEvalcuartoPK() {
    }

    public AspectEvalcuartoEvalcuartoPK(int aspevalcuartoid, Date fecha, String cuartoid, String pisoid, String edificioid) {
        this.aspevalcuartoid = aspevalcuartoid;
        this.fecha = fecha;
        this.cuartoid = cuartoid;
        this.pisoid = pisoid;
        this.edificioid = edificioid;
    }
    
    public AspectEvalcuartoEvalcuartoPK(Date fecha, String cuartoid, String pisoid, String edificioid) {
        this.fecha = fecha;
        this.cuartoid = cuartoid;
        this.pisoid = pisoid;
        this.edificioid = edificioid;
    }
    
    public AspectEvalcuartoEvalcuartoPK(Date fecha, int aspevalcuartoid, String cuartoid, String pisoid, String edificioid) {
        this.fecha = fecha;
        this.aspevalcuartoid = aspevalcuartoid;
        this.cuartoid = cuartoid;
        this.pisoid = pisoid;
        this.edificioid = edificioid;
    }

    public int getAspevalcuartoid() {
        return aspevalcuartoid;
    }

    public void setAspevalcuartoid(int aspevalcuartoid) {
        this.aspevalcuartoid = aspevalcuartoid;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCuartoid() {
        return cuartoid;
    }

    public void setCuartoid(String cuartoid) {
        this.cuartoid = cuartoid;
    }

    public String getPisoid() {
        return pisoid;
    }

    public void setPisoid(String pisoid) {
        this.pisoid = pisoid;
    }

    public String getEdificioid() {
        return edificioid;
    }

    public void setEdificioid(String edificioid) {
        this.edificioid = edificioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) aspevalcuartoid;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (cuartoid != null ? cuartoid.hashCode() : 0);
        hash += (pisoid != null ? pisoid.hashCode() : 0);
        hash += (edificioid != null ? edificioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AspectEvalcuartoEvalcuartoPK)) {
            return false;
        }
        AspectEvalcuartoEvalcuartoPK other = (AspectEvalcuartoEvalcuartoPK) object;
        if (this.aspevalcuartoid != other.aspevalcuartoid) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.cuartoid == null && other.cuartoid != null) || (this.cuartoid != null && !this.cuartoid.equals(other.cuartoid))) {
            return false;
        }
        if ((this.pisoid == null && other.pisoid != null) || (this.pisoid != null && !this.pisoid.equals(other.pisoid))) {
            return false;
        }
        if ((this.edificioid == null && other.edificioid != null) || (this.edificioid != null && !this.edificioid.equals(other.edificioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AspectEvalcuartoEvalcuartoPK[ aspevalcuartoid=" + aspevalcuartoid + ", fecha=" + fecha + ", cuartoid=" + cuartoid + ", pisoid=" + pisoid + ", edificioid=" + edificioid + " ]";
    }
    
}
