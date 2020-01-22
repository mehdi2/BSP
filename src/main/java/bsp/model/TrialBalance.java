package bsp.model;


import java.math.BigInteger;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 27-Jan-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

public class TrialBalance {
    private BigInteger groupId;//
    private String groupName;//
    private BigInteger prantGroupId;//
    private int gLabel;//
    private int companyCode;

    private BigInteger particularsId;
    private String particulars;//
    private int fileTypeCode;
    private double oDebitAmount;
    private double oCreditAmount;
    private double tDebitAmount;
    private double tCreditAmount;
    private double cDebitAmount;//
    private double cCreditAmount;//
//-----------------------------------------------


    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigInteger getPrantGroupId() {
        return prantGroupId;
    }

    public void setPrantGroupId(BigInteger prantGroupId) {
        this.prantGroupId = prantGroupId;
    }

    public int getgLabel() {
        return gLabel;
    }

    public void setgLabel(int gLabel) {
        this.gLabel = gLabel;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
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

    public int getFileTypeCode() {
        return fileTypeCode;
    }

    public void setFileTypeCode(int fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }

    public double getoDebitAmount() {
        return oDebitAmount;
    }

    public void setoDebitAmount(double oDebitAmount) {
        this.oDebitAmount = oDebitAmount;
    }

    public double getoCreditAmount() {
        return oCreditAmount;
    }

    public void setoCreditAmount(double oCreditAmount) {
        this.oCreditAmount = oCreditAmount;
    }

    public double gettDebitAmount() {
        return tDebitAmount;
    }

    public void settDebitAmount(double tDebitAmount) {
        this.tDebitAmount = tDebitAmount;
    }

    public double gettCreditAmount() {
        return tCreditAmount;
    }

    public void settCreditAmount(double tCreditAmount) {
        this.tCreditAmount = tCreditAmount;
    }

    public double getcDebitAmount() {
        return cDebitAmount;
    }

    public void setcDebitAmount(double cDebitAmount) {
        this.cDebitAmount = cDebitAmount;
    }

    public double getcCreditAmount() {
        return cCreditAmount;
    }

    public void setcCreditAmount(double cCreditAmount) {
        this.cCreditAmount = cCreditAmount;
    }
}
