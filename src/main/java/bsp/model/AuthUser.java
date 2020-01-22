package bsp.model;

//import com.oith.annotation.MacImagable;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "AUTH_USER")
public class AuthUser   {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @NotEmpty
    @Column(name = "USERNAME", length = 30, unique = true, nullable = false)
    private String username;

    @NotEmpty
    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "email", length = 30, unique = true)
    private String email;

    @NotEmpty
    @Column(name = "FULL_NAME", length = 50, nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 30, nullable = false)
    private Gender gender;

    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled = Boolean.TRUE;

    @Column(name = "ACCOUNT_EXPIRED", nullable = false)
    private Boolean accountExpired = Boolean.FALSE;

    @Column(name = "ACCOUNT_LOCKED", nullable = false)
    private Boolean accountLocked = Boolean.FALSE;

    @Column(name = "CREDENTIALS_EXPIRED", nullable = false)
    private Boolean credentialsExpired = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AUTH_USER_AUTH_ROLE",
            joinColumns = {
                    @JoinColumn(name = "AUTH_USER_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "AUTH_ROLE_ID")})
    private Set<AuthRole> authRoles = new LinkedHashSet<>();

    public AuthUser() {
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new LinkedHashSet<>();
        for (AuthRole role : authRoles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
        }
        return grantedAuthorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AuthRole> getAuthRoles() {
        return authRoles;
    }

    public void setAuthRoles(Set<AuthRole> authRoles) {
        this.authRoles = authRoles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return username;
    }
}
