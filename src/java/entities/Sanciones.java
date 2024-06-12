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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "sanciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sanciones.findAll", query = "SELECT s FROM Sanciones s")
    , @NamedQuery(name = "Sanciones.findByFecha", query = "SELECT s FROM Sanciones s WHERE s.sancionesPK.fecha = :fecha")
    , @NamedQuery(name = "Sanciones.findByBecadoci", query = "SELECT s FROM Sanciones s WHERE s.sancionesPK.becadoci = :becadoci")
    , @NamedQuery(name = "Sanciones.findBySancion", query = "SELECT s FROM Sanciones s WHERE s.sancion = :sancion")
    , @NamedQuery(name = "Sanciones.findByTiempo", query = "SELECT s FROM Sanciones s WHERE s.tiempo = :tiempo")
    , @NamedQuery(name = "Sanciones.findByEstado", query = "SELECT s FROM Sanciones s WHERE s.estado = :estado")
    , @NamedQuery(name = "Sanciones.findByDescripcion", query = "SELECT s FROM Sanciones s WHERE s.descripcion = :descripcion")})
public class Sanciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SancionesPK sancionesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "sancion")
    private String sancion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tiempo")
    private String tiempo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Becado becado;
    @JoinColumns({
        @JoinColumn(name = "incisoid", referencedColumnName = "id")
        , @JoinColumn(name = "articuloid", referencedColumnName = "articuloid")
        , @JoinColumn(name = "capituloid", referencedColumnName = "capituloid")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Incisoreglam incisoreglam;

    public Sanciones() {
    }

    public Sanciones(SancionesPK sancionesPK) {
        this.sancionesPK = sancionesPK;
    }

    public Sanciones(SancionesPK sancionesPK, String sancion, String tiempo, String estado, String descripcion, Incisoreglam incisoreglam) {
        this.sancionesPK = sancionesPK;
        this.sancion = sancion;
        this.tiempo = tiempo;
        this.estado = estado;
        this.descripcion = descripcion;
        this.incisoreglam = incisoreglam;
    }

    public Sanciones(Date fecha, String becadoci) {
        this.sancionesPK = new SancionesPK(fecha, becadoci);
    }

    public SancionesPK getSancionesPK() {
        return sancionesPK;
    }

    public void setSancionesPK(SancionesPK sancionesPK) {
        this.sancionesPK = sancionesPK;
    }

    public String getSancion() {
        return sancion;
    }

    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Becado getBecado() {
        return becado;
    }

    public void setBecado(Becado becado) {
        this.becado = becado;
    }

    public Incisoreglam getIncisoreglam() {
        return incisoreglam;
    }

    public void setIncisoreglam(Incisoreglam incisoreglam) {
        this.incisoreglam = incisoreglam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sancionesPK != null ? sancionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sanciones)) {
            return false;
        }
        Sanciones other = (Sanciones) object;
        if ((this.sancionesPK == null && other.sancionesPK != null) || (this.sancionesPK != null && !this.sancionesPK.equals(other.sancionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sanciones[ sancionesPK=" + sancionesPK + " ]";
    }
    
}
