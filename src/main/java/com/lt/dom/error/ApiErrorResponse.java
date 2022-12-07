package com.lt.dom.error;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {
    private HttpStatus code; ////The HTTP status code of error returned. Can be: 2xx, 4xx or 5xx.
    private Integer subcode; ////The HTTP status code of error returned. Can be: 2xx, 4xx or 5xx.


    private String key;   //  key	For API object errors, a short string from the list on the right hand side, describing the kind of error which occurred.

    private Object error;
    private String message;
    private String details;

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    ;
   // message	A human-readable message providing a short description about the error.
   //         details

    public ApiErrorResponse(HttpStatus code,String error, String message,String details) {
        super();
        this.code = code;
        this.error = error;
        this.message = message;
        this.details = details;
    }
    public ApiErrorResponse(HttpStatus _code,Integer code, String message,String details) {
        super();
        this.code = _code;
        this.error = code;
        this.message = message;
        this.details = details;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}