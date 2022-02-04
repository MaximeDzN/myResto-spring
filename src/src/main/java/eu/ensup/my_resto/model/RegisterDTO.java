package eu.ensup.my_resto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterDTO {

    private Long id;
    private String username;
    private String password;

}
