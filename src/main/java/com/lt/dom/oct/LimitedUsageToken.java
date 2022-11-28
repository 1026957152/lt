package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class LimitedUsageToken extends Base{


    private EnumOperationType operation;


    private String token ;//digital tickets or PDF tickets
    private String soure ;//digital tickets or PDF tickets

    private LocalDateTime expiry_date;

    public EnumOperationType getOperation() {
        return operation;
    }

    public void setOperation(EnumOperationType operation) {
        this.operation = operation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSoure() {
        return soure;
    }

    public void setSoure(String soure) {
        this.soure = soure;
    }

    public LocalDateTime getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDateTime expiry_date) {
        this.expiry_date = expiry_date;
    }
}
