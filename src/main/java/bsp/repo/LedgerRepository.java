package bsp.repo;

import bsp.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 29-Nov-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface LedgerRepository  extends JpaRepository<Ledger, BigInteger> {

    //For Ledger List Show
    List<Ledger> findByCompanyCodeAndIsLogFalseOrderByNameAsc(@Param("companyCode")  int companyCode);

    //For isReplica
    Ledger findByCompanyCodeAndNameAndIsLogFalse(@Param("companyCode")  int companyCode, @Param("name")  String name);

    //For Accounts Voucher. when Select Dr. Or Cr. Then Get Value for ledger.
    List<Ledger> findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(@Param("companyCode")  int companyCode, @Param("GroupId") ArrayList<BigInteger> groupId);

}
