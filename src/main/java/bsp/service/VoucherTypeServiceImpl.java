package bsp.service;

import bsp.model.VoucherType;
import bsp.repo.VoucherTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 01-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "VoucherTypeService")
public class VoucherTypeServiceImpl implements VoucherTypeService {

    @Autowired
    private VoucherTypeRepository voucherTypeRepository;

    @Override
    public List<VoucherType> findByCompanyCodeAndIsActiveAndIsLogOrderByNameAsc(int companyCode, boolean isActive, boolean isLog){
        return voucherTypeRepository.findByCompanyCodeAndIsActiveAndIsLogOrderByNameAsc(companyCode, isActive, isLog);
    }

    @Override
    public List<VoucherType> findByCompanyCodeAndIsLogOrderByNameAsc(int companyCode, boolean isLog){
        return voucherTypeRepository.findByCompanyCodeAndIsLogOrderByNameAsc(companyCode, isLog);
    }

    @Override
    public VoucherType findByCompanyCodeAndNameAndIsLogFalseOrderByNameAsc(int companyCode, String name){
        return voucherTypeRepository.findByCompanyCodeAndNameAndIsLogFalseOrderByNameAsc(companyCode, name);
    }

    //region Default Method
    @Override
    public List<VoucherType> findAll() {
        return voucherTypeRepository.findAll();
    }

    @Override
    public VoucherType findById(BigInteger id) {
        return voucherTypeRepository.findOne(id);
    }

    @Override
    public VoucherType save(VoucherType voucherType) {
        return voucherTypeRepository.save(voucherType);
    }

    @Override
    public void delete(BigInteger id) {
        voucherTypeRepository.delete(id);
    }
    //endregion


}
