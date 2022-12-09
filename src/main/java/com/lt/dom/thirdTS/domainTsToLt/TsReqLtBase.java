package com.lt.dom.thirdTS.domainTsToLt;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lt.dom.thirdTS.EnumMethord;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.constraints.NotNull;



//在Payment类上加上这些信息
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "method"
)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = TsReqLt产品列表.class, name = "item_list"),
        @JsonSubTypes.Type(value = TsReqLt下单接口.class, name = "item_orders"),
        @JsonSubTypes.Type(value = TsReqLt退单接口.class, name = "item_refund"),
        @JsonSubTypes.Type(value = TsReqLt重发接口_修改订单.class, name = "item_orders_modify")

})
public class TsReqLtBase {

    @NotNull
    private EnumMethord method ;//:接口方法名,固定值：item_list


    @NotNull
    private String _sig;//:请求签名，见说明文档

    @NotNull
    private String _pid;//:合作伙伴id

    @NotNull
    private String format;//:返回数据格式，json

    public static TsReqLtBase from(MultiValueMap<String, String> ob_) {

        TsReqLtBase tsReqLtBase = new TsReqLtBase();
        tsReqLtBase.set_pid((String)ob_.getFirst("_pid"));
        tsReqLtBase.setFormat((String)ob_.getFirst("format"));
        tsReqLtBase.set_sig((String)ob_.getFirst("_sig"));
        tsReqLtBase.setMethod(EnumMethord.valueOf((String)ob_.getFirst("method")));

        return tsReqLtBase;
    }

    public EnumMethord getMethod() {
        return method;
    }

    public void setMethod(EnumMethord method) {
        this.method = method;
    }

    public String get_sig() {
        return _sig;
    }

    public void set_sig(String _sig) {
        this._sig = _sig;
    }

    public String get_pid() {
        return _pid;
    }

    public void set_pid(String _pid) {
        this._pid = _pid;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
