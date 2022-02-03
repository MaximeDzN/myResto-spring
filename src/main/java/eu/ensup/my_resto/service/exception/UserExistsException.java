package eu.ensup.my_resto.service.exception;

public class UserExistsException extends Exception {
    public UserExistsException(String error) {
        super(error);
    }
}
