package bsp.service;

import bsp.model.VoucherType;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 01-Jan-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */


public interface VoucherTypeService {

    List<VoucherType> findByCompanyCodeAndIsActiveAndIsLogOrderByNameAsc(int companyCode, boolean isActive, boolean isLog);

    List<VoucherType> findByCompanyCodeAndIsLogOrderByNameAsc(int companyCode, boolean isLog);

    VoucherType findByCompanyCodeAndNameAndIsLogFalseOrderByNameAsc(int companyCode, String name);

    //region Default Method
    List<VoucherType> findAll();

    VoucherType findById(BigInteger id);

    VoucherType save(VoucherType voucherType);

    void delete(BigInteger id);
    //endregion
}
