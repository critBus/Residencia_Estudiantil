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
public class IncisoreglamPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "articuloid")
    private String articuloid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "capituloid")
    private String capituloid;

    public IncisoreglamPK() {
    }

    public IncisoreglamPK(String id, String articuloid, String capituloid) {
        this.id = id;
        this.articuloid = articuloid;
        this.capituloid = capituloid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticuloid() {
        return articuloid;
    }

    public void setArticuloid(String articuloid) {
        this.articuloid = articuloid;
    }

    public String getCapituloid() {
        return capituloid;
    }

    public void setCapituloid(String capituloid) {
        this.capituloid = capituloid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (articuloid != null ? articuloid.hashCode() : 0);
        hash += (capituloid != null ? capituloid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncisoreglamPK)) {
            return false;
        }
        IncisoreglamPK other = (IncisoreglamPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.articuloid == null && other.articuloid != null) || (this.articuloid != null && !this.articuloid.equals(other.articuloid))) {
            return false;
        }
        if ((this.capituloid == null && other.capituloid != null) || (this.capituloid != null && !this.capituloid.equals(other.capituloid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.IncisoreglamPK[ id=" + id + ", articuloid=" + articuloid + ", capituloid=" + capituloid + " ]";
    }
    
}
