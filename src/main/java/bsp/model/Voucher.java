package bsp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
//import org.apache.derby.client.am.DateTime;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 24-Jan-18.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "[voucher]")
public class Voucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    //region Default Attribute
    @NotNull
    @Column(name = "UserCode")
    private BigInteger userCode;

    @NotNull
    @Column(name = "ActionDate")
    private Timestamp actionDate;

    @NotNull
    @Column(name = "ActionType")
    private int actionType;

    @NotNull
    @Column(name = "CompanyCode")
    private int companyCode;
    //endregion

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="VoucherTypeId")
    private VoucherType voucherType;

    @NotNull
    @Column(name = "VoucherNo")
    private BigInteger voucherNo;

    @NotNull
    @Column(name = "VoucherDate")
    private Timestamp voucherDate;  /*Timestamp*/

    //For Variable
    @Transient
    private String voucherDateText;

    //May be object Relation
    @Column(name = "Reference")
    private String reference;

    //May be object Relation
    @Column(name = "PartyAccountId")
    private BigInteger PartyAccountId;

    @Column(name = "PurchaseLedgerId")
    private BigInteger PurchaseLedgerId;

    @Column(name = "Narration")
    private String narration;

//    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<AccountsVoucher> accountsVoucher =new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<InventoryVoucher> inventoryVoucher = new ArrayList<>();
///////////////////////////////////////////////////////////////////////////////////////////////

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public BigInteger getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(BigInteger voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Timestamp getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Timestamp voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherDateText() {
        return voucherDateText;
    }

    public void setVoucherDateText(String voucherDateText) {
        this.voucherDateText = voucherDateText;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigInteger getPartyAccountId() {
        return PartyAccountId;
    }

    public void setPartyAccountId(BigInteger partyAccountId) {
        PartyAccountId = partyAccountId;
    }

    public BigInteger getPurchaseLedgerId() {
        return PurchaseLedgerId;
    }

    public void setPurchaseLedgerId(BigInteger purchaseLedgerId) {
        PurchaseLedgerId = purchaseLedgerId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public List<AccountsVoucher> getAccountsVoucher() {
        return accountsVoucher;
    }

    public void setAccountsVoucher(List<AccountsVoucher> accountsVoucher) {
        this.accountsVoucher = accountsVoucher;
    }

    public List<InventoryVoucher> getInventoryVoucher() {
        return inventoryVoucher;
    }

    public void setInventoryVoucher(List<InventoryVoucher> inventoryVoucher) {
        this.inventoryVoucher = inventoryVoucher;
    }
}
