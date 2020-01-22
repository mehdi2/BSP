package bsp.service;

import bsp.model.Company;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 13-March-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public interface CompanyService {

    List<Company> findAll();

    Company findById(int id);

    Company save(Company company);

    void delete(int id);

}
