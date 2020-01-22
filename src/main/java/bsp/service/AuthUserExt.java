package bsp.service;

import bsp.model.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthUserExt implements UserDetails {

    private AuthUser authUser;

    public AuthUserExt(AuthUser authUser) {
        this.authUser = authUser;
        System.out.println("ooooooooooo: " + authUser.getUsername());
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authUser.getGrantedAuthorities();
    }

    @Override
    public boolean isEnabled() {
        return authUser.getEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !authUser.getAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !authUser.getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !authUser.getCredentialsExpired();
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
