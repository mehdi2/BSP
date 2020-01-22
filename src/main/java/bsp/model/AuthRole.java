package bsp.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUTH_ROLE")
public class AuthRole  {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Pattern(regexp = "")
    @Column(name = "AUTHORITY", length = 30, unique = true)
    private String authority;

    @ManyToMany(mappedBy = "authRoles")
    private Set<AuthUser> authUsers = new HashSet();

    public AuthRole() {
    }

    public AuthRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(Set<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }

    @Override
    public String toString() {
        return authority;
    }

}
