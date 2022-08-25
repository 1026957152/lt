package com.lt.dom.requestvo;

import java.time.LocalDateTime;
import java.util.List;

public class VoucherBatch {
    private int total_count;
    private int total_amount;
    private LocalDateTime Batch_Date_From;
    private LocalDateTime Batch_Date_Thru;

    private List<String> attachments;

    private int pull_number;

}
