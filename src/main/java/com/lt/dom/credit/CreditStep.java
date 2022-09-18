package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class CreditStep {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    private LocalDateTime created_at_新建时间;

    private String operator_user_name_操作员姓名;

    private long request;
    private EnumCreditAction operation_操作类型;

    private String operator_user_code_操作员编号;
    private EnumCreditStatus from_;
    private EnumCreditStatus to_;

    public String getOperator_user_code_操作员编号() {
        return operator_user_code_操作员编号;
    }

    public void setOperator_user_code_操作员编号(String operator_user_code_操作员编号) {
        this.operator_user_code_操作员编号 = operator_user_code_操作员编号;
    }

    public CreditStep(RequestCredit requestCredit, LocalDateTime now, String 信易贷, EnumCreditAction 推送) {
        this.request = requestCredit.getId();
        this.created_at_新建时间 = now;
        this.operator_user_name_操作员姓名 = 信易贷;
        this.operator_user_code_操作员编号 = 信易贷;
        this.operation_操作类型 = 推送;
    }

    public CreditStep() {

    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreated_at_新建时间() {
        return created_at_新建时间;
    }

    public void setCreated_at_新建时间(LocalDateTime created_at) {
        this.created_at_新建时间 = created_at;
    }

    public String getOperator_user_name_操作员姓名() {
        return operator_user_name_操作员姓名;
    }

    public void setOperator_user_name_操作员姓名(String created_by) {
        this.operator_user_name_操作员姓名 = created_by;
    }

    public EnumCreditAction getOperation_操作类型() {
        return operation_操作类型;
    }

    public void setOperation_操作类型(EnumCreditAction operation) {
        this.operation_操作类型 = operation;
    }

    public void setFrom_(EnumCreditStatus from) {
        this.from_ = from;
    }

    public EnumCreditStatus getFrom_() {
        return from_;
    }

    public void setTo_(EnumCreditStatus to) {
        this.to_ = to;
    }

    public EnumCreditStatus getTo_() {
        return to_;
    }
}
