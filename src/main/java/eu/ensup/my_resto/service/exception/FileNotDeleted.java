package eu.ensup.my_resto.service.exception;

public class FileNotDeleted extends Exception {
    public FileNotDeleted(String error) {
        super(error);
    }
}
