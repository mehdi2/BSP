package bsp.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 25-March-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public class CompanyEntity implements Serializable {

    private int companyId;
    private String companyName;
    private String companyAddress1;
    private String companyAddress2;
    private String contactNo;
    private String email;
    private String website;
    private String country;

    private Date financialPeriod;
    private Date accountsStartOn;
    private int typeOfBusiness;
    private int sectorOfBusiness;

    private String currencyName;
    private String currencySymbol;
    private String decimalPartName;
    private int decimalPartDigit;
    private int thousandSeparator;

    //region Default
    ///Seassion
//    private BigInteger userCode;

    //Default
//    private Timestamp actionDate;

    //Static
//    private int actionType;

    ///Seassion
//    private int companyCode;
    //endregion
    /////////////////////////////////////////////////////////


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress1() {
        return companyAddress1;
    }

    public void setCompanyAddress1(String companyAddress1) {
        this.companyAddress1 = companyAddress1;
    }

    public String getCompanyAddress2() {
        return companyAddress2;
    }

    public void setCompanyAddress2(String companyAddress2) {
        this.companyAddress2 = companyAddress2;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(Date financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    public Date getAccountsStartOn() {
        return accountsStartOn;
    }

    public void setAccountsStartOn(Date accountsStartOn) {
        this.accountsStartOn = accountsStartOn;
    }

    public int getTypeOfBusiness() {
        return typeOfBusiness;
    }

    public void setTypeOfBusiness(int typeOfBusiness) {
        this.typeOfBusiness = typeOfBusiness;
    }

    public int getSectorOfBusiness() {
        return sectorOfBusiness;
    }

    public void setSectorOfBusiness(int sectorOfBusiness) {
        this.sectorOfBusiness = sectorOfBusiness;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getDecimalPartName() {
        return decimalPartName;
    }

    public void setDecimalPartName(String decimalPartName) {
        this.decimalPartName = decimalPartName;
    }

    public int getDecimalPartDigit() {
        return decimalPartDigit;
    }

    public void setDecimalPartDigit(int decimalPartDigit) {
        this.decimalPartDigit = decimalPartDigit;
    }

    public int getThousandSeparator() {
        return thousandSeparator;
    }

    public void setThousandSeparator(int thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }
}
