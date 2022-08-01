package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RoyaltySettlement {


    @Id
    private long id;
    private long chargeId;
    private long reservationId;
    private int amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoyaltySettlement() {

    }

    public RoyaltySettlement(long partnerId, int amount) {

    }

    public void setChargeId(long chargeId) {
        this.chargeId = chargeId;
    }

    public long getChargeId() {
        return chargeId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    private long count;//结算的总笔数。
    private int count_succeeded;//结算成功的笔数。
    private int count_failed;//结算失败的笔数。
    private int count_canceled;//取消的笔数。
    private int time_finished;//完成时间，用 Unix 时间戳表示。

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getCount_succeeded() {
        return count_succeeded;
    }

    public void setCount_succeeded(int count_succeeded) {
        this.count_succeeded = count_succeeded;
    }

    public int getCount_failed() {
        return count_failed;
    }

    public void setCount_failed(int count_failed) {
        this.count_failed = count_failed;
    }

    public int getCount_canceled() {
        return count_canceled;
    }

    public void setCount_canceled(int count_canceled) {
        this.count_canceled = count_canceled;
    }

    public int getTime_finished() {
        return time_finished;
    }

    public void setTime_finished(int time_finished) {
        this.time_finished = time_finished;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
