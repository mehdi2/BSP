package bsp.repo;

import bsp.model.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Dell on 12/1/2017.
 */
@Repository
public interface VoucherTypeRepository extends JpaRepository<VoucherType, BigInteger> {

    //Accounts Voucher  & Inventory Voucher Top Bar Show Active Voucher Type
    List<VoucherType> findByCompanyCodeAndIsActiveAndIsLogOrderByNameAsc(@Param("companyCode")  int companyCode, @Param("isActive") boolean isActive, @Param("IsLog") boolean isLog);

    //Voucher Type index Show
    List<VoucherType> findByCompanyCodeAndIsLogOrderByNameAsc(@Param("companyCode")  int companyCode, @Param("IsLog") boolean isLog);

    //For Duplicate Check
    VoucherType findByCompanyCodeAndNameAndIsLogFalseOrderByNameAsc(@Param("companyCode")  int companyCode, @Param("name")  String name);

}
