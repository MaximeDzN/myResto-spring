package eu.ensup.my_resto.model;


/**
 * The enum Roles.
 */
public enum Roles {

    /**
     * User roles.
     */
    USER("user"),
    /**
     * Owner roles.
     */
    OWNER("owner");

    private String r;

    Roles(String role) {
        this.r = role;
    }
}
