package bsp.repo;

import bsp.model.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 25-Jun-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface CostCenterRepository  extends JpaRepository<CostCenter, BigInteger> {

    //For CostCenter List Company Wise
    List<CostCenter> findByCompanyCodeAndIsLogFalseOrderByNameAsc(@Param("companyCode") int companyCode);

    //For isReplica
    CostCenter findByCompanyCodeAndNameAndIsLogFalse(@Param("companyCode")  int companyCode, @Param("name")  String name);

    //Accounts CostCenter Controller->RemoveFunc(),
    List<CostCenter> findByParentCodeAndIsLog(@Param("parentCode") BigInteger parentCode, @Param("IsLog") boolean isLog);

    //Get All CostCenter Without ID List
    List<CostCenter> findByCompanyCodeAndIsLogFalseAndIdNotIn(@Param("companyCode") int companyCode, @Param("id") List<BigInteger> id);
}
