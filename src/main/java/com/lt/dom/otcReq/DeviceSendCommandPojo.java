package com.lt.dom.otcReq;


//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import com.lt.dom.otcenum.EnumDeviceCommand;

import java.time.LocalDateTime;
import java.util.List;

public class DeviceSendCommandPojo {  // 这个就是机器了啊


    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public static class Command {  // 这个就是机器了啊
        private EnumDeviceCommand code;
        private Object value;

        public EnumDeviceCommand getCode() {
            return code;
        }

        public void setCode(EnumDeviceCommand code) {
            this.code = code;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }


}