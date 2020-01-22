package bsp.controller.report;

import bsp.model.*;
import bsp.service.LedgerService;
import bsp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("report")
public class ReportController {
    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private VoucherService voucherService;

    @GetMapping(value = "/trialBalanceGetAllLedger")
    public String TrialBalance(Model model) {
        List<TrialBalance> trialBalances = new ArrayList<>();
        model.addAttribute("Ledgers", trialBalances);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        model.addAttribute("fromDate",timestamp);
        model.addAttribute("toDate",timestamp);
//        model.addAttribute("ReportType","trialBalanceGetAllLedger");
        return "report/trialBalanceGetAllLedger";
    }

    @GetMapping(value = "/statement")
    public String LedgerVoucher(Model model) {
        List<Ledger> ledgers = ledgerService.findAll();
        model.addAttribute("ledgers", ledgers);
        return "report/statement";
    }

    @GetMapping(value = "/daySummery")
    public String DaySummery(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("Vouchers", vouchers);
        return "report/daySummery";
    }

}
