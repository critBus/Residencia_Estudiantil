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
public class BecadoMedicamentosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "becadoci")
    private String becadoci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "medicamid")
    private int medicamid;

    public BecadoMedicamentosPK() {
    }

    public BecadoMedicamentosPK(String becadoci, int medicamid) {
        this.becadoci = becadoci;
        this.medicamid = medicamid;
    }

    public String getBecadoci() {
        return becadoci;
    }

    public void setBecadoci(String becadoci) {
        this.becadoci = becadoci;
    }

    public int getMedicamid() {
        return medicamid;
    }

    public void setMedicamid(int medicamid) {
        this.medicamid = medicamid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (becadoci != null ? becadoci.hashCode() : 0);
        hash += (int) medicamid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BecadoMedicamentosPK)) {
            return false;
        }
        BecadoMedicamentosPK other = (BecadoMedicamentosPK) object;
        if ((this.becadoci == null && other.becadoci != null) || (this.becadoci != null && !this.becadoci.equals(other.becadoci))) {
            return false;
        }
        if (this.medicamid != other.medicamid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BecadoMedicamentosPK[ becadoci=" + becadoci + ", medicamid=" + medicamid + " ]";
    }
    
}
