package eu.ensup.my_resto.model;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class OrderDTO {

    private Long id;
    private String status;
    private String address;
    private Double price;
    private UserDTO user;
    private List<OrderItemsDTO> items;
    private OffsetDateTime datecreated;
}
