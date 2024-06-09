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
public class EvalcuarteleriaAspectevalcuartelPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "becadoci")
    private String becadoci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aspect_evalcuartelid")
    private int aspectEvalcuartelid;

    public EvalcuarteleriaAspectevalcuartelPK() {
    }

    public EvalcuarteleriaAspectevalcuartelPK(Date fecha, int aspevalcuartelid, String becadoci) {
        this.fecha = fecha;
        this.aspectEvalcuartelid = aspevalcuartelid;
        this.becadoci = becadoci;
    }

   public EvalcuarteleriaAspectevalcuartelPK(Date fecha, String becadoci) {
        this.fecha = fecha;
        this.becadoci = becadoci;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBecadoci() {
        return becadoci;
    }

    public void setBecadoci(String becadoci) {
        this.becadoci = becadoci;
    }

    public int getAspectEvalcuartelid() {
        return aspectEvalcuartelid;
    }

    public void setAspectEvalcuartelid(int aspectEvalcuartelid) {
        this.aspectEvalcuartelid = aspectEvalcuartelid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (becadoci != null ? becadoci.hashCode() : 0);
        hash += (int) aspectEvalcuartelid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalcuarteleriaAspectevalcuartelPK)) {
            return false;
        }
        EvalcuarteleriaAspectevalcuartelPK other = (EvalcuarteleriaAspectevalcuartelPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.becadoci == null && other.becadoci != null) || (this.becadoci != null && !this.becadoci.equals(other.becadoci))) {
            return false;
        }
        if (this.aspectEvalcuartelid != other.aspectEvalcuartelid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EvalcuarteleriaAspectevalcuartelPK[ fecha=" + fecha + ", becadoci=" + becadoci + ", aspectEvalcuartelid=" + aspectEvalcuartelid + " ]";
    }
    
}
