package bsp.service;

import bsp.model.Godown;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 28-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface GodownService {

    List<Godown> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode);

    List<Godown> findByParentCodeAndIsLog(BigInteger parentCode, boolean isLog);

    List<Godown> findByCompanyCodeAndIsLogFalseAndIdNotIn(int companyCode, List<BigInteger> ids);

    Godown findByCompanyCodeAndNameAndIsLogFalse(int companyCode, String name);

    //region Default Method
    List<Godown> findAll();

    Godown findById(BigInteger id);

    Godown save(Godown godown);

    void delete(BigInteger id);
    //endregion
}
