package bsp.repo;

import bsp.model.AuthRequestMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;


public interface AuthRequestMapRepository extends JpaRepository<AuthRequestMap, BigInteger> {

    //@Query("SELECT u FROM InvProduct u WHERE u.nid LIKE %?1% OR u.mobile LIKE %?2% ")
    //Page<InvProduct> findAllByNidLikeAndMobLike(String nid, String mob, Pageable pageable);
}
