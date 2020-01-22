package bsp.service;

import bsp.model.Ledger;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 29-Nov-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface LedgerService {

    List<Ledger> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode);

    Ledger findByCompanyCodeAndNameAndIsLogFalse(int companyCode, String name);

    List<Ledger> findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(int companyCode, ArrayList<BigInteger> groupId);

    //region Default Method
    List<Ledger> findAll();

    Ledger findById(BigInteger id);

    Ledger save(Ledger accountsLedger);

    void delete(BigInteger pid);
    //endregion

}
