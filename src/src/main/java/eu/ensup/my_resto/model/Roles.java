package eu.ensup.my_resto.model;


public enum Roles {

    USER("user"),
    OWNER("owner");

    private String r;

    Roles(String role) {
        this.r = role;
    }
}
