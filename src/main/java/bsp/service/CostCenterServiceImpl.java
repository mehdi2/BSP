package bsp.service;

import bsp.model.CostCenter;
import bsp.model.Godown;
import bsp.repo.CostCenterRepository;
import bsp.repo.GodownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 25-Jun-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "CostCenterService")
public class CostCenterServiceImpl implements CostCenterService{
    @Autowired
    private CostCenterRepository costCenterRepository;

    @Override
    public List<CostCenter> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode){
        return costCenterRepository.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
    }

    @Override
    public CostCenter findByCompanyCodeAndNameAndIsLogFalse(int companyCode, String name){
        return costCenterRepository.findByCompanyCodeAndNameAndIsLogFalse(companyCode,name);
    }

    @Override
    public List<CostCenter> findByParentCodeAndIsLog(BigInteger parentCode, boolean isLog){
        return costCenterRepository.findByParentCodeAndIsLog(parentCode,isLog);
    }

    @Override
    public List<CostCenter> findByCompanyCodeAndIsLogFalseAndIdNotIn(int companyCode, List<BigInteger> ids){
        return costCenterRepository.findByCompanyCodeAndIsLogFalseAndIdNotIn(companyCode, ids);
    }

    //region Default Method
    @Override
    public List<CostCenter> findAll() {
        return costCenterRepository.findAll();
    }

    @Override
    public CostCenter findById(BigInteger id) { return costCenterRepository.findOne(id); }

    @Override
    public CostCenter save(CostCenter costCenter) { return costCenterRepository.save(costCenter); }

    @Override
    public void delete(BigInteger id) {
        costCenterRepository.delete(id);
    }
    //endregion
}
