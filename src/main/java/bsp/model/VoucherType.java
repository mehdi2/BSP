package bsp.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 12-Jan-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "voucher_type")
public class VoucherType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    //region Default Attribute
    @Column(name = "ActionDate")
    private Timestamp actionDate;

    @Column(name = "UserCode")
    private BigInteger userCode;

    @NotNull
    @Column(name = "CompanyCode")
    private int companyCode;

    @NotNull
    @Column(name = "ActionType")
    private int actionType;
    //endregion

    @Column(name = "Code")
    private BigInteger code;

    @Column(name = "Name", nullable=false)
    private String name;

    @Column(name = "Debit", length=100000)
    private ArrayList<BigInteger> debitGroupCodes;

    @Column(name = "Credit", length=100000)
    private ArrayList<BigInteger> creditGroupCodes;

    @Column(name = "Additional", length=100000)
    private ArrayList<BigInteger> additionalGroupCodes;

    @Column(name = "IsNarration")
    private boolean isNarration;

    @Column(name = "IsPrinting")
    private boolean isPrinting;

    @Column(name = "IsActive")
    private boolean isActive;

    @NotNull
    @Column(name = "IsLog")
    private boolean isLog;

////////////END///////////////////


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public BigInteger getUserCode() {
        return userCode;
    }

    public void setUserCode(BigInteger userCode) {
        this.userCode = userCode;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public BigInteger getCode() {
        return code;
    }

    public void setCode(BigInteger code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BigInteger> getDebitGroupCodes() {
        return debitGroupCodes;
    }

    public void setDebitGroupCodes(ArrayList<BigInteger> debitGroupCodes) {
        this.debitGroupCodes = debitGroupCodes;
    }

    public ArrayList<BigInteger> getCreditGroupCodes() {
        return creditGroupCodes;
    }

    public void setCreditGroupCodes(ArrayList<BigInteger> creditGroupCodes) {
        this.creditGroupCodes = creditGroupCodes;
    }

    public ArrayList<BigInteger> getAdditionalGroupCodes() {
        return additionalGroupCodes;
    }

    public void setAdditionalGroupCodes(ArrayList<BigInteger> additionalGroupCodes) {
        this.additionalGroupCodes = additionalGroupCodes;
    }

    public boolean isNarration() {
        return isNarration;
    }

    public void setNarration(boolean narration) {
        isNarration = narration;
    }

    public boolean isPrinting() {
        return isPrinting;
    }

    public void setPrinting(boolean printing) {
        isPrinting = printing;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }
}
