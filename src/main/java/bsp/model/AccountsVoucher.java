package bsp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 11-Jan-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "accounts_voucher")
public class AccountsVoucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="VoucherId")
    private Voucher voucher;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LedgerId")
    private Ledger ledger;

    @Column(name = "DebitAmount")
    private Double debitAmount;

    @Column(name = "CreditAmount")
    private Double creditAmount;

//    @NotNull
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="CostCenterId")
//    private CostCenter costCenter;

//    @Column(name = "CostCenterId")
    @OneToOne()
    @JoinColumn(name = "CostCenterId", referencedColumnName = "id")
    private CostCenter costCenter;

    @Column(name = "Narration")
    private String narration;
////////////////////////////////////////////////////////


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
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

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
