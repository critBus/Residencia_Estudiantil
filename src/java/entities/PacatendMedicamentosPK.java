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
public class PacatendMedicamentosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pacatendid")
    private String pacatendid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "medicamid")
    private int medicamid;

    public PacatendMedicamentosPK() {
    }

    public PacatendMedicamentosPK(String pacatendid, int medicamid) {
        this.pacatendid = pacatendid;
        this.medicamid = medicamid;
    }

    public String getPacatendid() {
        return pacatendid;
    }

    public void setPacatendid(String pacatendid) {
        this.pacatendid = pacatendid;
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
        hash += (pacatendid != null ? pacatendid.hashCode() : 0);
        hash += (int) medicamid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PacatendMedicamentosPK)) {
            return false;
        }
        PacatendMedicamentosPK other = (PacatendMedicamentosPK) object;
        if ((this.pacatendid == null && other.pacatendid != null) || (this.pacatendid != null && !this.pacatendid.equals(other.pacatendid))) {
            return false;
        }
        if (this.medicamid != other.medicamid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PacatendMedicamentosPK[ pacatendid=" + pacatendid + ", medicamid=" + medicamid + " ]";
    }
    
}
