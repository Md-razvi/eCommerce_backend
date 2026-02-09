package com.e_commerce.project.exception;


import com.e_commerce.project.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validExceptionsFound(MethodArgumentNotValidException e){
        Map<String,String> response=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String error=((FieldError)err).getField();
            String message=err.getDefaultMessage();
            response.put(error,message);

        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIResponse>methodResourceNotFound(ResourceNotFound e){
        String message1=e.getMessage();
        APIResponse apiResponse=new APIResponse(message1,false);
        return new ResponseEntity<> (apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse>methodAPIExceptionFound(ApiException e){
        String message1=e.getMessage();
        APIResponse apiResponse=new APIResponse(message1,false);
        return new ResponseEntity<> (apiResponse,HttpStatus.CONFLICT);
    }

}
