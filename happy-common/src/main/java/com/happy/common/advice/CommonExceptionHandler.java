package com.happy.common.advice;


import com.happy.common.enums.ExceptionEnum;
import com.happy.common.exception.CommonException;
import com.happy.common.ov.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResult> handleException(CommonException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }

}
