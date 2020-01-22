package bsp.controller.masters.accountsInfo;

import bsp.model.*;
import bsp.service.GroupService;
import bsp.supplemental.Collective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;
import java.math.BigInteger;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 22-Oct-17.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("accountsGroup")
public class AccountsGroupController extends Collective{
    @Autowired
    private GroupService groupService;
//
    private List<BigInteger> ids=new ArrayList<BigInteger>();

    private void getGroups(Model model) {
        List<Group> groups = new ArrayList<Group>();
        groups.addAll(setPrimaryGroupList());
        groups.addAll(this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,1,false));
        model.addAttribute("AcGroupList", groups);
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        getCompany(model);
        model.addAttribute("AcGroupList",getGroupList(278,1));
        Message("",model);
        return "masters/accountsInfo/groups/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("AcGroup", new Group());
        getGroups(model);
        model.addAttribute("mode", "create");
        model.addAttribute("default", false);
        Message("",model);
        getCompany(model);
        return "masters/accountsInfo/groups/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("AcGroup") @Valid Group obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.groupService.save(obj);
                else{
                    SetPreviousValue(obj,model);
                    Message("This Group is Duplicate.",model);
                    return "masters/accountsInfo/groups/form";
                }
                return "redirect:/accountsGroup/";
            }catch (Exception ex) {
                Message(ex.getCause().getCause().getMessage(),model);
                SetPreviousValue(obj,model);
                return "masters/accountsInfo/groups/form";
            }
        }
        return "masters/accountsInfo/groups/index";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {//
        try {
            Group group=this.groupService.findById(id);
            model.addAttribute("AcGroup", group);
            model.addAttribute("mode", "edit");

            List<Group> groups = new ArrayList<Group>();
            groups.addAll(setPrimaryGroupList());

            ids.clear();
            ids.add(id);
            RemoveFunc(id);

            groups.addAll(this.groupService.findByCompanyCodeAndTypeAndIsLogFalseAndIdNotIn(278,1,ids));
            model.addAttribute("AcGroupList", groups);

            getCompany(model);
            Message("", model);
            return "masters/accountsInfo/groups/form";

        } catch (Exception e) {
            return "redirect:/masters/accountsInfo/groups/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("AcGroup") @Valid Group obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            edit(obj.getId(), model);
            Message("Duplicate Group is Not Save. Please Try Again.", model);
            return "masters/accountsInfo/groups/form";
        }
        //endregion
        obj = SetDefault(obj, 2);
        if (!bindingResult.hasErrors()) {//|| group.isDefault()
            try {
                changeParentPrimaryType(obj);                                               //Update Child Primary Type Code
                this.groupService.save(setLogged(this.groupService.findById(obj.getId()))); //Insert
                this.groupService.save(obj);                                                //Update
                return "redirect:/accountsGroup/";
            } catch (Exception ex) {
                return "masters/accountsInfo/groups/form";
            }
        }
        return "";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            if(!isChild(id)) {
                Group group = groupService.findById(id);
                groupService.save(setLogged(group));  //Insert
                groupService.save(SetDefault(group, 3));    //Update
                Message("",model);
            }
            else {
                Message("Message: Have a Child For This Group.", model);
            }
            model.addAttribute("AcGroupList",getGroupList(278,1));
            getCompany(model);
            return "masters/accountsInfo/groups/index";
        } catch (Exception ex) {
            model.addAttribute("AcGroupList",getGroupList(278,1));
            getCompany(model);
            Message(ex.getMessage(),model);
            return "masters/accountsInfo/groups/index";
        }
    }


    //region All Methods
        private Group SetDefault(Group group, int action){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        group.setActionDate(timestamp);
        group.setCompanyCode(278);
        group.setUserCode(new BigInteger("1"));
        group.setActionType(action);
        group.setLog(action==3?true:false);
        group.setIndexing(0);
        group.setType(1);
//        group.setDefault(group.isDefault());
//        if (group.getParentCode()==null) {
//            group.setParentCode(this.groupService.findById(group.getId()).getParentCode());
//        }
        group.setPrimaryCode(group.getParentCode().compareTo(new BigInteger("4"))==1?this.groupService.findById(group.getParentCode()).getPrimaryCode():group.getParentCode().intValue());
//            group.setPrimaryCode(new BigInteger("4").compareTo(group.getParentCode())==1?this.groupService.findById(group.getParentCode()).getPrimaryCode():group.getParentCode().intValue());
//            group.setCode("0");
        return group;
    }

        private List<Group> setPrimaryGroupList(){
        List<Group> groups=new ArrayList<Group>();
        Group group;
        group = new Group();
        group.setId(new BigInteger("1"));
        group.setName("Asset (Primary)");
        groups.add(group);
        group = new Group();
        group.setId(new BigInteger("2"));
        group.setName("Liability (Primary)");
        groups.add(group);
        group = new Group();
        group.setId(new BigInteger("3"));
        group.setName("Income (Primary)");
        groups.add(group);
        group = new Group();
        group.setId(new BigInteger("4"));
        group.setName("Expense (Primary)");
        groups.add(group);
        return groups;
    }

        //When a Error then Show Previous Value(Entry Time A). Save Function A
        private void SetPreviousValue(Group group, Model model){
            Group gp = new Group();
            gp.setName(group.getName());
            gp.setParentCode(group.getParentCode());
            model.addAttribute("AcGroup", gp);
            model.addAttribute("mode", "create");
            model.addAttribute("default", false);
            getGroups(model);
        }

        //Under Group Name Remove
        private void RemoveFunc(BigInteger id){
            List<Group> groupList = this.groupService.findByParentCodeAndIsLog(id,false);
            if (!groupList.isEmpty()){
                for (Group group:groupList) {
                    ids.add(group.getId());
                    RemoveFunc(group.getId());
                }
            }
        }

        private List<Group> getGroupList(int companyCode, int typeCode){
            List<Group> groups = this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(companyCode,typeCode,false);
            for (Group group: groups) {
                Group gp = this.groupService.findById(group.getParentCode());
                if (gp==null)
                    group.setUnder(PrimaryGroup(group.getParentCode().intValue()));
                else
                    group.setUnder(gp.getName());
                group.setPrimaryCodeType(PrimaryGroup(group.getPrimaryCode()));
            }
            return groups;
        }

        private Group setLogged(Group group){
            Group logged = new Group();

            logged.setActionDate(group.getActionDate());
            logged.setActionType(group.getActionType());
            logged.setCompanyCode(group.getCompanyCode());
            logged.setUserCode(group.getUserCode());

            logged.setIndexing(group.getIndexing());
            logged.setType(group.getType());
            logged.setCode(group.getCode());
            logged.setName(group.getName());
            logged.setParentCode(group.getParentCode());
            logged.setPrimaryCode(group.getPrimaryCode());
            logged.setLog(true); ////

            return logged;
        }

        private boolean isChild(BigInteger id){
            List<Group> groupList = groupService.findByParentCodeAndIsLog(id,false);
            return (groupList.size()>0)?true:false;
        }

        private boolean isReplica(int companyCode, String name, BigInteger id){
            Group group = groupService.findByCompanyCodeAndTypeAndNameAndIsLogFalse(companyCode, 1, name);
            if(group!=null && id!=null)
                if(group.getId().compareTo(id)==0)
                    return false;
            if (group==null)
                return false;
            else
                return true;
        }

        private void changeParentPrimaryType(Group group){
            List<Group> groupList = this.groupService.findByParentCodeAndIsLog(group.getId(),false);
            if (!groupList.isEmpty()){
                for (Group gp:groupList) {
                    Group g = this.groupService.findById(gp.getId());
                    g.setPrimaryCode(group.getPrimaryCode());
                    changeParentPrimaryType(gp);
                }
            }
        }
    //endregion
}