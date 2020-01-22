package bsp.controller.transactions;

import bsp.model.*;
import bsp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 23-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("accountingVouchers")
public class AccountingVouchersController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherTypeService voucherTypeService;

    @Autowired
    private CostCenterService costCenterService;

    @Autowired
    private LedgerService ledgerService;

    @GetMapping(value = "/{voucherTypeId}")
    public String index(@PathVariable("voucherTypeId") BigInteger voucherTypeId, Model model, HttpSession bspSession) {
        model.addAttribute("costCenterList",this.costCenterService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278));
        Voucher voucher = this.voucherService.findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(278,voucherTypeId);
        if(voucher==null){
            voucher=new Voucher();
            voucher.setVoucherNo(new BigInteger("1"));
        }
        else{
//            voucher = new Voucher();
            BigInteger voucherValue = this.voucherService.findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(278,voucherTypeId).getVoucherNo();
            voucher.setVoucherNo(voucherValue.add(new BigInteger("1")));
        }
        voucher.setVoucherType(this.voucherTypeService.findById(voucherTypeId));
        voucher.setUserCode(new BigInteger("1"));
        voucher.setActionDate(new Timestamp(System.currentTimeMillis()));

//        voucher.setVoucherDateText(new Timestamp(System.currentTimeMillis()).toString());
        Date d = Calendar.getInstance().getTime(); // Current time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Set your date format
        voucher.setVoucherDateText(sdf.format(d));
        voucher.setVoucherDate(new Timestamp(d.getTime()));


        model.addAttribute("Voucher", voucher);

        VoucherType voucherTypes = voucherTypeService.findById(voucherTypeId);
        //For isLog
//        List<Ledger> accountsLedgers = ledgerService.findByActionTypeNotAndCompanyCodeAndGroupIdInOrderByNameAsc(3,1, voucherTypes.getDebitGroupCodes());
        List<Ledger> accountsLedgers = ledgerService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278);
        model.addAttribute("accountsLedgers", accountsLedgers);

        Message("",model);
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        List<VoucherType> voucherTypeList = this.voucherTypeService.findByCompanyCodeAndIsActiveAndIsLogOrderByNameAsc(278,true,false);
        model.addAttribute("AcVoucherTypes", voucherTypeList);
        model.addAttribute("AcVoucherType",this.voucherTypeService.findById(voucherTypeId));

        model.addAttribute("isCostCenter",true);   // Cost Center Enable/Disable

        model.addAttribute("isDebit",true);         // isDebit use for Select Debit or Credit
        return "transactions/accountingVouchers";

    }

    @PostMapping(value = {"/create"})
    public String save(@ModelAttribute("voucher") @Valid Voucher obj, BindingResult bindingResult, Model model) {
        obj.getAccountsVoucher().removeIf(accountsVoucher -> accountsVoucher.getLedger()==null);
        Voucher finalObj = obj;
        obj.getAccountsVoucher().forEach(voucher->voucher.setVoucher(finalObj));
        obj = SetDefault(obj, 1);

        if (!bindingResult.hasErrors()) {
            try {
                Voucher savedObj = this.voucherService.save(obj);
                return "redirect:/accountingVouchers/"+obj.getVoucherType().getId();
            } catch (Exception ex) {
                System.out.println("err: "+ex.getCause().getCause().getMessage());
            }
        }

           return "/accountingVouchers/create/"+obj.getVoucherType().getId();
    }

    //region All Methods
        private void Message(String msg, Model model){
            Message message = new Message();
            message.setDisplay(msg!=""?true:false);
            message.setMessage(msg);
            model.addAttribute("msg",message);
        }

        private Voucher SetDefault(Voucher voucher, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        voucher.setActionDate(timestamp);
        voucher.setCompanyCode(278);
        voucher.setUserCode(new BigInteger("1"));
        voucher.setActionType(action);

        voucher.setVoucherDate(convertStringToTimestamp(voucher.getVoucherDateText()));

            return voucher;
    }

        public Timestamp convertStringToTimestamp(String str_date) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date(sdf.parse(str_date).getTime());
                java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
                return timeStampDate;
            } catch (ParseException e) {
                System.out.println("Exception :" + e);
                return null;
            }
        }
    //endregion

}
