package eu.ensup.my_resto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CartItemDTO {
    private Long id;
    private Long quantity;
}
