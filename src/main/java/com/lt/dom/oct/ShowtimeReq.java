package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


public class ShowtimeReq {

    private long movie;//	The requested Theatre Number.


    private long layout;//	The total count of rows.
    private long theatre;//	The total count of columns.

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("sellUntilDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sellUntilDateTime;//	The list of seats in the layout.
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("showDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime showDateTime;//	The version of an auditorium layout. When a layout is changed, this will be incremented.

    private int maximumIntendedAttendance;//	The total count of rows.

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("visibilityDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visibilityDateTime;//	The total count of rows.

    public long getMovie() {
        return movie;
    }

    public void setMovie(long movie) {
        this.movie = movie;
    }

    public long getLayout() {
        return layout;
    }

    public void setLayout(long layout) {
        this.layout = layout;
    }

    public long getTheatre() {
        return theatre;
    }

    public void setTheatre(long theatre) {
        this.theatre = theatre;
    }

    public LocalDateTime getSellUntilDateTime() {
        return sellUntilDateTime;
    }

    public void setSellUntilDateTime(LocalDateTime sellUntilDateTime) {
        this.sellUntilDateTime = sellUntilDateTime;
    }

    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }

    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }

    public int getMaximumIntendedAttendance() {
        return maximumIntendedAttendance;
    }

    public void setMaximumIntendedAttendance(int maximumIntendedAttendance) {
        this.maximumIntendedAttendance = maximumIntendedAttendance;
    }

    public LocalDateTime getVisibilityDateTime() {
        return visibilityDateTime;
    }

    public void setVisibilityDateTime(LocalDateTime visibilityDateTime) {
        this.visibilityDateTime = visibilityDateTime;
    }
}
