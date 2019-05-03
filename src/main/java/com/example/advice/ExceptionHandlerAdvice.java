package com.example.advice;

import com.example.dto.ResponseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

/**@ClassName
 *@Description:  spring mvc的异常处理
 *@Data 2019/3/27
 *Author censhaojie
 */
@RestControllerAdvice //定义全局异常处理类
public class ExceptionHandlerAdvice {
    @ExceptionHandler({IllegalArgumentException.class}) //声明异常处理方法
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400 HttpStatus.BAD_REQUEST操作无效
    public ResponseInfo badRequestException(IllegalArgumentException exception){
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value()+"",exception.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)//403 资源不可访问
    public ResponseInfo FORRequestException(AccessDeniedException exception){
        return new ResponseInfo(HttpStatus.FORBIDDEN.value()+"",exception.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
            UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(Exception exception) {
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500 程序异常
    public ResponseInfo exception(Throwable throwable) {
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", throwable.getMessage());

    }
}
