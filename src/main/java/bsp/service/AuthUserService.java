package bsp.service;

import bsp.model.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigInteger;
import java.util.List;

public interface AuthUserService extends UserDetailsService {
    List<AuthUser> findAll();

    AuthUser findById(BigInteger id);

    AuthUser save(AuthUser authUser);

    void delete(BigInteger pid);

    AuthUser findByUsername(String username);
}
