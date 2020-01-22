package bsp.repo;

import bsp.model.AccountsVoucher;
import bsp.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface AccountsVoucherRepository  extends JpaRepository<AccountsVoucher, BigInteger> {

    List<AccountsVoucher> findByLedgerId(@Param("LedgerId") BigInteger LedgerId);

    List<AccountsVoucher> findByVoucherIdInAndDebitAmountIsNullOrderByIdAsc(@Param("VoucherId") List<BigInteger> voucherId);

}
