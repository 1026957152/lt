package com.lt.dom.error;

import com.lt.dom.otcenum.EnumRedemptionfailures;
import com.lt.dom.otcenum.Enumfailures;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
        BookNotFoundException ex, WebRequest request) {

/*
        ApiErrorResponse response =
            new ApiErrorResponse(HttpStatus.NOT_FOUND,"error-0001",
                    EnumRedemptionfailures.resource_not_found.name(),"No book found with ID " + ex.getId());
        return new ResponseEntity<>(response, response.getCode());
*/

        ApiErrorResponse response =
                new ApiErrorResponse(HttpStatus.NOT_FOUND,EnumRedemptionfailures.resource_not_found.name(),
                        request.getContextPath(),"No book found with ID " + ex.getId());
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
                new ApiErrorResponse(HttpStatus.CONFLICT,"已经存在",
                        request.getContextPath(),ex.getError());
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
                        ex.getObject(),ex.getDetail());
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