package bsp.service;

import bsp.model.AuthUser;
import bsp.repo.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

@Service(value = "authUserService")

public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public List<AuthUser> findAll() {
        return authUserRepository.findAll();
    }

    @Override
    public AuthUser findById(BigInteger id) {
        return authUserRepository.findOne(id);
    }

    @Override
    public AuthUser save(AuthUser authUser) {
        return authUserRepository.save(authUser);
    }

    @Override
    public void delete(BigInteger id) {
        authUserRepository.delete(id);
    }

    @Override
    public AuthUser findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(">>>>>>>User Name: "+username);

        AuthUser authUser = authUserRepository.findByUsername(username);

        System.out.println(">>>>>>>After User Name: "+username);

        if (authUser == null)
            throw new UsernameNotFoundException("auth.badCredentials.label");

        if (!authUser.getEnabled())
            throw new RuntimeException("auth.account.disabled.label");

        if (authUser.getAccountLocked())
            throw new RuntimeException("auth.accountLocked.label");

        if (authUser.getCredentialsExpired())
            throw new RuntimeException("auth.credentialsExpired.label");

        if (authUser.getAccountExpired())
            throw new RuntimeException("auth.accountExpired.label");

//        AuthUserExt

//        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return new User(
//                authUser.getUsername(),
//                authUser.getPassword(),
//                grantedAuthorities
//        );


        return new AuthUserExt(authUser);
//        return new AuthUserExt(authUser);
    }

}
