package eu.ensup.my_resto.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImageDTO {

    private Long id;

    @Size(max = 255)
    private String path;

}
