package com.lt.dom.controllerOct;

//import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
/*import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;*/
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API 返回结果
 *
 * @author wangjiao
 * @since 2020/11/14
 */
/*@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor*/
public class ApiResult implements Serializable {
  /** 响应码: 状态码为200才算请求成功 */
  private int code;

  /** 响应消息 */
  private String msg;

  /** 是否成功 */
  private boolean success;

  /** 响应数据 */
  private Object data;

  /** 响应时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 // @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date time;

  public ApiResult() {
    this.success = true;
    this.msg = ApiCode.SUCCESS.getMsg();
    this.code = ApiCode.SUCCESS.getCode();
    this.time = new Date(System.currentTimeMillis());
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public static ApiResult of() {
    return new ApiResult();
  }

  public static ApiResult of(final boolean result) {
    ApiResult commonResponse = ApiResult.of();
    commonResponse.setSuccess(result);
    commonResponse.setData(null);
    return commonResponse;
  }

  public static ApiResult of(final String msg) {
    ApiResult commonResponse = ApiResult.of();
    commonResponse.setSuccess(false);
    commonResponse.setCode(ApiCode.FAIL.getCode());
    commonResponse.setMsg(msg);
    return commonResponse;
  }

  public static ApiResult of(final Object data) {
    ApiResult commonResponse = ApiResult.of();
    commonResponse.setData(data);
    return commonResponse;
  }

  public static ApiResult of(final ApiCode resultCode) {
    return of(false, resultCode);
  }

  public static ApiResult of(final ApiCode resultCode, final Object data) {
    ApiResult commonResponse = of(false, resultCode);
    commonResponse.setData(data);
    return commonResponse;
  }

  public static ApiResult of(final boolean result, final ApiCode resultCode) {
    ApiResult commonResponse = ApiResult.of();
    commonResponse.setSuccess(result);
    commonResponse.setMsg(resultCode.getMsg());
    commonResponse.setCode(resultCode.getCode());
    return commonResponse;
  }

  public static ApiResult of(final boolean result, final Object data) {
    ApiResult commonResponse = ApiResult.of();
    commonResponse.setSuccess(result);
    commonResponse.setData(data);
    return commonResponse;
  }

  public static ApiResult of(final boolean result, final Object data, final ApiCode resultCode) {
    ApiResult commonResponse = of(result, resultCode);
    commonResponse.setData(data);
    return commonResponse;
  }

  public static ApiResult ok() {
    return of(true);
  }

  public static ApiResult ok(final Object data) {
    return of(data);
  }

  public static ApiResult ok(final ApiCode data) {
    return of(data);
  }

  public static ApiResult ok(final Object data, final ApiCode resultCode) {
    return of(true, data, resultCode);
  }

  public static ApiResult fail() {
    return of(false, ApiCode.FAIL);
  }

  public static ApiResult fail(final ApiCode data) {
    return of(false, data);
  }

  public static ApiResult fail(final String msg) {
    return of(msg);
  }

  public static ApiResult fail(ApiCode apiCode, Object data) {
    if (ApiCode.SUCCESS == apiCode) {
      throw new RuntimeException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
    }
    return of(apiCode, data);
  }

  public static ApiResult okMap(String key, Object value) {
    Map<String, Object> map = new HashMap<>();
    map.put(key, value);
    return ok(map);
  }
}
/*
————————————————
版权声明：本文为CSDN博主「一枚小爪哇」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_38066812/article/details/109964260*/
