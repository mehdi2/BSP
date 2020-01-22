package bsp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 25-Jun-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "cost_center")
public class CostCenter implements Serializable{

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

    @Column(name = "Code")
    private String code;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "ParentCode")
    private BigInteger parentCode;

    @NotNull
    @Column(name = "IsLog")
    private boolean isLog;

    //region @Transient Variable
        @Transient
        private String parentName;
    //endregion

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public BigInteger getUserCode() {
        return userCode;
    }

    public void setUserCode(BigInteger userCode) {
        this.userCode = userCode;
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

    public BigInteger getParentCode() {
        return parentCode;
    }

    public void setParentCode(BigInteger parentCode) {
        this.parentCode = parentCode;
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }

    //region Description

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    //endregion
}
