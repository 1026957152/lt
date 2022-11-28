package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumSmsVerificationType;
import com.lt.dom.otcenum.EnumVefifyStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken extends Base{
    private static final int EXPIRATION = 60 * 24;

    @Enumerated(EnumType.STRING)
    private EnumSmsVerificationType type;

    private String token;

    public EnumSmsVerificationType getType() {
        return type;
    }

    public void setType(EnumSmsVerificationType type) {
        this.type = type;
    }

/*    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    */
    @Transient
    private User user;
    
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    private EnumVefifyStatus status;
    private String userCode;


    @NotNull
    private String uuid;

    @NotNull
    private String phone;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public VerificationToken() {

    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStatus(EnumVefifyStatus status) {
        this.status = status;
    }

    public EnumVefifyStatus getStatus() {
        return status;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
    // standard constructors, getters and setters
}