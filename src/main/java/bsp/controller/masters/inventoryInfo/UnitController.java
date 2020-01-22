package bsp.controller.masters.inventoryInfo;

import bsp.model.Message;
import bsp.model.Unit;
import bsp.service.UnitService;
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
 * Created by Chowdhury Muhammad Mehdi Hasan on 17-April-18.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("unit")
public class UnitController extends Collective {
    @Autowired
    private UnitService unitService;

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        getCompany(model);
        Message("",model);
        List<Unit> units = this.unitService.findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(278);
        model.addAttribute("units", units);
        return "masters/inventoryInfo/unit/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        setCommon(new Unit(), "create", "",model);
        return "masters/inventoryInfo/unit/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("unit") @Valid Unit obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getUnitSymbol(),obj.getId()))
                    this.unitService.save(obj);
                else{
                    setCommon(obj,"create","Duplicate Unit is Not Save. Please Try Again.", model);
                    return "masters/inventoryInfo/unit/form";
                }
                return "redirect:/unit/";
            } catch (Exception ex) {
                setCommon(new Unit(),"create",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/unit/form";
            }
        }
        return "masters/inventoryInfo/unit";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {
        try {
            setCommon(unitService.findById(id),"edit","",model);
            return "masters/inventoryInfo/unit/form";

        } catch (Exception e) {
            return "redirect:/masters/inventoryInfo/unit/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("unit") @Valid Unit obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getUnitSymbol(), obj.getId())) {
            setCommon(obj,"edit","Duplicate Unit is Not Save. Please Try Again.",model);
            return "masters/inventoryInfo/unit/form";
        }
        //endregion
        obj = SetDefault(obj,2);
        if (!bindingResult.hasErrors()) {
            try {
                this.unitService.save(setLogged(this.unitService.findById(obj.getId())));   //Insert
                this.unitService.save(obj);
                return "redirect:/unit/";
            } catch (Exception ex) {
                setCommon(obj,"edit",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/unit/form";
            }
        }
        return "unit/form";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            Unit unit = this.unitService.findById(id);
            this.unitService.save(setLogged(unit));  //Insert
            this.unitService.save(SetDefault(unit, 3));    //Update
            return "redirect:/unit";
        } catch (Exception ex) {
            Message(ex.getCause().getCause().getMessage(),model);
            return "masters/inventoryInfo/unit/index";
        }
    }

    //region All Methods
    private Unit SetDefault(Unit unit, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        unit.setActionDate(timestamp);
        unit.setCompanyCode(278);
        unit.setUserCode(new BigInteger("1"));
        unit.setActionType(action);
        unit.setLog(action==3?true:false);
        return unit;
    }

    private void setCommon(Unit unit, String action, String message, Model model){
        model.addAttribute("unit", unit);
        model.addAttribute("mode", action);
        getCompany(model);
        Message(message,model);
    }

    //Duplicate Data Check
    private boolean isReplica(int companyCode,String unitSymbol,BigInteger id){
        Unit unit = unitService.findByCompanyCodeAndUnitSymbolAndIsLogFalse(companyCode,unitSymbol);
        if(unit!=null && id!=null)
            if(unit.getId().compareTo(id)==0)
                return false;
        if (unit==null)
            return false;
        else
            return true;
    }

    private Unit setLogged(Unit unit){
        Unit logged = new Unit();

        logged.setActionDate(unit.getActionDate());
        logged.setActionType(unit.getActionType());
        logged.setCompanyCode(unit.getCompanyCode());
        logged.setUserCode(unit.getUserCode());

        logged.setUnitType(unit.getUnitType());
        logged.setUnitSymbol(unit.getUnitSymbol());
        logged.setFormalName(unit.getFormalName());
        logged.setNumberOfDecimalPlace(unit.getNumberOfDecimalPlace());
        logged.setLog(true); ////

        return logged;
    }
    //endregion
}