package com.lt.dom.config;

public final class Constants {
    public static final double PI = 3.14159265359;
    public static final String WX_OPEN_REDIRECT_URL = "";
    public static final String WEB_SOCKET_URL = "ws://10.0.0.41:8080/position/socket";
    public static final String WEB_SOCKET_BUS_TOPIC = "/topic/notification";


    static final double GOLDEN_RATIO = 1.6180;
    static final double GRAVITATIONAL_ACCELERATION = 9.8;
    static final double EULERS_NUMBER = 2.7182818284590452353602874713527;
    public static final String STATIC_PATH = "static";

    public static final String STATIC_RECOURCE_PATH="http://yulinmei.cn:8080/oct/"+ Constants.STATIC_PATH+"/" ;
            ;

    public enum Operation {
        ADD,
        SUBTRACT,
        DIVIDE,
        MULTIPLY
    }
    
    private Constants() {
        
    }
}