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
@Table(name = "aspect_evalcuarto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AspectEvalcuarto.findAll", query = "SELECT a FROM AspectEvalcuarto a")
    , @NamedQuery(name = "AspectEvalcuarto.findById", query = "SELECT a FROM AspectEvalcuarto a WHERE a.id = :id")
    , @NamedQuery(name = "AspectEvalcuarto.findByName", query = "SELECT a FROM AspectEvalcuarto a WHERE a.name = :name")
    , @NamedQuery(name = "AspectEvalcuarto.findByMaxvalue", query = "SELECT a FROM AspectEvalcuarto a WHERE a.maxvalue = :maxvalue")})
public class AspectEvalcuarto implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aspectEvalcuarto", fetch = FetchType.LAZY)
    private List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoList;

    public AspectEvalcuarto() {
    }

    public AspectEvalcuarto(Integer id) {
        this.id = id;
    }

    public AspectEvalcuarto(Integer id, String name, int maxvalue) {
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
    public List<AspectEvalcuartoEvalcuarto> getAspectEvalcuartoEvalcuartoList() {
        return aspectEvalcuartoEvalcuartoList;
    }

    public void setAspectEvalcuartoEvalcuartoList(List<AspectEvalcuartoEvalcuarto> aspectEvalcuartoEvalcuartoList) {
        this.aspectEvalcuartoEvalcuartoList = aspectEvalcuartoEvalcuartoList;
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
        if (!(object instanceof AspectEvalcuarto)) {
            return false;
        }
        AspectEvalcuarto other = (AspectEvalcuarto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AspectEvalcuarto[ id=" + id + " ]";
    }
    
}
