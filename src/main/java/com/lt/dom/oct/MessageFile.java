package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumProductMessage;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @ClassName Message
 * @Description TODO
 * @Author 树下魅狐
 * @Date 2020/4/28 0028 19:21
 * @Version since 1.0
 **/
public class MessageFile {

    private String message;

    public MessageFile(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}