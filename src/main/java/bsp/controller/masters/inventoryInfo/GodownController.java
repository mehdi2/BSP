package bsp.controller.masters.inventoryInfo;

import bsp.model.Godown;
import bsp.model.Message;
import bsp.service.GodownService;
import bsp.supplemental.Collective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 28-April-18.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("godown")
public class GodownController extends Collective {
    @Autowired
    private GodownService godownService;

    private List<BigInteger> ids=new ArrayList<BigInteger>();

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<Godown> godowns = this.godownService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278);
        godowns.forEach(godown -> godown.setParentName(godown.getParentCode().compareTo(new BigInteger("0"))==0?"Primary":this.godownService.findById(godown.getParentCode()).getName()));
        model.addAttribute("GodownList",godowns);
        getCompany(model);
        Message("",model);
        return "masters/inventoryInfo/godown/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        setCommon(new Godown(),"create","",model);
        return "masters/inventoryInfo/godown/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("Godown") @Valid Godown obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.godownService.save(obj);
                else{
                    setCommon(obj,"create","Duplicate Godown is Not Save. Please Try Again.", model);
                    return "masters/inventoryInfo/godown/form";
                }
                return "redirect:/godown";
            } catch (Exception ex) {
                setCommon(new Godown(),"create",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/godown/form";
            }
        }
        return "masters/inventoryInfo/godown";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {
        try {
            setCommon(godownService.findById(id),"edit","",model);
            return "masters/inventoryInfo/godown/form";

        } catch (Exception e) {
            return "redirect:/masters/inventoryInfo/godown/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("Godown") @Valid Godown obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            setCommon(obj,"edit","Duplicate Godown is Not Save. Please Try Again.",model);
            return "masters/inventoryInfo/godown/form";
        }
        //endregion
        obj = SetDefault(obj,2);
        if (!bindingResult.hasErrors()) {
            try {
                this.godownService.save(setLogged(this.godownService.findById(obj.getId())));   //Insert
                this.godownService.save(obj);
                return "redirect:/godown";
            } catch (Exception ex) {
                setCommon(obj,"edit",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/godown/form";
            }
        }
        return "redirect:/godown/";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            if(!isChild(id)) {
                Godown godown = this.godownService.findById(id);
                this.godownService.save(setLogged(godown));  //Insert
                this.godownService.save(SetDefault(godown, 3));    //Update
                index(model);
            }
            else {
                index(model);
                Message("Message: Have a Child For This Godown.", model);
            }
            return "masters/inventoryInfo/godown/index";
        } catch (Exception ex) {
            index(model);
            Message(ex.getCause().getCause().getMessage(), model);
            return "masters/inventoryInfo/godown/index";
        }
    }

    //region All Methods
    private Godown SetDefault(Godown godown, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        godown.setActionDate(timestamp);
        godown.setCompanyCode(278);
        godown.setUserCode(new BigInteger("1"));
        godown.setActionType(action);
        godown.setLog(action==3?true:false);
        return godown;
    }

    private void setCommon(Godown godown, String action, String message, Model model){
        model.addAttribute("Godown", godown);
        if(action=="edit") {
            model.addAttribute("ddlGodownList", getParentList(godown.getId()));
        }
        else{
            List<Godown> godowns = new ArrayList<Godown>();
            godowns.add(setPrimary());
            godowns.addAll(this.godownService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278));
            model.addAttribute("ddlGodownList", godowns);
        }
        model.addAttribute("mode", action);
        getCompany(model);
        Message(message,model);
    }

    //Remove Child From CostCenter
    private List<Godown> getParentList(BigInteger id){
        List<Godown> costCenters = new ArrayList<Godown>();
        costCenters.add(setPrimary());

        ids.clear();
        ids.add(id);
        RemoveFunc(id);

        costCenters.addAll(this.godownService.findByCompanyCodeAndIsLogFalseAndIdNotIn(278,ids));

        return costCenters;
    }

    private Godown setPrimary(){
        Godown godown = new Godown();
        godown.setId(new BigInteger("0"));
        godown.setName("Primary");
        return godown;
    }

    //Under Group Name Remove
    private void RemoveFunc(BigInteger id){
        List<Godown> godowns = this.godownService.findByParentCodeAndIsLog(id,false);
        if (!godowns.isEmpty()){
            for (Godown godown:godowns) {
                ids.add(godown.getId());
                RemoveFunc(godown.getId());
            }
        }
    }

    //Duplicate Data Check
    private boolean isReplica(int companyCode,String name,BigInteger id){
        Godown godown = godownService.findByCompanyCodeAndNameAndIsLogFalse(companyCode,name);
        if(godown!=null && id!=null)
            if(godown.getId().compareTo(id)==0)
                return false;
        if (godown==null)
            return false;
        else
            return true;
    }

    private Godown setLogged(Godown godown){
        Godown logged = new Godown();

        logged.setActionDate(godown.getActionDate());
        logged.setActionType(godown.getActionType());
        logged.setCompanyCode(godown.getCompanyCode());
        logged.setUserCode(godown.getUserCode());

        logged.setCode(godown.getCode());
        logged.setName(godown.getName());
        logged.setParentCode(godown.getParentCode());
        logged.setLog(true); ////

        return logged;
    }

    private boolean isChild(BigInteger id){
        List<Godown> godowns = this.godownService.findByParentCodeAndIsLog(id,false);
        return (godowns.size()>0)?true:false;
    }
    //endregion
}