package com.lt.dom.proto.req;

import com.lt.dom.proto.req.BusAndStopUpdateMessage;

import java.util.List;


//https://github.com/Droy24/YourBus/blob/master/src/main/java/com/entity/Seat.java

public class BusVehicleSummaryVo {


        List<BusAndStopUpdateMessage.LineUpdateMessage>  lines;

        public List<BusAndStopUpdateMessage.LineUpdateMessage> getLines() {
            return lines;
        }

        public void setLines(List<BusAndStopUpdateMessage.LineUpdateMessage> lines) {
            this.lines = lines;
        }


    }
