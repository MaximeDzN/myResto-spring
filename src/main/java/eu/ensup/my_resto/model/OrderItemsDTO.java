package eu.ensup.my_resto.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class OrderItemsDTO {
    private ItemDTO item;
    private Integer quantity;
}
