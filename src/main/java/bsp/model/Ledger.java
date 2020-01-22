package bsp.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 21-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Entity
@Table(name = "ledger")
public class Ledger implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private BigInteger id;

    //region Default Attribute
    @Column(name = "ActionDate")
    private Timestamp actionDate;

    @NotNull
    @Column(name = "CompanyCode")
    private int companyCode;

    @Column(name = "UserCode")
    private BigInteger userCode;

    @NotNull
    @Column(name = "ActionType")
    private int actionType;
    //endregion

    @Column(name = "Code")
    private String code;

//    ALTER TABLE mytbl ADD UNIQUE (columnName);
    @NotNull
    @Column(name = "Name")
    private String name;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "GroupId")
    private Group group;

    @Column(name = "OpeningBalance")
    @ColumnDefault("0.0")
    private Double openingBalance=0.0;

    @Column(name = "IsDebit")
    private boolean isDebit;

    @Column(name = "IsCostCenter")
    private boolean isCostCenter;

    @Column(name = "IsActive")
    private boolean isActive;

    @NotNull
    @Column(name = "IsLog")
    private boolean isLog;

    //region @Transient Variable
        @Transient
        private String ParentName;

        @Transient
        private int debit;
    //endregion

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

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public boolean isCostCenter() {
        return isCostCenter;
    }

    public void setCostCenter(boolean costCenter) {
        isCostCenter = costCenter;
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

    //region @Transient Variable
        public String getParentName() {
            return ParentName;
        }

        public void setParentName(String parentName) {
            ParentName = parentName;
        }

        public int getDebit() {
            return debit;
        }

        public void setDebit(int debit) {
            debit = debit;
        }
    //endregion
}
