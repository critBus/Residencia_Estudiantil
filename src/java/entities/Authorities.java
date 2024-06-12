/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
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
@Table(name = "authorities" , schema="public")

public class Authorities implements java.io.Serializable {

    
    
    private AuthoritiesPK authoritiesPK;
    private Users users;
    
    public Authorities() {
    }

    public Authorities(AuthoritiesPK id, Users users) {
       this.authoritiesPK = id;
       this.users = users;
    }
    
    @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="username", column=@Column(name="username", nullable=false, length=50) ), 
        @AttributeOverride(name="authority", column=@Column(name="authority", nullable=false, length=50) ) } )
    public AuthoritiesPK getAuthoritiesPK() {
        return authoritiesPK;
    }

    public void setAuthoritiesPK(AuthoritiesPK authoritiesPK) {
        this.authoritiesPK = authoritiesPK;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="username", nullable=false, insertable=false, updatable=false)

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    
}
