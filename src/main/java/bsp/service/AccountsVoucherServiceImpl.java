package bsp.service;

import bsp.model.AccountsVoucher;
import bsp.model.Ledger;
import bsp.repo.AccountsVoucherRepository;
import bsp.repo.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "AccountsVoucherService")
public class AccountsVoucherServiceImpl implements AccountsVoucherService{
    @Autowired
    private AccountsVoucherRepository accountsVoucherRepository;

    @Override
    public List<AccountsVoucher> findByLedgerId(BigInteger ledgerId){
        return accountsVoucherRepository.findByLedgerId(ledgerId);
    }

    @Override
    public List<AccountsVoucher> findByVoucherIdInAndDebitAmountIsNullOrderByIdAsc(List<BigInteger> voucherId){
        return accountsVoucherRepository.findByVoucherIdInAndDebitAmountIsNullOrderByIdAsc(voucherId);
    }

    //region Default Method
    @Override
    public List<AccountsVoucher> findAll() {
        return accountsVoucherRepository.findAll();
    }

    @Override
    public AccountsVoucher findById(BigInteger id) {
        return accountsVoucherRepository.findOne(id);
    }

    @Override
    public AccountsVoucher save(AccountsVoucher accountsVoucher) {
        return accountsVoucherRepository.save(accountsVoucher);
    }

    @Override
    public void delete(BigInteger id) {
        accountsVoucherRepository.delete(id);
    }
    //endregion



}
