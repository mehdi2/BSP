package bsp.controller.masters.accountsInfo;

import bsp.model.*;
import bsp.service.GroupService;
import bsp.service.LedgerService;
import bsp.supplemental.Collective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 28-Nov-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("ledger")
public class LedgerController extends Collective {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private GroupService groupService;

    private void getGroups(Model model) {
        model.addAttribute("AcGroupList", this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,1,false));
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<Ledger> ledgers = this.ledgerService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278);
        for (Ledger ledger: ledgers) {
            ledger.setParentName(ledger.getGroup().getName());
        }
        model.addAttribute("AcLedgersList", ledgers);
        getCompany(model);
        return "masters/accountsInfo/ledgers/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("AcLedger",model.hashCode()==0?setAccountLedger():model.asMap().get("AcLedger"));
        getGroups(model);
        model.addAttribute("mode", "create");
        getCompany(model);
        Message("", model);
        return "masters/accountsInfo/ledgers/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("AcLedger") @Valid Ledger obj, BindingResult bindingResult, Model model) {
        SetDefault(obj, 1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.ledgerService.save(obj);
                else{
                    SetPreviousValue(obj, model);
                    return "masters/accountsInfo/ledgers/form";
                }
                return "redirect:/ledger/";
            } catch (Exception ex) {
                create(model);
                Message(ex.getCause().getCause().getMessage(), model);
                return "masters/accountsInfo/ledgers/form";
            }

        }
        return "";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {//
        try {
            model.addAttribute("AcLedger", this.ledgerService.findById(id));
            model.addAttribute("mode", "edit");
            getGroups(model);
            getCompany(model);
            Message("",model);
            return "masters/accountsInfo/ledgers/form";
        } catch (Exception ex) {
            create(model);
            return "masters/accountsInfo/ledgers/form";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("AcGroup") @Valid Ledger obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            edit(obj.getId(), model);
            Message("Duplicate Ledger is Not Save. Please Try Again.", model);
            return "masters/accountsInfo/ledgers/form";
        }
        //endregion
        obj = SetDefault(obj,2);
        if (!bindingResult.hasErrors()) {
            try {
                this.ledgerService.save(setLogged(obj.getId()));                        //Insert
                this.ledgerService.save(obj);                                           //Update
                return "redirect:/ledger/";
            } catch (Exception ex) {
                model.addAttribute("AcLedger",obj);
                Message(ex.getCause().getCause().getMessage(),model);
                return "masters/accountsInfo/ledgers/form";
            }
        }
        return "";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id) {
        try {
            Ledger ledger = this.ledgerService.findById(id);
            ledger.setLog(true);
            this.ledgerService.save(setLogged(ledger.getId()));
            this.ledgerService.save(SetDefault(ledger,3));
            return "redirect:/ledger/";
        } catch (Exception ex) {
            return "redirect:/ledger";
        }
    }

    //region All Methods
    private Ledger SetDefault(Ledger ledger, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ledger.setActionDate(timestamp);
        ledger.setCompanyCode(278);
        ledger.setUserCode(new BigInteger("1"));
        ledger.setActionType(action);
        return ledger;
    }

    //When Open New Ledger Form then Select isActive
    private Ledger setAccountLedger(){
        Ledger ledger = new Ledger();
        ledger.setActive(true);
        return ledger;
    }

    //For Logged Data
    private Ledger setLogged(BigInteger id){
        Ledger ledger = this.ledgerService.findById(id);
        Ledger logged = new Ledger();

        logged.setActionDate(ledger.getActionDate());
        logged.setCompanyCode(ledger.getCompanyCode());
        logged.setUserCode(ledger.getUserCode());
        logged.setActionType(ledger.getActionType());

        logged.setCode(ledger.getCode());
        logged.setName(ledger.getName());
        logged.setGroup(ledger.getGroup());
        logged.setOpeningBalance(ledger.getOpeningBalance());
        logged.setDebit(ledger.isDebit());
        logged.setCostCenter(ledger.isCostCenter());
        logged.setActive(ledger.isActive());
        logged.setLog(true);
        return logged;
    }

    //Duplicate Data Check
    private boolean isReplica(int companyCode,String name,BigInteger id){
        Ledger ledger = ledgerService.findByCompanyCodeAndNameAndIsLogFalse(companyCode,name);
        if(ledger!=null && id!=null)
            if(ledger.getId().compareTo(id)==0)
                return false;
        if (ledger==null)
            return false;
        else
            return true;
    }

    //When Wrong or Error then show previous value
    private void SetPreviousValue(Ledger ledger, Model model){
        model.addAttribute("AcLedger", ledger);
        model.addAttribute("mode", "create");
        getGroups(model);
        getCompany(model);
        Message("Duplicate Ledger is not Save. Please Try Again.",model);
    }
    //endregion

}
