package bsp.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */


public class StatementEntity {
    private String date;
    private BigInteger particularsId;
    private String particulars;

    private BigInteger voucherTypeId;
    private String voucherType;

    private BigInteger voucherNo;
    private Double debitAmount;
    private Double creditAmount;
    private Double balance;
    ///////////////////////////////////////////////////////////


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigInteger getParticularsId() {
        return particularsId;
    }

    public void setParticularsId(BigInteger particularsId) {
        this.particularsId = particularsId;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public BigInteger getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(BigInteger voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public BigInteger getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(BigInteger voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
