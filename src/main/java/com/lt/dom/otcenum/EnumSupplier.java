package com.lt.dom.otcenum;

public enum EnumSupplier {
            _白云山(EnumSupplierDisplayType.畅游,"白云山",EnumSupplierType.A级景区),
            _碧麟湾(EnumSupplierDisplayType.畅游,"碧麟湾",EnumSupplierType.A级景区),
            _赤牛坬(EnumSupplierDisplayType.畅游,"赤牛坬",EnumSupplierType.A级景区),
            _动物园(EnumSupplierDisplayType.畅游,"动物园",EnumSupplierType.A级景区),
            _尔林兔(EnumSupplierDisplayType.畅游,"尔林兔",EnumSupplierType.A级景区),
            _二郎山(EnumSupplierDisplayType.畅游,"二郎山",EnumSupplierType.A级景区),
            _郝家桥(EnumSupplierDisplayType.畅游,"郝家桥",EnumSupplierType.A级景区),
            _红碱淖(EnumSupplierDisplayType.畅游,"红碱淖",EnumSupplierType.A级景区),
            _红石峡(EnumSupplierDisplayType.畅游,"红石峡",EnumSupplierType.A级景区),
            _军旅园(EnumSupplierDisplayType.畅游,"军旅园",EnumSupplierType.A级景区),
            _麻黄梁(EnumSupplierDisplayType.畅游,"麻黄梁",EnumSupplierType.A级景区),
            _清凉寺(EnumSupplierDisplayType.畅游,"清凉寺",EnumSupplierType.A级景区),
            _神龙山(EnumSupplierDisplayType.畅游,"神龙山",EnumSupplierType.A级景区),
            _杨家沟(EnumSupplierDisplayType.畅游,"杨家沟",EnumSupplierType.A级景区),
            _赵家峁(EnumSupplierDisplayType.畅游,"赵家峁",EnumSupplierType.A级景区),
            _镇北台(EnumSupplierDisplayType.畅游,"镇北台",EnumSupplierType.A级景区),
            _治沙连(EnumSupplierDisplayType.畅游,"治沙连",EnumSupplierType.A级景区),
            _白舍牛滩(EnumSupplierDisplayType.畅游,"白舍牛滩",EnumSupplierType.A级景区),
            _大美石窑(EnumSupplierDisplayType.畅游,"大美石窑",EnumSupplierType.A级景区),
            _圣都乐园(EnumSupplierDisplayType.畅游,"圣都乐园",EnumSupplierType.A级景区),
            _镇靖古堡(EnumSupplierDisplayType.畅游,"镇靖古堡",EnumSupplierType.A级景区),
            _李自城行宫(EnumSupplierDisplayType.畅游,"李自城行宫",EnumSupplierType.A级景区),
            _三道河子村(EnumSupplierDisplayType.畅游,"三道河子村",EnumSupplierType.A级景区),
            _陕北古风园(EnumSupplierDisplayType.畅游,"陕北古风园",EnumSupplierType.A级景区),
            _乡圪求河村(EnumSupplierDisplayType.畅游,"乡圪求河村",EnumSupplierType.A级景区),
            _塞上森林小镇(EnumSupplierDisplayType.畅游,"塞上森林小镇",EnumSupplierType.A级景区),
            _陕北民俗大观园(EnumSupplierDisplayType.畅游,"陕北民俗大观园",EnumSupplierType.A级景区),
            _绥德县现代创新农业园(EnumSupplierDisplayType.畅游,"绥德县现代创新农业园",EnumSupplierType.A级景区),

            _榆林大剧院(EnumSupplierDisplayType.剧院,"榆林大剧院",EnumSupplierType.剧院),
            _榆林剧院(EnumSupplierDisplayType.剧院,"榆林剧院",EnumSupplierType.剧院),
    _国际旅行社(EnumSupplierDisplayType.旅行,"国际旅行社",EnumSupplierType.TravelAgent),

    ;

    EnumSupplierDisplayType category;
    String name;
    EnumSupplierType type;
    EnumSupplier(EnumSupplierDisplayType category,String name,EnumSupplierType type) {

        this.category = category;
        this.type = type;
        this.name = name;
    }


    public EnumSupplierDisplayType getCategory() {
        return category;
    }

    public void setCategory(EnumSupplierDisplayType category) {
        this.category = category;
    }

    public EnumSupplierType getType() {
        return type;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
