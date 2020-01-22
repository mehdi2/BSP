package bsp.service;

import bsp.model.Ledger;
import bsp.repo.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 29-Nov-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "LedgerService")
public class LedgerServiceImpl implements LedgerService{
    @Autowired
    private LedgerRepository ledgerRepository;

    @Override
    public List<Ledger> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode){
        return ledgerRepository.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
    }

    @Override
    public Ledger findByCompanyCodeAndNameAndIsLogFalse(int companyCode, String name){
        return ledgerRepository.findByCompanyCodeAndNameAndIsLogFalse(companyCode,name);
    }

    @Override
    public List<Ledger> findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(int companyCode, ArrayList<BigInteger> groupId){
        return ledgerRepository.findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(companyCode,groupId);
    }

    //region Default Method
    @Override
    public List<Ledger> findAll() {
        return ledgerRepository.findAll();
    }

    @Override
    public Ledger findById(BigInteger id) {
        return ledgerRepository.findOne(id);
    }

    @Override
    public Ledger save(Ledger generalCodeFile) {
        return ledgerRepository.save(generalCodeFile);
    }

    @Override
    public void delete(BigInteger id) {
        ledgerRepository.delete(id);
    }
    //endregion



}
