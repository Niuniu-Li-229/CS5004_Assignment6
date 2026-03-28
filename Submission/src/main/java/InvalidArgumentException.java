/**
 * Thrown when command line arguments are invalid or incomplete.
 */
public class InvalidArgumentException extends Exception {

  public InvalidArgumentException(String message) {
    super(message);
  }
}
