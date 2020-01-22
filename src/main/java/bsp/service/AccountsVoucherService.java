package bsp.service;

import bsp.model.AccountsVoucher;
import bsp.model.Ledger;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface AccountsVoucherService {

    List<AccountsVoucher> findByLedgerId(BigInteger ledgerId);

    List<AccountsVoucher> findByVoucherIdInAndDebitAmountIsNullOrderByIdAsc(List<BigInteger> voucherId);

    //region Default Method
    List<AccountsVoucher> findAll();

    AccountsVoucher findById(BigInteger id);

    AccountsVoucher save(AccountsVoucher accountsVoucher);

    void delete(BigInteger id);
    //endregion

}
