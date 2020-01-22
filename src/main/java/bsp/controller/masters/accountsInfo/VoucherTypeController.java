package bsp.controller.masters.accountsInfo;

import bsp.model.*;
import bsp.service.GroupService;
import bsp.service.VoucherTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 01-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("voucherType")
public class VoucherTypeController {
    @Autowired
    private HttpSession bspSession;

    @Autowired
    private VoucherTypeService voucherTypeService;

    @Autowired
    private GroupService groupService;

    private void getGroup(Model model) {
        model.addAttribute("GroupList", this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,1,false));
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<VoucherType> voucherTypes = this.voucherTypeService.findByCompanyCodeAndIsLogOrderByNameAsc(278,false);
        model.addAttribute("AcVoucherTypeList", voucherTypes);
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        return "masters/accountsInfo/voucherTypes/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("AcVoucherType", new VoucherType());
        getGroup(model);
        model.addAttribute("mode", "create");
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        Message("",model);
        return "masters/accountsInfo/voucherTypes/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("AcVoucherType") @Valid VoucherType obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.voucherTypeService.save(obj);
                else{
                    SetPreviousValue(obj,model);
                    Message("This Voucher Type is Duplicate.",model);
                    return "masters/accountsInfo/voucherTypes/form";
                }
                return "redirect:/voucherType/";
            } catch (Exception ex) {
                SetPreviousValue(obj,model);
                Message(ex.getCause().getCause().getMessage(),model);
                return "masters/accountsInfo/voucherType/form";
            }
        }
        return "";
    }

    @GetMapping(value = "/edit/{VoucherTypeId}")
    public String edit(@PathVariable BigInteger VoucherTypeId, Model model) {//
        try {
            VoucherType voucherType  = this.voucherTypeService.findById(VoucherTypeId);
            model.addAttribute("AcVoucherType", voucherType);
            model.addAttribute("mode", "edit");
            getGroup(model);
            model.addAttribute("companies", bspSession.getAttribute("sCompany"));
            Message("",model);
            return "masters/accountsInfo/voucherTypes/form";
        } catch (Exception e) {
            return "redirect:/masters/accountsInfo/voucherTypes/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("AcVoucherType") @Valid VoucherType obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            edit(obj.getId(), model);
            Message("Duplicate Voucher Type is Not Save. Please Try Again.", model);
            return "masters/accountsInfo/voucherTypes/form";
        }
        //endregion
        obj = SetDefault(obj,2);
        if (!bindingResult.hasErrors()) {
            try {
                this.voucherTypeService.save(setLogged(this.voucherTypeService.findById(obj.getId()))); //Insert
                this.voucherTypeService.save(obj);
                return "redirect:/voucherType/";
            } catch (Exception ex) {
                model.addAttribute("AcVoucherType", obj);
                model.addAttribute("mode", "edit");
                getGroup(model);
                Message(ex.getCause().getCause().getMessage(),model);
                return "voucherType/form";
            }
        }
        return "";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id) {
        try {
            VoucherType voucherType = voucherTypeService.findById(id);
            voucherTypeService.save(setLogged(voucherType));  //Insert
            voucherTypeService.save(SetDefault(voucherType, 3));    //Update
            return "redirect:/voucherType/";
        } catch (Exception ex) {
            return "redirect:/voucherType" + id;
        }
    }

    //region All Methods
    private void Message(String msg, Model model){
        Message message = new Message();
        message.setDisplay(msg!=""?true:false);
        message.setMessage(msg);
        model.addAttribute("msg",message);
    }

    private VoucherType SetDefault(VoucherType voucherType, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        voucherType.setActionDate(timestamp);
        voucherType.setCompanyCode(278);
        voucherType.setUserCode(new BigInteger("1"));
        voucherType.setActionType(action);
        voucherType.setLog(action==3?true:false);
        return voucherType;
    }

    private boolean isReplica(int companyCode, String name, BigInteger id){
        VoucherType voucherType = voucherTypeService.findByCompanyCodeAndNameAndIsLogFalseOrderByNameAsc(companyCode, name);
        if(voucherType!=null && id!=null)
            if(voucherType.getId().compareTo(id)==0)
                return false;
        if (voucherType==null)
            return false;
        else
            return true;
    }

    private void SetPreviousValue(VoucherType voucherType, Model model){
        VoucherType vt = new VoucherType();
        vt.setName(voucherType.getName());
        vt.setDebitGroupCodes(voucherType.getDebitGroupCodes());
        vt.setCreditGroupCodes(voucherType.getCreditGroupCodes());
        vt.setAdditionalGroupCodes(voucherType.getAdditionalGroupCodes());
        vt.setNarration(voucherType.isNarration());
        vt.setPrinting(voucherType.isPrinting());
        vt.setActive(voucherType.isActive());
        model.addAttribute("AcVoucherType", vt);
        model.addAttribute("mode", "create");
        getGroup(model);
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
    }

    private VoucherType setLogged(VoucherType voucherType){
        VoucherType logged = new VoucherType();

        logged.setActionDate(voucherType.getActionDate());
        logged.setActionType(voucherType.getActionType());
        logged.setCompanyCode(voucherType.getCompanyCode());
        logged.setUserCode(voucherType.getUserCode());

        logged.setCode(voucherType.getCode());
        logged.setName(voucherType.getName());
        logged.setDebitGroupCodes(voucherType.getDebitGroupCodes());
        logged.setCreditGroupCodes(voucherType.getCreditGroupCodes());
        logged.setAdditionalGroupCodes(voucherType.getAdditionalGroupCodes());
        logged.setNarration(voucherType.isNarration());
        logged.setPrinting(voucherType.isPrinting());
        logged.setActive(voucherType.isActive());
        logged.setLog(true); ////

        return logged;
    }
    //endregion

}
