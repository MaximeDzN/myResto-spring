package eu.ensup.my_resto.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String username;

    @Size(max = 80)
    private String role;

}
