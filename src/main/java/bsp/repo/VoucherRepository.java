package bsp.repo;

import bsp.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 23-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface VoucherRepository  extends JpaRepository<Voucher, BigInteger> {

//      @Query(value="select v from Voucher v where v.voucherNo = ?1 limit 1", nativeQuery=true)
      Voucher findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(@Param("companyCode") int CompanyCode, @Param("VoucherTypeId") BigInteger voucherTypeId);

      List<Voucher> findByIdInOrderByIdAsc(@Param("Id") List<BigInteger> voucherId);

//      List<Voucher> findByVoucherDateBeforeIdInOrderByIdAsc(@Param("VoucherDate") Timestamp voucherDate, @Param("Id") List<BigInteger> voucherId);

      List<Voucher> findByVoucherDateBetween(@Param("VoucherDate") Date sDate, @Param("VoucherDate") Date eDate);
}
