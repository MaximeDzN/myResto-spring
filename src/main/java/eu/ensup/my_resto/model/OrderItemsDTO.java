package eu.ensup.my_resto.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * The type Order items dto.
 */
@Data
@Builder
@ToString
public class OrderItemsDTO {
    private ItemDTO item;
    private Integer quantity;
}
