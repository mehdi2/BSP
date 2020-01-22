package bsp.repo;

import bsp.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 19-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface UnitRepository  extends JpaRepository<Unit, BigInteger> {
    //For Unit List Company Wise
    List<Unit> findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(@Param("companyCode") int companyCode);

    //For isReplica
    Unit findByCompanyCodeAndUnitSymbolAndIsLogFalse(@Param("companyCode")  int companyCode, @Param("UnitSymbol")  String unitSymbol);

}
