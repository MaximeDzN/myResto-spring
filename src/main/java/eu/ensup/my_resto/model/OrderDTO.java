package eu.ensup.my_resto.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String status;

    @NotNull
    @Size(max = 150)
    private String address;

    @NotNull
    private Double price;

    private Long user;

    private List<ItemDTO> items;

}
