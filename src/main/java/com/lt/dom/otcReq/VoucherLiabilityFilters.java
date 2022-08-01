package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumOrderStatus;
import com.lt.dom.otcenum.EnumPayChannel;

import java.time.LocalDate;

public class VoucherLiabilityFilters {  // 这个就是机器了啊

    private LocalDate Voucher_Code;//: Allows you to search for a specific voucher code.
    private LocalDate Voucher_Status;//:  By default, both issued and partially redeemed will be selected. You can also select redeemed.
    private LocalDate Issued;//: Allows you to filter by the date a voucher was issued/created.
    private LocalDate Expiry;//: Allows you to filter by the date of expiry.
    private LocalDate Internal_Reference;//: Allows you to filter by your custom (or auto generated) internal reference.

}
