package bsp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 19-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "item")
public class Item implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    //region Default Attribute
    @Column(name = "ActionDate")
    private Timestamp actionDate;

    @NotNull
    @Column(name = "ActionType")
    private int actionType;

    @NotNull
    @Column(name = "CompanyCode")
    private int companyCode;

    @Column(name = "UserCode")
    private BigInteger userCode;
    //endregion

    @Column(name = "Code")
    private String code;

    @NotNull
    @Column(name = "Name")
    private String name;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="GroupId")
    private Group group;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "UnitId")
    private Unit unit;

    //#Opening Balance

    @Column(name = "IsService")
    private boolean isService;

    @Column(name = "IsActive")
    private boolean isActive;

    @NotNull
    @Column(name = "IsLog")
    private boolean isLog;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemDetail> itemDetails =new ArrayList<>();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }
}
