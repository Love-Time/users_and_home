package zuzex.test.home.advice;





import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import zuzex.test.home.exception.ObjectNotFoundException;
import zuzex.test.home.service.BindingErrorsService;


@ControllerAdvice
public class DefaultAdvice  {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<IncorrectDataResponse> handleIncorrectDataException(MethodArgumentNotValidException ex) {
        System.out.println("GJFJGFJGLKFJGklf");
        IncorrectDataResponse incorrectDataResponse = new IncorrectDataResponse(BindingErrorsService.getErrors(ex.getFieldErrors()));
        return new ResponseEntity<>(incorrectDataResponse, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Response> handleAllExceptions(Exception ex) {
//        Response response = new Response(ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }



    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Response> ObjectNotFoundExceptionHandler(ObjectNotFoundException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
