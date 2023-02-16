package com.onpier.demo.librarydemo.web;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ErrorController
   extends ResponseEntityExceptionHandler
{

   @ExceptionHandler
   @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
   protected @ResponseBody String error(Exception ex)
   {
      return ex.toString();
   }
}
