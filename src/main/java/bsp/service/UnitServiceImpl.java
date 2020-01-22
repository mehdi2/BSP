package bsp.service;

import bsp.model.Unit;
import bsp.repo.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 19-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "UnitService")
public class UnitServiceImpl implements UnitService{
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public List<Unit> findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(int companyCode){
        return unitRepository.findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(companyCode);
    }

    @Override
    public Unit findByCompanyCodeAndUnitSymbolAndIsLogFalse(int companyCode, String unitSymbol){
        return unitRepository.findByCompanyCodeAndUnitSymbolAndIsLogFalse(companyCode,unitSymbol);
    }

    //region Default Method
    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public Unit findById(BigInteger id) { return unitRepository.findOne(id); }

    @Override
    public Unit save(Unit unit) { return unitRepository.save(unit); }

    @Override
    public void delete(BigInteger id) {
        unitRepository.delete(id);
    }
    //endregion
}
