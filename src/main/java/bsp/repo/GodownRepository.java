package bsp.repo;

import bsp.model.Godown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 28-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface GodownRepository  extends JpaRepository<Godown, BigInteger> {

    //Get All Godown without isLog == True.
    List<Godown> findByCompanyCodeAndIsLogFalseOrderByNameAsc(@Param("companyCode") int companyCode);

    //Inventory Godown Controller->RemoveFunc(),
    List<Godown> findByParentCodeAndIsLog(@Param("parentCode") BigInteger parentCode, @Param("IsLog") boolean isLog);

    //Get All CostCenter Without ID List
    List<Godown> findByCompanyCodeAndIsLogFalseAndIdNotIn(@Param("companyCode") int companyCode, @Param("id") List<BigInteger> id);

    //For isReplica
    Godown findByCompanyCodeAndNameAndIsLogFalse(@Param("companyCode")  int companyCode, @Param("name")  String name);


}
