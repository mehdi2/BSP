package bsp.service;

import bsp.model.Item;
import bsp.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 20-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "ItemService")
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findByCompanyCodeAndIsLogFalseOrderByNameAsc(int companyCode){
        return itemRepository.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
    }

    @Override
    public List<Item> findByCompanyCodeAndIsServiceAndIsLogOrderByNameAsc(int companyCode, boolean isService, boolean isLog){
        return itemRepository.findByCompanyCodeAndIsServiceAndIsLogOrderByNameAsc(companyCode, isService, isLog);
    }

    @Override
    public Item findByCompanyCodeAndNameAndIsServiceAndIsLogFalse(int companyCode, String name, boolean isService){
        return itemRepository.findByCompanyCodeAndNameAndIsServiceAndIsLogFalse(companyCode, name, isService);
    }

    //region Default Method
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item findById(BigInteger id) { return itemRepository.findOne(id); }

    @Override
    public Item save(Item unit) { return itemRepository.save(unit); }

    @Override
    public void delete(BigInteger id) {
        itemRepository.delete(id);
    }
    //endregion

}
