package bsp.service;

import bsp.model.Item;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 20-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface ItemService {

    List<Item> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode);

    List<Item> findByCompanyCodeAndIsServiceAndIsLogOrderByNameAsc(int companyCode, boolean isService, boolean isLog);

    Item findByCompanyCodeAndNameAndIsServiceAndIsLogFalse(int companyCode, String name, boolean isService);

    //region Default Method
    List<Item> findAll();

    Item findById(BigInteger id);

    Item save(Item unit);

    void delete(BigInteger pid);
    //endregion

}
