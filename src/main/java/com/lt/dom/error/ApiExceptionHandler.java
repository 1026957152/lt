package com.lt.dom.error;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.lt.dom.otcenum.EnumRedemptionfailures;
import com.lt.dom.otcenum.Enumfailures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@RestControllerAdvice

public class ApiExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(NullPointerException ex, WebRequest request){

        ex.printStackTrace();
        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST,"ex.getError().name()",
                        ex.getMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());
    }




    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(ConstraintViolationException ex, WebRequest request){

        ex.printStackTrace();
        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST,Enumfailures.bad_request.name(),
                        ex.getMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());
    }


    @ExceptionHandler(Error403Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Error403Exception ex, WebRequest request){

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.FORBIDDEN,ex.getError().name(),
                        ex.getMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());
    }

    @ExceptionHandler(Error401Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Error401Exception ex, WebRequest request){

   //     String s =  messageSource.getMessage(ex.getError().getMessage_code(), null, Locale.getDefault());


        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED,ex.getError().name(),
                        ex.getMessage(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());
    }

    @ExceptionHandler(Quantity_exceededException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Quantity_exceededException ex, WebRequest request){

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED,EnumRedemptionfailures.quantity_exceeded.name(),
                        ex.getMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());
    }
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(TokenRefreshException ex, WebRequest request){

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED,EnumRedemptionfailures.resource_not_found.name(),
                        ex.getMessage(),ex.getError());
        return new ResponseEntity<>(response, response.getCode());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request){

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED,EnumRedemptionfailures.resource_not_found.name(),
                       ex.getMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            UserNotFoundException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.NOT_FOUND,Enumfailures.user_not_found.name(),
                        ex.getMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());

    }






    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
        BookNotFoundException ex, WebRequest request) {


        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.NOT_FOUND,ex.getError().name(),
                        ex.getMessage(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

/*    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error,"");
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getCode());
    }*/


    @ExceptionHandler(ExistException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            ExistException ex, WebRequest request) {

/*
        ApiErrorResponse response =
            new ApiErrorResponse(HttpStatus.NOT_FOUND,"error-0001",
                    EnumRedemptionfailures.resource_not_found.name(),"No book found with ID " + ex.getId());
        return new ResponseEntity<>(response, response.getCode());
*/

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.CONFLICT,ex.getError().name(),
                        ex.getMessage(),ex.getMessage());
        return new ResponseEntity<>(response, response.getCode());

    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            MethodArgumentNotValidException ex, WebRequest request) {

/*
        ApiErrorResponse response =
            new ApiErrorResponse(HttpStatus.NOT_FOUND,"error-0001",
                    EnumRedemptionfailures.resource_not_found.name(),"No book found with ID " + ex.getId());
        return new ResponseEntity<>(response, response.getCode());
*/

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),
                        ex.getMessage(),ex.getAllErrors().toString());
        return new ResponseEntity<>(response, response.getCode());

    }
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            HttpServerErrorException.InternalServerError ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.voucher_disabled.name(),
                        ex.getMessage(),"internal error");
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(voucher_disabledException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            voucher_disabledException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.voucher_disabled.name(),
                        ex.getMessage(),Enumfailures.voucher_disabled.name());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(Missing_customerException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            Missing_customerException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.missing_customer.name(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }


    @ExceptionHandler(Booking_not_pendingException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            Booking_not_pendingException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.booking_not_pending.name(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }
    @ExceptionHandler(No_voucher_suitable_for_publicationException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            No_voucher_suitable_for_publicationException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.no_voucher_suitable_for_publication.name(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(Missing_documentException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            Missing_documentException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.missing_documents.name(),
                        ex.getDetail(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            PaymentException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getError().name(),
                        ex.getMessage(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(User_already_be_guideException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            User_already_be_guideException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.user_already_be_guide.getText(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }




    @ExceptionHandler(User_realname_auth_failureException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            User_realname_auth_failureException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.user_realname_auth_failure.getText(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(Voucher_has_no_permistion_redeemException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            Voucher_has_no_permistion_redeemException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, Enumfailures.voucher_has_no_permistion_redeem.getText(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler(wx_login_errorException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            wx_login_errorException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Enumfailures.wx_login_error.getText(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }



    @ExceptionHandler(voucher_not_publishException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            voucher_not_publishException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Enumfailures.voucher_not_publish.getText(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }
    @ExceptionHandler(username_already_exists_errorException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            username_already_exists_errorException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }
    @ExceptionHandler(Campaign_inactiveException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            Campaign_inactiveException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(),
                        ex.getObject(),ex.getDetail());
        return new ResponseEntity<>(response, response.getCode());

    }

/*
    @ExceptionHandler(BadJwtException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            BadJwtException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.CONFLICT, Enumfailures.BadJwtException.name() ,
                        ex.getMessage(),ex.getMessage());
        return new ResponseEntity<>(response, response.getCode());

    }
*/



/*    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            BadCredentialsException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(),
                        ex.getLocalizedMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());

    }*/

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            MultipartException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),
                        ex.getLocalizedMessage(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ApiErrorResponse> handleApiException(
            MissingServletRequestParameterException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getParameterName(),
                        ex.getParameterType(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());

    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiErrorResponse> handleApiException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getName(),
                        ex.getErrorCode(),ex.getLocalizedMessage());
        return new ResponseEntity<>(response, response.getCode());

    }
/*
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "default error handler",request.getContextPath(),ex.getLocalizedMessage());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getCode());
    }
*/


/*    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }*/
}