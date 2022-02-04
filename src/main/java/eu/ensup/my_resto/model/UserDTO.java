package eu.ensup.my_resto.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


/**
 * The type User dto.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String role;
}
