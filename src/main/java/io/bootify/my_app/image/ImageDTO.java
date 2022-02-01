package io.bootify.my_app.image;

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
