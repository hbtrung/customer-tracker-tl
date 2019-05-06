package com.trungho.crm.demo.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionController {

	private Logger logger = Logger.getLogger(getClass().getName());
	
    @ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<CustomerErrorResponse> handleException(HttpServletRequest request, CustomerNotFoundException e) {
    	logger.log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
		CustomerErrorResponse error = new CustomerErrorResponse(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception e)   {
        logger.log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
        return "error/error";
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Exception e)   {
        logger.log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
        return "error/404";
    }
    
//	@ExceptionHandler
//	public ResponseEntity<CustomerErrorResponse> handleException(Exception exc) {
//		CustomerErrorResponse error = new CustomerErrorResponse(
//				HttpStatus.BAD_REQUEST.value(), 
//				exc.getMessage());
//		
//		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//	}
}
