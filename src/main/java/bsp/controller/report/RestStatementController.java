package bsp.controller.report;

import bsp.supplemental.Response;
import bsp.model.*;
import bsp.service.AccountsVoucherService;
import bsp.service.CompanyService;
import bsp.service.LedgerService;
import bsp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.*;
import java.util.Locale;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@RestController
@RequestMapping("restStatement")
public class RestStatementController {

    @Autowired
    private AccountsVoucherService accountsVoucherService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private LedgerService ledgerService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private HttpSession bspSession;

    @GetMapping(value = {"/statement/{ledgerId}/{from}/{to}"})
    public Response Statement(@PathVariable BigInteger ledgerId, @PathVariable String from, @PathVariable String to) {
        try {
            OpeningBalance(stringToDate(from),stringToDate(to),ledgerId);
            List<StatementEntity> statementEntities=new ArrayList<StatementEntity>();
            statementEntities.add(OpeningBalanceRow(ledgerId));
            statementEntities.addAll(statementList(ledgerId,stringToDate(from),stringToDate(to)));

            //<editor-fold desc="Send Data">
            Response response = new Response("Done", statementEntities);
            response.setStatus("Done");
            //</editor-fold>
            return response;
        }catch (Exception ex){
            return null;
        }
    }

    private StatementEntity OpeningBalanceRow(BigInteger ledgerId){
        StatementEntity statementEntity = new StatementEntity();
        Ledger ledger = this.ledgerService.findById(ledgerId);
        statementEntity.setDate(ledger.getActionDate().toString());
        statementEntity.setVoucherType("");
        statementEntity.setVoucherNo(new BigInteger("0"));

        statementEntity.setParticulars("Opening Balance");
        if (!ledger.isDebit()) {
            statementEntity.setCreditAmount(ledger.getOpeningBalance() * -1);
            statementEntity.setDebitAmount(0.0);
        }
        else {
            statementEntity.setDebitAmount(ledger.getOpeningBalance());
            statementEntity.setCreditAmount(0.0);
        }
        return statementEntity;
    }

    private List<StatementEntity> statementList(BigInteger ledgerId, Date from, Date to){
        List<StatementEntity> statementEntities=new ArrayList<StatementEntity>();
        List<Voucher> vouchers= voucherService.findByIdInOrderByIdAsc(getVoucherIds(ledgerId));

        for (Voucher voucher: vouchers){//  Timestamp
            try{
                Date dbDate=new SimpleDateFormat("yyyy-MM-dd").parse(voucher.getVoucherDate().toString());
                System.out.println("dbDate: "+dbDate);
//                System.out.println(from.compareTo(dbDate)+" ::########:: "+to.compareTo(dbDate));
                if (from.compareTo(dbDate)==1){
                    continue;
                }
                if (to.compareTo(dbDate)==-1){
                    continue;
                }
            }catch (Exception ex){}



//                System.out.println(voucher.getAccountsVoucher().size());
            if(voucher.getAccountsVoucher().size()==2){
                StatementEntity statementEntity = new StatementEntity();
                voucher.getAccountsVoucher().removeIf(cm->(cm.getLedger().getId().compareTo(ledgerId)==0));
                statementEntity.setDate(new SimpleDateFormat("MMM dd, YYYY").format(voucher.getVoucherDate()));
                statementEntity.setParticularsId(voucher.getAccountsVoucher().get(0).getLedger().getId());
                statementEntity.setParticulars(voucher.getAccountsVoucher().get(0).getLedger().getName());
                statementEntity.setVoucherTypeId(voucher.getVoucherType().getId());
                statementEntity.setVoucherType(voucher.getVoucherType().getName());
                statementEntity.setVoucherNo(voucher.getVoucherNo());
                statementEntity.setDebitAmount(voucher.getAccountsVoucher().get(0).getCreditAmount()==null?0.00:voucher.getAccountsVoucher().get(0).getCreditAmount());
                statementEntity.setCreditAmount(voucher.getAccountsVoucher().get(0).getDebitAmount()==null?0.00:voucher.getAccountsVoucher().get(0).getDebitAmount());
                statementEntity.setBalance(0.00);
                statementEntities.add(statementEntity);
            }
            else{
                StatementEntity statementEntity = new StatementEntity();

                statementEntity.setDate(new SimpleDateFormat("MMM dd, YYYY").format(voucher.getVoucherDate()));
                statementEntity.setParticularsId(new BigInteger("0"));
                statementEntity.setParticulars("As Per Details");
                statementEntity.setVoucherTypeId(voucher.getVoucherType().getId());
                statementEntity.setVoucherType(voucher.getVoucherType().getName());
                statementEntity.setVoucherNo(voucher.getVoucherNo());

                boolean isDebit=false;
                Double total = 0.00;
                for (AccountsVoucher accountsVoucher:voucher.getAccountsVoucher()){
                    if (accountsVoucher.getLedger().getId()==ledgerId && accountsVoucher.getDebitAmount()!=null){
                        isDebit=true;
                    }
                    total+=accountsVoucher.getDebitAmount()==null?0.00:accountsVoucher.getDebitAmount();
                }
                if(!isDebit){
                    statementEntity.setDebitAmount(total);
                    statementEntity.setCreditAmount(0.00);
                }
                else{
                    statementEntity.setDebitAmount(0.00);
                    statementEntity.setCreditAmount(total);
                }

                statementEntity.setBalance(0.00);
                statementEntities.add(statementEntity);
            }

        }
        return statementEntities;
    }

    //Done
    private Date stringToDate(String pDate){
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(pDate);
        }catch (Exception ex){return new Date();}
    }

    //Done
    private List<BigInteger> getVoucherIds(BigInteger ledgerId){
        List<AccountsVoucher> accountsVouchers = accountsVoucherService.findByLedgerId(ledgerId);
        ArrayList<BigInteger> voucherIds=new ArrayList<BigInteger>();
        for (AccountsVoucher accountsVoucher:accountsVouchers){
            voucherIds.add(accountsVoucher.getVoucher().getId());
        }
        return voucherIds;
    }

    private Double OpeningBalance(Date sDate, Date eDate, BigInteger ledgerId){

//        Opening Balance+Sum of Debit-Sum of Credit
        Company company = companyService.findById(278);
        Double openingBalance= this.ledgerService.findById(ledgerId).getOpeningBalance();
        Double debitAmount=0.00;
        Double creditAmount=0.00;
//        java.sql.Date date=new java.sql.Date("08/08/2018");
        List<Voucher> vouchers = voucherService.findByVoucherDateBetween(company.getAccountsStartOn(),sDate);
        System.out.println(vouchers.size());
//        List<Voucher> vouchers = voucherService.findByIdInOrderByIdAsc(getVoucherIds(ledgerId));
        for (Voucher voucher:vouchers){
            for (AccountsVoucher accountsVoucher:voucher.getAccountsVoucher()){
                System.out.println("------------------------------>"+accountsVoucher.getLedger().getName()+"---->"+accountsVoucher.getDebitAmount()+"--->"+accountsVoucher.getCreditAmount());
                if(accountsVoucher.getId()==ledgerId){
                    debitAmount+=accountsVoucher.getDebitAmount();
                    creditAmount+=accountsVoucher.getCreditAmount();
                }
            }
        }
        Double ClosingBalance = openingBalance+debitAmount-creditAmount;
        return ClosingBalance;
    }
}
