/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Euclides
 */
@Entity
@Table(name = "authorities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authorities.findAll", query = "SELECT a FROM Authorities a")
    , @NamedQuery(name = "Authorities.findByUser", query = "SELECT a FROM Authorities a WHERE a.authoritiesPK.user = :user")
    , @NamedQuery(name = "Authorities.findByAuthority", query = "SELECT a FROM Authorities a WHERE a.authoritiesPK.authority = :authority")})
public class Authorities implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthoritiesPK authoritiesPK;
    @JoinColumn(name = "user", referencedColumnName = "user", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user1;
    
    private boolean isSelected;
    
    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public Authorities() {
    }

    public Authorities(AuthoritiesPK authoritiesPK) {
        this.authoritiesPK = authoritiesPK;
    }

    public Authorities(AuthoritiesPK id, User user) {
       this.authoritiesPK = id;
       this.user1 = user;
    }

    public AuthoritiesPK getAuthoritiesPK() {
        return authoritiesPK;
    }

    public void setAuthoritiesPK(AuthoritiesPK authoritiesPK) {
        this.authoritiesPK = authoritiesPK;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authoritiesPK != null ? authoritiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authorities)) {
            return false;
        }
        Authorities other = (Authorities) object;
        if ((this.authoritiesPK == null && other.authoritiesPK != null) || (this.authoritiesPK != null && !this.authoritiesPK.equals(other.authoritiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Authorities[ authoritiesPK=" + authoritiesPK + " ]";
    }
    
}
