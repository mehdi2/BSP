package bsp.repo;

import bsp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 13-March-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {



}
