package az.ibar.etaskify.exception;

import az.ibar.etaskify.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity handle(ControllerException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ApiResponse(false, ex.getMessage()));
    }

}