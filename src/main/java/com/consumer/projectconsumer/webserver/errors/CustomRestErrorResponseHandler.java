package com.consumer.projectconsumer.webserver.errors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomRestErrorResponseHandler extends ResponseEntityExceptionHandler {


    /**
     * Gets HttpClientErrorException.BadRequest and returns ResponseEntity object with CustomError
     * @param badRequestError
     * @return
     */
    public static ResponseEntity<?> handleBadRequest(HttpClientErrorException.BadRequest badRequestError) {
        String error = "Bad request";
        final CustomError ce = new CustomError(badRequestError.getStatusCode(), badRequestError.getMessage(), error);

        return new ResponseEntity<Object>(ce, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets ID that is not found and returns ResponseEntity object with 404 status code
     * @param id
     * @return
     */
    public static ResponseEntity<?> handleNotFoundException(Integer id) {
        String message = "'" + id + "' not found!";
        final CustomError ce = new CustomError(HttpStatus.NOT_FOUND, message, message);
        return new ResponseEntity<Object>(ce, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


    public static ResponseEntity<?> handleInternalServerError(Exception e) {
        String message = "Something bad happened";
        final CustomError ce = new CustomError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), message);
        return new ResponseEntity<Object>(ce, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
