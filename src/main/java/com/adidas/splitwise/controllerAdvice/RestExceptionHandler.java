package com.adidas.splitwise.controllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import com.adidas.splitwise.customExceptions.EntityAlreadyExistsException;
import com.adidas.splitwise.customExceptions.EntityNotFoundException;
import com.adidas.splitwise.customResponses.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Class to handle exceptions and return custom exceptions responses
 *
 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
 * @class RestExceptionHandler
 * @date 18/02/2023
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Method to handle when an {@link EntityNotFoundException} is thrown
	 * 
	 * @return {@link ResponseEntity}
	 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
	 * @date 18/02/2023
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex){
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}

	/**
	 * Method to handle when an {@link EntityAlreadyExistsException} is thrown
	 *
	 * @return {@link ResponseEntity}
	 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
	 * @date 18/02/2023
	 */
	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex){
		return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	/**
	 * Method to handle when a {@link MethodArgumentNotValidException} is thrown
	 * 
	 * @return {@link ResponseEntity}
	 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
	 * @date 18/02/2023
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {	
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map( err -> err.getField().concat(": ").concat(err.getDefaultMessage()))
				.collect(Collectors.toList());
		
		return buildResponseEntity(new ApiError(status, "Validation error", ex, errors));
	}

	/**
	 * Helper method to create a {@link ResponseEntity} from a {@link ApiError}
	 * 
	 * @return {@link ResponseEntity}
	 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
	 * @date 18/02/2023
	 */
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	        return new ResponseEntity<>(apiError, apiError.getStatus());
	    }
}
