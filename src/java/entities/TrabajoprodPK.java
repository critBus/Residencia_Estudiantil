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
public class TrabajoprodPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
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

    public TrabajoprodPK() {
    }

    public TrabajoprodPK(Date fecha, String pisoid, String edificioid) {
        this.fecha = fecha;
        this.pisoid = pisoid;
        this.edificioid = edificioid;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (pisoid != null ? pisoid.hashCode() : 0);
        hash += (edificioid != null ? edificioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrabajoprodPK)) {
            return false;
        }
        TrabajoprodPK other = (TrabajoprodPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
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
        return "entities.TrabajoprodPK[ fecha=" + fecha + ", pisoid=" + pisoid + ", edificioid=" + edificioid + " ]";
    }
    
}
