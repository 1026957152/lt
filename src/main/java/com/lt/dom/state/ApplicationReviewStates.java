package com.lt.dom.state;

public enum ApplicationReviewStates {
    DRAFT,// 旅行社新建
    APPLY, //旅行社提交领票申请
    AWAITING_DETAIL_APPLY, // 旅投一审通过，旅行社上报具体人员
    DETAIL_APPLY, // 旅行社二次提交
    AWAITING_REDEEMING_旅客游玩中, // 旅投二审通过,游客在玩
    APPLY_FUND, // 旅行社提交结算申请,
    AWAITING_PAYMENT, // 旅投审核结算完成,
    FUND_DONE, // 财政局打款结束,


}

