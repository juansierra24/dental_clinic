package com.jcst.dentalclinic.customExceptions;

/**
 * Custom exception which is thrown when trying to create an object that already exists.
 * For instance, when trying to create a user with the same email (email has unique constraint)
 *
 * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
 * @class EntityAlreadyExistsException
 * @date 20/02/2023
 */
@SuppressWarnings("serial")
public class EntityAlreadyExistsException extends RuntimeException {
    /**
     * @author <a href="mailto:carlos.sierratrujillo@gmail.com">Juan Carlos Sierra Trujillo</a>
     * @date 18/02/2023
     *
     * @param searchEntity {@link Class} The class type of the object that is trying to create
     * @param searchField {@link String} The target field of the searching (ej: id, email)
     * @param searchParameter {@link Object} The field value
     */
    public EntityAlreadyExistsException(Class<?> searchEntity, String searchField, Object searchParameter){
        super(searchEntity.getSimpleName()
                + " with " + searchField + " = " + searchParameter.toString() + " already exists");
    }
}
