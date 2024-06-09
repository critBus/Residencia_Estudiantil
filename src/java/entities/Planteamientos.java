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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "planteamientos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planteamientos.findAll", query = "SELECT p FROM Planteamientos p")
    , @NamedQuery(name = "Planteamientos.findById", query = "SELECT p FROM Planteamientos p WHERE p.id = :id")
    , @NamedQuery(name = "Planteamientos.findByFecha", query = "SELECT p FROM Planteamientos p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Planteamientos.findByPlanteamiento", query = "SELECT p FROM Planteamientos p WHERE p.planteamiento = :planteamiento")
    , @NamedQuery(name = "Planteamientos.findByRespuesta", query = "SELECT p FROM Planteamientos p WHERE p.respuesta = :respuesta")
    , @NamedQuery(name = "Planteamientos.findByEstado", query = "SELECT p FROM Planteamientos p WHERE p.estado = :estado")
    , @NamedQuery(name = "Planteamientos.findByAsunto", query = "SELECT p FROM Planteamientos p WHERE p.asunto = :asunto")
    , @NamedQuery(name = "Planteamientos.findByDescripcion", query = "SELECT p FROM Planteamientos p WHERE p.descripcion = :descripcion")})
public class Planteamientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "planteamiento")
    private String planteamiento;
    @Size(max = 1024)
    @Column(name = "respuesta")
    private String respuesta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "becadoci", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Becado becadoci;
    @JoinColumn(name = "trabajadorci", referencedColumnName = "ci")
    @ManyToOne(fetch = FetchType.LAZY)
    private Trabajador trabajadorci;

    public Planteamientos() {
    }

    public Planteamientos(String id) {
        this.id = id;
    }

    public Planteamientos(String id, Date fecha, String planteamiento, String respuesta, String estado, String asunto, String descripcion, Trabajador trabajador, Becado becado) {
        this.id = id;
        this.fecha = fecha;
        this.planteamiento = planteamiento;
        this.respuesta = respuesta;
        this.estado = estado;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.trabajadorci = trabajador;
        this.becadoci = becado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPlanteamiento() {
        return planteamiento;
    }

    public void setPlanteamiento(String planteamiento) {
        this.planteamiento = planteamiento;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Becado getBecadoci() {
        return becadoci;
    }

    public void setBecadoci(Becado becadoci) {
        this.becadoci = becadoci;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planteamientos)) {
            return false;
        }
        Planteamientos other = (Planteamientos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Planteamientos[ id=" + id + " ]";
    }
    
}
