package com.lt.dom.otcenum;

public enum Enum_信合税务_item {
___信用等级信息_ID(Enum_信合税务.信用等级信息,"ID","id"),
___信用等级信息_NSRMC(Enum_信合税务.信用等级信息,"NSRMC","企业名称"),
___信用等级信息_SHXYDM(Enum_信合税务.信用等级信息,"SHXYDM","统一社会信用代码"),
___信用等级信息_PJND(Enum_信合税务.信用等级信息,"PJND","评审年度"),
___信用等级信息_PDJB(Enum_信合税务.信用等级信息,"PDJB","纳税信用等级"),
___税票信息_ID(Enum_信合税务.税票信息,"ID","id"),
___税票信息_XFMC(Enum_信合税务.税票信息,"XFMC","销售名称"),
___税票信息_XFSBH(Enum_信合税务.税票信息,"XFSBH","销售识别号"),
___税票信息_XFHYDM(Enum_信合税务.税票信息,"XFHYDM","销售行业代码"),
___税票信息_GFMC(Enum_信合税务.税票信息,"GFMC","购买名称"),
___税票信息_GFSBH(Enum_信合税务.税票信息,"GFSBH","购买识别号"),
___税票信息_GFHYDM(Enum_信合税务.税票信息,"GFHYDM","购买行业代码"),
___税票信息_SPBM(Enum_信合税务.税票信息,"SPBM","商品编码"),
___税票信息_SPMC(Enum_信合税务.税票信息,"SPMC","商品名称"),
___税票信息_JE(Enum_信合税务.税票信息,"JE","同类商品月销售金额"),
___税票信息_KPCS(Enum_信合税务.税票信息,"KPCS","月开票次数"),
___税票信息_KPYF(Enum_信合税务.税票信息,"KPYF","开票月份"),
___欠税信息_ID(Enum_信合税务.欠税信息,"ID","id"),
___欠税信息_DJXH(Enum_信合税务.欠税信息,"DJXH","登记序号"),
___欠税信息_NSRMC(Enum_信合税务.欠税信息,"NSRMC","企业名称"),
___欠税信息_SHXYDM(Enum_信合税务.欠税信息,"SHXYDM","统一社会信用代码"),
___欠税信息_SFQS(Enum_信合税务.欠税信息,"SFQS","当前是否欠税"),
___欠税信息_QSJE(Enum_信合税务.欠税信息,"QSJE","欠税金额"),
___欠税信息_NDYF(Enum_信合税务.欠税信息,"NDYF","月份"),
___纳税信息_ID(Enum_信合税务.纳税信息,"ID","id"),
___纳税信息_NSRMC(Enum_信合税务.纳税信息,"NSRMC","企业名称"),
___纳税信息_SHXYDM(Enum_信合税务.纳税信息,"SHXYDM","统一社会信用代码"),
___纳税信息_ZZSYNSE(Enum_信合税务.纳税信息,"ZZSYNSE","增值税应纳税金额（万元）"),
___纳税信息_ZZSSJJE(Enum_信合税务.纳税信息,"ZZSSJJE","增值税实际缴税金额（万元）"),
___纳税信息_SDSYNSE(Enum_信合税务.纳税信息,"SDSYNSE","企业所得税应纳税金额（万元）"),
___纳税信息_SDSSJJE(Enum_信合税务.纳税信息,"SDSSJJE","企业所得税实际缴税金额（万元）"),
___纳税信息_RKYF(Enum_信合税务.纳税信息,"RKYF","缴税日期"),
___连续缴税周期信息_ID(Enum_信合税务.连续缴税周期信息,"ID","id"),
___连续缴税周期信息_SHXYDM(Enum_信合税务.连续缴税周期信息,"SHXYDM","统一社会信用代码"),
___连续缴税周期信息_NSRMC(Enum_信合税务.连续缴税周期信息,"NSRMC","企业名称"),
___连续缴税周期信息_LYYF(Enum_信合税务.连续缴税周期信息,"LYYF","连续正常缴税周期(月)"),
___连续缴税周期信息_ZJRKYF(Enum_信合税务.连续缴税周期信息,"ZJRKYF","月份"),
___连续缴税周期信息_JSSJ(Enum_信合税务.连续缴税周期信息,"JSSJ","计算时间"),
___非正常户信息_ID(Enum_信合税务.非正常户信息,"ID","id"),
___非正常户信息_NSRMC(Enum_信合税务.非正常户信息,"NSRMC","纳税人名称"),
___非正常户信息_SHXYDM(Enum_信合税务.非正常户信息,"SHXYDM","社会信用代码"),
___非正常户信息_RDRQ(Enum_信合税务.非正常户信息,"RDRQ","认定日期"),
___非正常户信息_SFFZC(Enum_信合税务.非正常户信息,"SFFZC","是否非正常"),



            ;


    Enum_信合税务 type;
    String name;
    String mark;

    Enum_信合税务_item(Enum_信合税务 type, String name, String mark) {

        this.type = type;
        this.name = name;
        this.mark = mark;
    }
}
