package eu.ensup.my_resto.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemsDTO {
    private Long id;
    private Integer quantity;
}
