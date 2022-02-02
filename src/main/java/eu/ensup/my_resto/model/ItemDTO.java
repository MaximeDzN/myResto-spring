package eu.ensup.my_resto.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.ensup.my_resto.domain.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder
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
    @Size(max = 50)
    private String category;

    private Image image;
}
