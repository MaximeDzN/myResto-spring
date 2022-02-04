package eu.ensup.my_resto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class CartItemDTO {
    private Long id;
    private Long quantity;
}
