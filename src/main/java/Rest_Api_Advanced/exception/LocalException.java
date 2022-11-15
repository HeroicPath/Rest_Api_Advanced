package Rest_Api_Advanced.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data @AllArgsConstructor
public class LocalException extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;
}
