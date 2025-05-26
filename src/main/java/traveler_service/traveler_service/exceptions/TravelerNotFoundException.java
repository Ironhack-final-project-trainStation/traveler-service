package traveler_service.traveler_service.exceptions;

public class TravelerNotFoundException extends RuntimeException {
    public TravelerNotFoundException(String message) {
        super(message);
    }
}
