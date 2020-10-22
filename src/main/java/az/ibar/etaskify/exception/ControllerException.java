package az.ibar.etaskify.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
