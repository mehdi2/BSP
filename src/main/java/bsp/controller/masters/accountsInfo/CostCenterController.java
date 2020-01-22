package bsp.controller.masters.accountsInfo;

import bsp.model.CostCenter;
import bsp.service.CostCenterService;
import bsp.supplemental.Collective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 25-Jun-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("costCenter")
public class CostCenterController extends Collective{

    @Autowired
    private CostCenterService costCenterService;

    private List<BigInteger> ids=new ArrayList<BigInteger>();

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<CostCenter> costCenters = this.costCenterService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278);
        costCenters.forEach(costCenter -> costCenter.setParentName(costCenter.getParentCode().compareTo(new BigInteger("0"))==0?"Primary":this.costCenterService.findById(costCenter.getParentCode()).getName()));
        model.addAttribute("CostCenterList", costCenters );
        getCompany(model);
        if (!model.asMap().containsKey("msg"))
            Message("",model);
        return "masters/accountsInfo/costCenter/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        setPreviousValue(new CostCenter(),"create","",model);
        return "masters/accountsInfo/costCenter/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("CostCenter") @Valid CostCenter obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.costCenterService.save(obj);
                else{
                    setPreviousValue(obj,"create","Duplicate Group is Not Save. Please Try Again.", model);
                    return "masters/accountsInfo/costCenter/form";
                }
                return "redirect:/costCenter";
            } catch (Exception ex) {
                setPreviousValue(new CostCenter(),"create",ex.getCause().getCause().getMessage(),model);
                return "masters/accountsInfo/costCenter/form";
            }
        }
        return "masters/accountsInfo/costCenter/";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {
        try {
            setPreviousValue(this.costCenterService.findById(id),"edit","",model);
            return "masters/accountsInfo/costCenter/form";

        } catch (Exception e) {
            return "redirect:/masters/accountsInfo/costCenter/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("CostCenter") @Valid CostCenter obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            setPreviousValue(obj,"edit","Duplicate Group is Not Save. Please Try Again.",model);
            return "masters/accountsInfo/costCenter/form";
        }
        //endregion
        obj = SetDefault(obj,2);

        if (!bindingResult.hasErrors()) {
            try {
                this.costCenterService.save(setLogged(this.costCenterService.findById(obj.getId())));                                   //Insert
                this.costCenterService.save(obj);                                                                                       //Update
                return "redirect:/costCenter/";
            } catch (Exception ex) {
                setPreviousValue(obj,"edit",ex.getCause().getCause().getMessage(),model);
                return "masters/accountsInfo/costCenter/form";
            }
        }
        return "CostCenter/form";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            if(!isChild(id)) {
                CostCenter costCenter = this.costCenterService.findById(id);
                this.costCenterService.save(setLogged(costCenter));         //Insert
                this.costCenterService.save(SetDefault(costCenter, 3));    //Update
                Message("",model);
            }
            else
                Message("Message: Have a Child For This Group.", model);
            index(model);
            return "masters/accountsInfo/costCenter/index";
        } catch (Exception ex) {
            index(model);
            Message(ex.getCause().getCause().getMessage(), model);
            return "masters/accountsInfo/costCenter/index";
        }
    }

    //region All Methods
    private CostCenter SetDefault(CostCenter costCenter, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        costCenter.setActionDate(timestamp);
        costCenter.setCompanyCode(278);
        costCenter.setUserCode(new BigInteger("1"));
        costCenter.setActionType(action);
        costCenter.setLog(action==3?true:false);
        return costCenter;
    }

    private CostCenter setPrimary(){  //GodownList
        CostCenter costCenter = new CostCenter();
        costCenter.setId(new BigInteger("0"));
        costCenter.setName("Primary");
        return costCenter;
    }

    //Duplicate Data Check
    private boolean isReplica(int companyCode,String name,BigInteger id){
        CostCenter ledger = costCenterService.findByCompanyCodeAndNameAndIsLogFalse(companyCode,name);
        if(ledger!=null && id!=null)
            if(ledger.getId().compareTo(id)==0)
                return false;
        if (ledger==null)
            return false;
        else
            return true;
    }

    private void setPreviousValue(CostCenter costCenter, String action, String message, Model model){
        model.addAttribute("CostCenter", costCenter);
        if(action=="edit") {
            model.addAttribute("CostCenterList", getParentList(costCenter.getId()));
        }
        else{
            List<CostCenter> costCenters = new ArrayList<CostCenter>();
            costCenters.add(setPrimary());
            costCenters.addAll(this.costCenterService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278));
            model.addAttribute("CostCenterList", costCenters);
        }
        model.addAttribute("mode", action);
        getCompany(model);
        Message(message,model);
    }

    //Under Group Name Remove
    private void RemoveFunc(BigInteger id){
        List<CostCenter> groupList = this.costCenterService.findByParentCodeAndIsLog(id,false);
        if (!groupList.isEmpty()){
            for (CostCenter costCenter:groupList) {
                ids.add(costCenter.getId());
                RemoveFunc(costCenter.getId());
            }
        }
    }

    //Remove Child From CostCenter
    private List<CostCenter> getParentList(BigInteger id){
        List<CostCenter> costCenters = new ArrayList<CostCenter>();
        costCenters.add(setPrimary());

        ids.clear();
        ids.add(id);
        RemoveFunc(id);

        costCenters.addAll(this.costCenterService.findByCompanyCodeAndIsLogFalseAndIdNotIn(278,ids));

        return costCenters;
    }

    private CostCenter setLogged(CostCenter costCenter){
        CostCenter logged = new CostCenter();

        logged.setActionDate(costCenter.getActionDate());
        logged.setActionType(costCenter.getActionType());
        logged.setCompanyCode(costCenter.getCompanyCode());
        logged.setUserCode(costCenter.getUserCode());

        logged.setCode(costCenter.getCode());
        logged.setName(costCenter.getName());
        logged.setParentCode(costCenter.getParentCode());
        logged.setParentName("");
        logged.setLog(true); ////

        return logged;
    }

    private boolean isChild(BigInteger id){
        List<CostCenter> groupList = this.costCenterService.findByParentCodeAndIsLog(id,false);
        return (groupList.size()>0)?true:false;
    }
    //endregion
}