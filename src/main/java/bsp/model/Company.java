package bsp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 10-March-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CompanyId")
    private int companyId;

    @Column(name = "CompanyName", unique = true)
    private String companyName;

    @Column(name = "CompanyAddressLine1")
    private String companyAddressLine1;

    @Column(name = "CompanyAddressLine2")
    private String companyAddressLine2;

    @Column(name = "ContactNo")
    private String contactNo;

    @Column(name = "Email")
    private String email;

    @Column(name = "Website")
    private String website;

    @Column(name = "FinancialPeriod")
    private Date financialPeriod;

    @Column(name = "AccountsStartOn")
    private Date accountsStartOn;

    @Column(name = "TypeOfBusiness")
    private int typeOfBusiness;

    @Column(name = "SectorOfBusiness")
    private int sectorOfBusiness;

    @Column(name = "CountryCode")
    private String country;

    @Column(name = "CurrencyName")
    private String currencyName;

    @Column(name = "CurrencySymbol")
    private String currencySymbol;

    @Column(name = "DecimalPartName")
    private String decimalPartName;

    @Column(name = "DecimalPartDigit")
    private int decimalPartDigit;

    /*1. Thousand / Lakh (1,00,000)
    2. Million / Billion (100,000,000)*/
    @Column(name = "DigitGrouping")
    private int digitGrouping;

    //region Default
    @Column(name = "UserCode")
    private BigInteger userCode;

    @Column(name = "ActionDate")
    private Timestamp actionDate;

    @Column(name = "ActionType")
    private int actionType;
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

    public String getCompanyAddressLine1() {
        return companyAddressLine1;
    }

    public void setCompanyAddressLine1(String companyAddressLine1) {
        this.companyAddressLine1 = companyAddressLine1;
    }

    public String getCompanyAddressLine2() {
        return companyAddressLine2;
    }

    public void setCompanyAddressLine2(String companyAddressLine2) {
        this.companyAddressLine2 = companyAddressLine2;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getDigitGrouping() {
        return digitGrouping;
    }

    public void setDigitGrouping(int digitGrouping) {
        this.digitGrouping = digitGrouping;
    }

    public BigInteger getUserCode() {
        return userCode;
    }

    public void setUserCode(BigInteger userCode) {
        this.userCode = userCode;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

}
