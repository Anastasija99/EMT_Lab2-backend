package emit.lab.model.exceptions;

public class BookNotFound extends RuntimeException {

    public BookNotFound(String message) {
        super(message);
    }
}