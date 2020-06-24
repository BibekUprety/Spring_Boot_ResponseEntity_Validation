package com.bibek.validitaion;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@SuppressWarnings({"unchecked","rawtypes"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handlesUserNotFoundException(RecordNotFoundException recordNotFoundException, WebRequest webRequest){
        List<String> details = new ArrayList<>();
        details.add(recordNotFoundException.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object>handlesAllException(Exception exception,WebRequest webRequest){
        List<String> details=new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error=new ErrorResponse("Server Error!",details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);

    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> details=new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()){
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error=new ErrorResponse("Validation Failed!",details);
        return new ResponseEntity(error,HttpStatus.NOT_FOUND);
    }
}
