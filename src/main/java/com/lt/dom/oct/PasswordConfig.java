package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumActivityType;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class PasswordConfig extends Base{


    private Integer LimitPasswordReuse;
    private Integer MaxConsecutiveDupeChars;

    private Integer MaximumPasswordAge;
    private Integer MinimumPasswordAge;

    private Integer AllowedFailedAttempts;
    private Integer LockoutDuration;
    private Integer UpperCaseRequired;
    private Integer LowerCaseRequired;
    private Integer SpecialCharacterRequired;
    private Integer NumericRequired;
    private Integer MinimumCharacterCount;

    public Integer getLimitPasswordReuse() {
        return LimitPasswordReuse;
    }

    public void setLimitPasswordReuse(Integer limitPasswordReuse) {
        LimitPasswordReuse = limitPasswordReuse;
    }

    public Integer getMaxConsecutiveDupeChars() {
        return MaxConsecutiveDupeChars;
    }

    public void setMaxConsecutiveDupeChars(Integer maxConsecutiveDupeChars) {
        MaxConsecutiveDupeChars = maxConsecutiveDupeChars;
    }

    public Integer getMaximumPasswordAge() {
        return MaximumPasswordAge;
    }

    public void setMaximumPasswordAge(Integer maximumPasswordAge) {
        MaximumPasswordAge = maximumPasswordAge;
    }

    public Integer getMinimumPasswordAge() {
        return MinimumPasswordAge;
    }

    public void setMinimumPasswordAge(Integer minimumPasswordAge) {
        MinimumPasswordAge = minimumPasswordAge;
    }

    public Integer getAllowedFailedAttempts() {
        return AllowedFailedAttempts;
    }

    public void setAllowedFailedAttempts(Integer allowedFailedAttempts) {
        AllowedFailedAttempts = allowedFailedAttempts;
    }

    public Integer getLockoutDuration() {
        return LockoutDuration;
    }

    public void setLockoutDuration(Integer lockoutDuration) {
        LockoutDuration = lockoutDuration;
    }

    public Integer getUpperCaseRequired() {
        return UpperCaseRequired;
    }

    public void setUpperCaseRequired(Integer upperCaseRequired) {
        UpperCaseRequired = upperCaseRequired;
    }

    public Integer getLowerCaseRequired() {
        return LowerCaseRequired;
    }

    public void setLowerCaseRequired(Integer lowerCaseRequired) {
        LowerCaseRequired = lowerCaseRequired;
    }

    public Integer getSpecialCharacterRequired() {
        return SpecialCharacterRequired;
    }

    public void setSpecialCharacterRequired(Integer specialCharacterRequired) {
        SpecialCharacterRequired = specialCharacterRequired;
    }

    public Integer getNumericRequired() {
        return NumericRequired;
    }

    public void setNumericRequired(Integer numericRequired) {
        NumericRequired = numericRequired;
    }

    public Integer getMinimumCharacterCount() {
        return MinimumCharacterCount;
    }

    public void setMinimumCharacterCount(Integer minimumCharacterCount) {
        MinimumCharacterCount = minimumCharacterCount;
    }
}
