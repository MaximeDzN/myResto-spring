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
    private String name;
    private Integer quantity;
    private String description;
    private Double price;
    private String category;
    private Image image;
}
