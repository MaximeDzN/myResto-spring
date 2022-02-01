package eu.ensup.my_resto.model;


public enum Roles {

    USER("user"),
    OWNER("owner");

    private String role;

    Roles(String role) {
        this.role = role;
    }
}
