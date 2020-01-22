package bsp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 11-Jan-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "inventory_voucher")
public class InventoryVoucher{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="VoucherId")
    private Voucher voucher;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="GodownId")
    private Godown godown;

//    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ItemId")
    private Item item;

    @Column(name = "Quantity")
    private float quantity;

    @Column(name = "Rate")
    private float rate;

    @Column(name = "Amount")
    private Double amount;

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

    public Godown getGodown() {
        return godown;
    }

    public void setGodown(Godown godown) {
        this.godown = godown;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

}
