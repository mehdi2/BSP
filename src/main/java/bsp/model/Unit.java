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
@Table(name = "unit")
public class Unit implements Serializable{

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

    //Relational Table Company
    @NotNull
    @Column(name = "CompanyCode")
    private int companyCode;

    //Relational Table User
    @Column(name = "UserCode")
    private BigInteger userCode;
    //endregion

    //////////////////////////////
    @NotNull
    @Column(name = "UnitType")
    private int unitType;

    @NotNull
    @Column(name = "UnitSymbol")
    private String unitSymbol;

    @Column(name = "FormalName")
    private String formalName;

    @Column(name = "NumberOfDecimalPlace")
    private int numberOfDecimalPlace;

    @NotNull
    @Column(name = "IsLog")
    private boolean isLog;

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

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public int getNumberOfDecimalPlace() {
        return numberOfDecimalPlace;
    }

    public void setNumberOfDecimalPlace(int numberOfDecimalPlace) {
        this.numberOfDecimalPlace = numberOfDecimalPlace;
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }
}
