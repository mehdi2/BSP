package bsp.service;

import bsp.model.Godown;
import bsp.repo.GodownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 28-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "GodownService")
public class GodownServiceImpl implements GodownService{
    @Autowired
    private GodownRepository godownRepository;

    @Override
    public List<Godown> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode){
        return godownRepository.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
    }

    @Override
    public List<Godown> findByParentCodeAndIsLog(BigInteger parentCode, boolean isLog){
        return godownRepository.findByParentCodeAndIsLog(parentCode,isLog);
    }

    @Override
    public List<Godown> findByCompanyCodeAndIsLogFalseAndIdNotIn(int companyCode, List<BigInteger> ids){
        return godownRepository.findByCompanyCodeAndIsLogFalseAndIdNotIn(companyCode, ids);
    }

    @Override
    public Godown findByCompanyCodeAndNameAndIsLogFalse(int companyCode, String name){
        return godownRepository.findByCompanyCodeAndNameAndIsLogFalse(companyCode,name);
    }

    //region Default Method
    @Override
    public List<Godown> findAll() {
        return godownRepository.findAll();
    }

    @Override
    public Godown findById(BigInteger id) { return godownRepository.findOne(id); }

    @Override
    public Godown save(Godown godown) { return godownRepository.save(godown); }

    @Override
    public void delete(BigInteger id) {
        godownRepository.delete(id);
    }
    //endregion
}
