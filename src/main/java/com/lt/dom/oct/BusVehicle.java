package com.lt.dom.oct;

import cn.hutool.core.date.DateTime;
import com.lt.dom.BusSeat;
import com.lt.dom.EnumTag;
import com.lt.dom.otcenum.EnumBusType;
import com.lt.dom.otcenum.EnumBussinessType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


//https://github.com/Droy24/YourBus/blob/master/src/main/java/com/entity/Seat.java
@Entity
public class BusVehicle extends Base{

    private String code;

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    private Long supplier;
    @Column(name = "platename")
    private String plateName;

    @Column(name = "seatsbooked")
    private Integer seatsbooked = 0;

    @Column(name = "totalseats")
    private Integer totalSeats;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable
    private List<BusSeat> seat = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    private BusRoute route;

    @Column(name = "dailystarttime")
    private DateTime dailyStartTime;

    @Column(name = "dailyStoptime")
    private DateTime dailyStopTime;

    @Column(name = "busType")
    private EnumBusType busType;

    public BusVehicle() {

    }
/*

    public Bus(BusDTO busdto) {
        // if(busId!=null)
        this.busId = busdto.getBusId();
        this.busType = busdto.getBusType();
        this.totalSeats = busdto.getTotalSeats();
        this.seatsbooked = busdto.getSeatsbooked();
        if (busdto.getDailyStartTime() != null) {
            this.dailyStartTime = busdto.getDailyStartTime();
        }
        if (busdto.getDailyStopTime() != null)
        {
            this.dailyStopTime = busdto.getDailyStopTime();
        }
        this.plateName = busdto.getPlateName();
        if (busdto.getRoute() != null)
            this.route = new Route(busdto.getRoute());
        if (busdto.getSeat() != null && !busdto.getSeat().isEmpty())
        {
            this.seat = busdto.getSeat().stream().map(Seat::new).collect(Collectors.toList());
        }
        else
        {
            System.out.prIntegeregerln("in bus seat constructor");
            this.seat = createSeats(this.totalSeats);
            System.out.prIntegerln("seat created " + seat.size());
        }
    }
*/

    public static List<Seat> createSeats(Integer n) {
        List<Seat> seats = new ArrayList<>();
        for (Integer i = 0; i < n; i++) {
            Seat seat = new Seat();
            seat.setSeatName("A-"+i);
            seats.add(seat);
        }
        return seats;
    }

    public BusVehicle(String plateName, EnumBusType busType, Integer seatsBooked) {

        this.plateName = plateName;
        this.busType = busType;
        this.seatsbooked = seatsBooked;
    }

    public Integer getSeatsbooked() {
        return this.seatsbooked;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }

    public EnumBusType getBusType() {
        return busType;
    }

    public void setBusType(EnumBusType busType) {
        this.busType = busType;
    }

    public List<BusSeat> getSeat() {
        return seat;
    }

    public void setSeat(List<BusSeat> seat) {
        this.seat = seat;
    }

    public void setSeatsbooked(Integer seatsbooked) {
        this.seatsbooked = seatsbooked;
    }



    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public DateTime getDailyStartTime() {
        return dailyStartTime;
    }

    public void setDailyStartTime(DateTime dailyStartTime) {
        this.dailyStartTime = dailyStartTime;
    }

    public DateTime getDailyStopTime() {
        return dailyStopTime;
    }

    public void setDailyStopTime(DateTime dailyStopTime) {
        this.dailyStopTime = dailyStopTime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

 /*   @Override
    public Integer hashCode() {
        final Integer prime = 31;
        Integer result = 1;
        result = prime * result + ((busId == null) ? 0 : busId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bus other = (Bus) obj;
        if (busId == null) {
            if (other.busId != null)
                return false;
        } else if (!busId.equals(other.busId))
            return false;
        return true;
    }*/
}
