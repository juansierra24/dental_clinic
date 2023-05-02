package com.jcst.dentalclinic.customResponses;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * Custom error class to centralize errors into a unique object type
 * and offer a more readable error to the user
 * 
 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
 * @class ApiError
 * @date 18/02/2023
 */
@Data
public class ApiError {
	   private HttpStatus status;
	   private LocalDateTime timestamp;
	   private String message;
	   private List<String> errors;

	   private ApiError() {
	       timestamp = LocalDateTime.now();
	   }

	   public ApiError(HttpStatus status) {
	       this();
	       this.status = status;
	   }

	   public ApiError(HttpStatus status, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = "Unexpected error";
	   }
	   
	   public ApiError(HttpStatus status, String message) {
	       this();
	       this.status = status;
	       this.message = message;
	   }

	   public ApiError(HttpStatus status, String message, Throwable ex, List<String> errors) {
	       this();
	       this.status = status;
	       this.message = message;
	       this.errors = errors;
	   }
}
