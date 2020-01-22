package bsp.service;

import bsp.model.CostCenter;
import bsp.model.Godown;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 25-Jun-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface CostCenterService {

    List<CostCenter> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode);

    CostCenter findByCompanyCodeAndNameAndIsLogFalse(int companyCode, String name);

    List<CostCenter> findByParentCodeAndIsLog(BigInteger parentCode, boolean isLog);

    List<CostCenter> findByCompanyCodeAndIsLogFalseAndIdNotIn(int companyCode, List<BigInteger> ids);

    //region Default Method
    List<CostCenter> findAll();

    CostCenter findById(BigInteger id);

    CostCenter save(CostCenter costCenter);

    void delete(BigInteger id);
    //endregion
}
