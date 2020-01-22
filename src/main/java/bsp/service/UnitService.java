package bsp.service;

import bsp.model.Unit;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 19-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface UnitService {
    List<Unit> findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(int companyCode);

    Unit findByCompanyCodeAndUnitSymbolAndIsLogFalse(int companyCode, String unitSymbol);

    //region Default Method
    List<Unit> findAll();

    Unit findById(BigInteger id);

    Unit save(Unit unit);

    void delete(BigInteger pid);
    //endregion
}
