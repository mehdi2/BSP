package bsp.service;

import bsp.model.Voucher;
import bsp.repo.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 23-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Service(value = "VoucherService")
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public Voucher findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(int companyCode, BigInteger voucherTypeId){
        return voucherRepository.findFirstByCompanyCodeAndVoucherTypeIdOrderByVoucherNoDesc(companyCode, voucherTypeId);
    }

    @Override
    public List<Voucher> findByIdInOrderByIdAsc(List<BigInteger> voucherIds){
        return voucherRepository.findByIdInOrderByIdAsc(voucherIds);
    }

//    @Override
//    public List<Voucher> findByVoucherDateBeforeIdInOrderByIdAsc(Timestamp voucherDate, List<BigInteger> voucherIds){
//        return voucherRepository.findByVoucherDateBeforeIdInOrderByIdAsc(voucherDate,voucherIds);
//    }

    @Override
    public List<Voucher> findByVoucherDateBetween(Date sDate, Date eDate){
        return voucherRepository.findByVoucherDateBetween(sDate, eDate);
    }

    //<editor-fold desc="Default Method">
    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findById(BigInteger id) {
        return voucherRepository.findOne(id);
    }

    @Override
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public void delete(BigInteger id) {
        voucherRepository.delete(id);
    }
    //</editor-fold>
}
