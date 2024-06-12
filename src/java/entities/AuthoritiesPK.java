/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Euclides
 */
@Embeddable
public class AuthoritiesPK implements Serializable {

    
    private String username;
    private String authority;

    public AuthoritiesPK() {
    }

    public AuthoritiesPK(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
    
    @Column(name="username", nullable=false, length=50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="authority", nullable=false, length=50)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AuthoritiesPK) ) return false;
		 AuthoritiesPK castOther = ( AuthoritiesPK ) other; 
         
		 return ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getAuthority()==castOther.getAuthority()) || ( this.getAuthority()!=null && castOther.getAuthority()!=null && this.getAuthority().equals(castOther.getAuthority()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getAuthority() == null ? 0 : this.getAuthority().hashCode() );
         return result;
   }   
    
}
