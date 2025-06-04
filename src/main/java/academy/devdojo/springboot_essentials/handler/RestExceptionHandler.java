package academy.devdojo.springboot_essentials.handler;

import academy.devdojo.springboot_essentials.exception.BadRequestException;
import academy.devdojo.springboot_essentials.repository.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler (BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(
              BadRequestExceptionDetails.builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.BAD_REQUEST.value())
                      .title("Bad Request Exception, Check the documentarion")
                      .details(badRequestException.getMessage())
                      .developerMessage(badRequestException.getClass().getName())
                      .build(),HttpStatus.BAD_REQUEST
        );

    }
}
