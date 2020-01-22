package bsp.repo;

import bsp.model.Item;
import bsp.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 20-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface ItemRepository  extends JpaRepository<Item, BigInteger> {

    //For Accounts Voucher Purchase Item Name
    List<Item> findByCompanyCodeAndIsLogFalseOrderByNameAsc(@Param("companyCode") int companyCode);

    //For Item List And Service List in index Controller
    List<Item> findByCompanyCodeAndIsServiceAndIsLogOrderByNameAsc(@Param("companyCode") int companyCode, @Param("IsService") boolean isService, @Param("IsLog") boolean isLog);

    //Duplicate Item Or Service Item Check
    Item findByCompanyCodeAndNameAndIsServiceAndIsLogFalse(@Param("companyCode") int companyCode, @Param("Name") String name, @Param("IsService") boolean isService);

}
