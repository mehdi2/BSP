package bsp.controller.transactions;

import bsp.model.*;
import bsp.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 23-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("accountingStockVouchers")
public class AccountingStockVouchersController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherTypeService voucherTypeService;

    @Autowired
    private CostCenterService costCenterService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GodownService godownService;

    @Autowired
    private ItemService itemService;

    private ArrayList<BigInteger> ids = new ArrayList<BigInteger>();

    public String UPLOAD_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\";

    @GetMapping(value = "/{voucherTypeId}")
    public String index(@PathVariable("voucherTypeId") BigInteger voucherTypeId, Model model, HttpSession bspSession) {
        //region Party Name
            VoucherType voucherType = voucherTypeService.findById(voucherTypeId);
            ArrayList<BigInteger> partyNamesLedgerId = voucherType.getCreditGroupCodes();
            if (partyNamesLedgerId!=null){
                for (BigInteger id: partyNamesLedgerId) {
                    findFunc(id);
                }
                partyNamesLedgerId.addAll(ids);
                ids.clear();
                List<Ledger> partyNames = ledgerService.findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(278,partyNamesLedgerId);
                model.addAttribute("PartyNames",partyNames);
            }
        //endregion

        //region Purchase
            ArrayList<BigInteger> purchaseLedgerId = voucherType.getDebitGroupCodes();
            if (purchaseLedgerId!=null){
                for (BigInteger id: purchaseLedgerId) {
                    findFunc(id);
                }
                purchaseLedgerId.addAll(ids);
                ids.clear();
                List<Ledger> purchase = ledgerService.findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(278,purchaseLedgerId);
                model.addAttribute("PurchaseLedgers",purchase);
            }
        //endregion

        //Godown
        model.addAttribute("Godowns",this.godownService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278));

        //Item Name
        model.addAttribute("items",this.itemService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278));

        //region Additional Ledger
            ArrayList<BigInteger> additionalLedgerIds = voucherType.getAdditionalGroupCodes();
            if (additionalLedgerIds!=null){
                for (BigInteger id: additionalLedgerIds) {
                    findFunc(id);
                }
                additionalLedgerIds.addAll(ids);
                ids.clear();
                List<Ledger> additionalLedgers = ledgerService.findByCompanyCodeAndIsLogFalseAndGroupIdInOrderByNameAsc(278,additionalLedgerIds);
                model.addAttribute("AdditionalLedgers",additionalLedgers);
            }
        //endregion


        //region Voucher Setup
        Voucher voucher = this.voucherService.findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(278,voucherTypeId);
        if(voucher==null){
            voucher=new Voucher();
            voucher.setVoucherNo(new BigInteger("1"));
        }
        else{
            voucher = new Voucher();
            BigInteger voucherValue = this.voucherService.findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(278,voucherTypeId).getVoucherNo();
            voucher.setVoucherNo(voucherValue.add(new BigInteger("1")));
        }
        voucher.setVoucherType(this.voucherTypeService.findById(voucherTypeId));
        voucher.setUserCode(new BigInteger("1"));
        voucher.setActionDate(new Timestamp(System.currentTimeMillis()));
        voucher.setVoucherDate(new Timestamp(System.currentTimeMillis()));
        voucher.setCompanyCode(278);
        model.addAttribute("Voucher", voucher);
        //endregion

        //region Other
        Message("",model);
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        model.addAttribute("AcVoucherTypes", this.voucherTypeService.findByCompanyCodeAndIsActiveAndIsLogOrderByNameAsc(278,true,false));
        model.addAttribute("AcVoucherType",this.voucherTypeService.findById(voucherTypeId));
        //endregion
        return "transactions/accountingStockVouchers";
    }

    @PostMapping(value = {"/create"})
    public String save(@ModelAttribute("voucher") @Valid Voucher obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj, 1);
        //region Accounts Voucher
        obj.getAccountsVoucher().removeIf(cm -> (cm.getDebitAmount()==0 || Double.isNaN(cm.getDebitAmount())==true) && (cm.getCreditAmount()==0 || Double.isNaN(cm.getCreditAmount())==true));
        Voucher subObj = obj;
        obj.getAccountsVoucher().forEach(voucher->{
            voucher.setVoucher(subObj);
            voucher.setLedger(this.ledgerService.findById(voucher.getLedger().getId()));
        });
        //endregion

        //region Inventory Voucher
        obj.getInventoryVoucher().removeIf(cm -> cm.getItem()==null &&(cm.getAmount()==0 || Double.isNaN(cm.getAmount())==true));
        Voucher subObject = obj;
        obj.getInventoryVoucher().forEach(inventory->{
            inventory.setVoucher(subObject);
            if (inventory.getGodown()!=null)
                if (inventory.getGodown().getId()!=null)
                    inventory.setGodown(this.godownService.findById(inventory.getGodown().getId()));
            if (inventory.getItem()!=null)
                if (inventory.getItem().getId()!=null)
                    inventory.setItem(this.itemService.findById(inventory.getItem().getId()));
                else
                    inventory.setItem(null);

        });
        //endregion

        //region Files Upload
//        if(uploadingFiles.length<1 || uploadingFiles.length>5 || uploadingFiles[0].isEmpty()){
//            return "redirect:/invProduct/show/" + obj.getId();
//        }
//        for(MultipartFile uploadedFile : uploadingFiles) {
//            File file = new File(UPLOAD_DIRECTORY +obj.getVoucherType().getId()+" "+obj.getId()+" "+ uploadedFile.getOriginalFilename());
//
//            try {
//                uploadedFile.transferTo(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        //endregion

        if (!bindingResult.hasErrors()) {
            try {
                 Voucher savedObj = this.voucherService.save(obj);
                return "redirect:/accountingStockVouchers/"+obj.getVoucherType().getId();
            } catch (Exception ex) {
                System.out.println("err: "+ex.getCause().getCause().getMessage());
            }
        }

        return "/accountingVouchers/"+obj.getVoucherType().getId();
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
        voucher.setVoucherDate(convertStringToTimestamp(voucher.getVoucherDateText()));
        voucher.setCompanyCode(1);
        voucher.setUserCode(new BigInteger("1"));
        voucher.setActionType(action);
        return voucher;
    }

    public Timestamp convertStringToTimestamp(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            // you can change format of date
            Date date = formatter.parse(str_date);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    private void findFunc(BigInteger id){
        List<Group> groupList = this.groupService.findByParentCodeAndIsLog(id,false);
        if (!groupList.isEmpty()){
            for (Group group:groupList) {
                ids.add(group.getId());
                findFunc(group.getId());
            }
        }
    }

    //endregion
}
