package eu.ensup.my_resto.model;

import lombok.Getter;
import lombok.Setter;


/**
 * The type Field error.
 */
@Getter
@Setter
public class FieldError {

    private String field;
    private String errorCode;

}
