package bsp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CM Mehdi on 10/22/2017.
 * Mobile: +8801711972985
 * email: mehdi.uiu@gmail.com
 */

@Entity
@Table(name = "[group]")
public class Group implements Cloneable {

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

    //It will be show by Order by List
    @Column(name = "Indexing")
    private int indexing;

    //Default groups. This Data is not edit and Delete only edit name.
    @Column(name = "IsDefault")
    private boolean Default;

    //1.Account Group 2.Stock/Inventory Group
    @NotNull
    @Column(name = "Type")
    private int type;

    @Column(name = "Code")
    private String code;

    @NotNull
    @Column(name = "Name")
    private String name;

    @NotNull
    @Column(name = "ParentCode")
    private BigInteger parentCode;

    @NotNull
    @Column(name = "PrimaryCode")
    private int primaryCode;

    @NotNull
    @Column(name = "IsLog")
    private boolean isLog;

    //region @Transient Variable
        @Transient
        private String under;
        @Transient
        private String primaryCodeType;
    //endregion

///////////////////////////////////////////////////////////////////////

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

    public int getIndexing() {
        return indexing;
    }

    public void setIndexing(int indexing) {
        this.indexing = indexing;
    }

    public boolean isDefault() {
        return Default;
    }

    public void setDefault(boolean aDefault) {
        Default = aDefault;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getPrimaryCode() {
        return primaryCode;
    }

    public void setPrimaryCode(int primaryCode) {
        this.primaryCode = primaryCode;
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }

    //region @Transient Variable
    public String getUnder() {
        return under;
    }

    public void setUnder(String under) {
        this.under = under;
    }

    public String getPrimaryCodeType() {
        return primaryCodeType;
    }

    public void setPrimaryCodeType(String primaryCodeType) {
        this.primaryCodeType = primaryCodeType;
    }
    //endregion
}