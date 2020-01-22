package bsp.service;

import bsp.model.Company;
import bsp.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 13-March-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "CompanyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(int id) {
        return companyRepository.findOne(id);
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(int id) {
        companyRepository.delete(id);
    }

}
