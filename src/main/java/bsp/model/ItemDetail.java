package bsp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 19-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "item_detail")
public class ItemDetail implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ItemId")
    private Item item;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="GodownId")
    private Godown godown;

    @NotNull
    @Column(name = "Quantity")
    private int quantity;

    @NotNull
    @Column(name = "Rate")
    private Double rate;

    @NotNull
    @Column(name = "Amount")
    private Double amount;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Godown getGodown() {
        return godown;
    }

    public void setGodown(Godown godown) {
        this.godown = godown;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
