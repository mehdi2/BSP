package bsp.controller.transactions;

import bsp.supplemental.Response;
import bsp.model.CostCenter;
import bsp.model.Ledger;
import bsp.model.VoucherType;
import bsp.service.*;
import bsp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 26-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@RestController
@RequestMapping("accountingVouchers")
public class RestAccountingVouchersController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherTypeService voucherTypeService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CostCenterService costCenterService;

    @GetMapping(value = {"/ledger/{voucherType}/{drCr}"})
    public Response getLedger(@PathVariable BigInteger voucherType, @PathVariable int drCr) {
        try {
//            List<voucherType> voucherTypes = voucherService.findById(1,voucherType,drCr==1?true:false);
            VoucherType voucherTypes = voucherTypeService.findById(voucherType);

            List<Ledger> accountsLedgers = ledgerService.findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(278, drCr == 1 ? voucherTypes.getDebitGroupCodes() : voucherTypes.getCreditGroupCodes()); //Here Company Code is 1. It will be dynamic

//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Request ledger size: "+accountsLedgers.size());
//            int i=0;
//            for (Ledger ledger: accountsLedgers) {
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>Group ID: "+ledger.getGroup().getId());
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>Group Name: "+ledger.getGroup().getName());
//                accountsLedgers.forEach(object->object.setGroup(this.groupService.findById(new BigInteger("811"))));
////                ledger.setLedgerName(ledger.getLedgerName()+" ( Opening Balance: "+ledger.getOpeningBalance()+" )");
////                System.out.println(ledger.getLedgerName());
//            }
            Response response = new Response("Done", accountsLedgers);
            response.setData(accountsLedgers);
            response.setStatus("Done");
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = {"/costCenter/"})
    public Response getCostCenterList() {
        try {
            List<CostCenter> costCenterList = costCenterService.findAll(); //Here Company Code is 1. It will be dynamic
            Response response = new Response("Done", costCenterList);
            response.setData(costCenterList);
            response.setStatus("Done");
            return response;
        } catch (Exception e) {
            return null;
        }
    }



//    @GetMapping(value = {"/costCenter/{voucherType}/{drCr}"})
//    public Response getLedger(@PathVariable BigInteger voucherType, @PathVariable int drCr) {
//        try {
////            List<voucherType> voucherTypes = voucherService.findById(1,voucherType,drCr==1?true:false);
//            VoucherType voucherTypes = voucherTypeService.findById(voucherType);
//            List<Ledger> accountsLedgers = ledgerService.findByIsActiveAndCompanyCodeAndGroupIdIn(true, 1, drCr == 1 ? voucherTypes.getDebitGroupCodes() : voucherTypes.getCreditGroupCodes()); //Here Company Code is 1. It will be dynamic
////            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Request ledger size: "+accountsLedgers.size());
////            int i=0;
////            for (Ledger ledger: accountsLedgers) {
////                System.out.println(">>>>>>>>>>>>>>>>>>>>>Group ID: "+ledger.getGroup().getId());
////                System.out.println(">>>>>>>>>>>>>>>>>>>>>Group Name: "+ledger.getGroup().getName());
////                accountsLedgers.forEach(object->object.setGroup(this.groupService.findById(new BigInteger("811"))));
//////                ledger.setLedgerName(ledger.getLedgerName()+" ( Opening Balance: "+ledger.getOpeningBalance()+" )");
//////                System.out.println(ledger.getLedgerName());
////            }
//            Response response = new Response("Done", accountsLedgers);
//            response.setData(accountsLedgers);
//            response.setStatus("Done");
//            return response;
//        } catch (Exception e) {
//            return null;
//        }
//    }

}
