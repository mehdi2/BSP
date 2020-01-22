package bsp.service;

import bsp.model.Group;
import bsp.model.Ledger;
import bsp.repo.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 16-Nov-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "GroupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Group> findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(int companyCode, int type, boolean isLog){
        return groupRepository.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(companyCode, type, isLog);
    }

//    @Override
//    public List<Group> findByCompanyCodeAndTypeAndIdNot(int companyCode, int type, BigInteger id){
//        return groupRepository.findByCompanyCodeAndTypeAndIdNot(companyCode, type, id);
//    }

    @Override
    public List<Group> findByCompanyCodeAndTypeAndIsLogFalseAndIdNotIn(int companyCode, int type, List<BigInteger> ids){
        return groupRepository.findByCompanyCodeAndTypeAndIsLogFalseAndIdNotIn(companyCode, type, ids);
    }

    @Override
    public List<Group> findByParentCodeAndIsLog(BigInteger parentCode, boolean isLog){
        return groupRepository.findByParentCodeAndIsLog(parentCode,isLog);
    }

    @Override
    public Group findByCompanyCodeAndTypeAndNameAndIsLogFalse(int CompanyCode, int type, String name){
        return groupRepository.findByCompanyCodeAndTypeAndNameAndIsLogFalse(CompanyCode, type, name);
    }

    //region Default Method
    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findById(BigInteger id) {
        return groupRepository.findOne(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(BigInteger id) {
        groupRepository.delete(id);
    }
    //endregion

}
