package ch.zli.coworkingSpace.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
