package bsp.repo;

import bsp.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AuthUserRepository extends JpaRepository<AuthUser, BigInteger> {

    AuthUser findByUsername(String username);
}
