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
public class BecadoEnfermedadesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "becadoci")
    private String becadoci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enfermid")
    private int enfermid;

    public BecadoEnfermedadesPK() {
    }

    public BecadoEnfermedadesPK(String becadoci, int enfermid) {
        this.becadoci = becadoci;
        this.enfermid = enfermid;
    }

    public String getBecadoci() {
        return becadoci;
    }

    public void setBecadoci(String becadoci) {
        this.becadoci = becadoci;
    }

    public int getEnfermid() {
        return enfermid;
    }

    public void setEnfermid(int enfermid) {
        this.enfermid = enfermid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (becadoci != null ? becadoci.hashCode() : 0);
        hash += (int) enfermid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BecadoEnfermedadesPK)) {
            return false;
        }
        BecadoEnfermedadesPK other = (BecadoEnfermedadesPK) object;
        if ((this.becadoci == null && other.becadoci != null) || (this.becadoci != null && !this.becadoci.equals(other.becadoci))) {
            return false;
        }
        if (this.enfermid != other.enfermid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BecadoEnfermedadesPK[ becadoci=" + becadoci + ", enfermid=" + enfermid + " ]";
    }
    
}
