/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "aspect_evalcuartel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AspectEvalcuartel.findAll", query = "SELECT a FROM AspectEvalcuartel a")
    , @NamedQuery(name = "AspectEvalcuartel.findById", query = "SELECT a FROM AspectEvalcuartel a WHERE a.id = :id")
    , @NamedQuery(name = "AspectEvalcuartel.findByName", query = "SELECT a FROM AspectEvalcuartel a WHERE a.name = :name")
    , @NamedQuery(name = "AspectEvalcuartel.findByMaxvalue", query = "SELECT a FROM AspectEvalcuartel a WHERE a.maxvalue = :maxvalue")})
public class AspectEvalcuartel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxvalue")
    private int maxvalue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aspectEvalcuartel", fetch = FetchType.LAZY)
    private List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelList;

    public AspectEvalcuartel() {
    }

    public AspectEvalcuartel(Integer id) {
        this.id = id;
    }

    public AspectEvalcuartel(Integer id, String name, int maxvalue) {
        this.id = id;
        this.name = name;
        this.maxvalue = maxvalue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    @XmlTransient
    public List<EvalcuarteleriaAspectevalcuartel> getEvalcuarteleriaAspectevalcuartelList() {
        return evalcuarteleriaAspectevalcuartelList;
    }

    public void setEvalcuarteleriaAspectevalcuartelList(List<EvalcuarteleriaAspectevalcuartel> evalcuarteleriaAspectevalcuartelList) {
        this.evalcuarteleriaAspectevalcuartelList = evalcuarteleriaAspectevalcuartelList;
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
        if (!(object instanceof AspectEvalcuartel)) {
            return false;
        }
        AspectEvalcuartel other = (AspectEvalcuartel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AspectEvalcuartel[ id=" + id + " ]";
    }
    
}
