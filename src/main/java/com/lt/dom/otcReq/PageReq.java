package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumOrderStatus;
import com.lt.dom.otcenum.EnumPayChannel;

import java.time.LocalDate;

public class PageReq {  // 这个就是机器了啊

    private int limit = 10;
    private int page = 1;





    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
