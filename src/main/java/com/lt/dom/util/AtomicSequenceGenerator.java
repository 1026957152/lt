package com.lt.dom.util;





import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicSequenceGenerator implements SequenceGenerator {

    Integer initalValue ;
    private AtomicLong value ;

    public AtomicSequenceGenerator(int i) {
        this.initalValue = i;
        this.value = new AtomicLong(initalValue);
    }

    @Override
    public long getNext() {
        return value.getAndIncrement();
    }
    @Override
    public String getNextString() {

        return StringUtils.leftPad(value.getAndIncrement()+"", 10, "0");

    }
}