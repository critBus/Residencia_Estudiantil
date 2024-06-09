/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Euclides
 */
@Embeddable
public class CuartoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private String id;
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

    public CuartoPK() {
    }

    public CuartoPK(String id, String pisoid, String edificioid) {
        this.id = id;
        this.pisoid = pisoid;
        this.edificioid = edificioid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        hash += (id != null ? id.hashCode() : 0);
        hash += (pisoid != null ? pisoid.hashCode() : 0);
        hash += (edificioid != null ? edificioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuartoPK)) {
            return false;
        }
        CuartoPK other = (CuartoPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
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
        return "entities.CuartoPK[ id=" + id + ", pisoid=" + pisoid + ", edificioid=" + edificioid + " ]";
    }
    
}
