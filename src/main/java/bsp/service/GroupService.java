package bsp.service;

import bsp.model.Group;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 16-Nov-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */


public interface GroupService {

//    List<Group> findByCompanyCodeAndTypeOrderByNameAsc(int companyCode, int type);
    List<Group> findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(int companyCode, int type, boolean isLog);

//    List<Group> findByCompanyCodeAndTypeAndIdNot(int companyCode, int type, BigInteger id);

    List<Group> findByCompanyCodeAndTypeAndIsLogFalseAndIdNotIn(int companyCode, int type, List<BigInteger> ids);

    List<Group> findByParentCodeAndIsLog(BigInteger parentCode, boolean isLog);

//    List<Group> findByCompanyCodeAndTrackId(int companyCode, BigInteger trackId);

    //Ledger->Form
//    List<Group> findByCompanyCodeOrderByNameAsc(int companyCode);

    Group findByCompanyCodeAndTypeAndNameAndIsLogFalse(int companyCode, int type, String name);

    //region Default Method
    List<Group> findAll();

    Group findById(BigInteger id);

    Group save(Group group);

    void delete(BigInteger id);
    //endregion

}
