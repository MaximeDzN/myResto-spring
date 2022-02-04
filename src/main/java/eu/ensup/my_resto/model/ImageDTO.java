package eu.ensup.my_resto.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


/**
 * The type Image dto.
 */
@Getter
@Setter
public class ImageDTO {

    private Long id;
    private String path;

}
