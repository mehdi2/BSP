package bsp.service;

import bsp.model.Voucher;
import org.springframework.data.repository.query.Param;

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

public interface VoucherService {

    Voucher findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(@Param("companyCode") int CompanyCode, @Param("VoucherTypeId") BigInteger voucherTypeId);

    List<Voucher> findByIdInOrderByIdAsc(@Param("Id") List<BigInteger> voucherId);

//    List<Voucher> findByVoucherDateBeforeIdInOrderByIdAsc(@Param("VoucherDate") Timestamp VoucherDate, @Param("Id") List<BigInteger> voucherId);

    List<Voucher> findByVoucherDateBetween(@Param("VoucherDate") Date sDate, @Param("VoucherDate") Date eDate);

    //region Default Method
    List<Voucher> findAll();

    Voucher findById(BigInteger id);

    Voucher save(Voucher voucher);

    void delete(BigInteger id);
    //endregion


}
