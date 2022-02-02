package eu.ensup.my_resto.model;


public enum Roles {

    USER("USER"),
    OWNER("OWNER");

    private String role;

    Roles(String role) {
        this.role = role;
    }
}
