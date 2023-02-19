package com.adidas.splitwise.customExceptions;

/**
 * Custom exception which is thrown when a searched entity is not found in the database
 * this class sets a readable message for the client.
 *
 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
 * @class EntityNotFoundException
 * @date 18/02/2023
 */
@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {
	
	/**
	 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
	 * @date 18/02/2023
	 * 
	 * @param searchEntity {@link Class} The class type of the searched object 
	 * @param searchField {@link String} The target field of the searching (ej: id)
	 * @param searchParameter {@link Object} The field value
	 */
	public EntityNotFoundException(Class<?> searchEntity, String searchField, Object searchParameter){
		super("Object " + searchEntity.getSimpleName()
				+ " with " + searchField + " = " + searchParameter.toString() + " not found");
	}
}
