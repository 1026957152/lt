package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductTheatre {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;
    //只有但一票 才有 核销 之说。

    //    redeem tickets using a barcode or reference number. It's not enabled by default and not available or intended for resellers.
    // 景区闲逛票，  景区炸鸡票，景区天文馆票


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    private String theatre;//	The name of the Theatre showing the movie (tickets only).
    private long theatreId;//	The Id of the Theatre showing the movie (tickets only).
    private String movie;//	The name of the movie (tickets only).
    private String showDateTime;//	The Date time of the movie showing in local theatre time (tickets only).
    private String showDateTimeUtc;//	The Date time of the movie showing in UTC (tickets only).
    private long performanceNumber;//	The showtimes performance number (tickets only).
    private long auditorium;//	The auditorium the showtime is in (tickets only).


}
