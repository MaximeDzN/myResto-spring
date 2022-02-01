package eu.ensup.my_resto.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemDTO {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String name;

    @NotNull
    private Integer quantity;

    @Size(max = 250)
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private String category;

    private Long image;

}
