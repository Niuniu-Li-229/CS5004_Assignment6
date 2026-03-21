/**
 * Thrown when the CSV file cannot be read or parsed.
 */

public class CSVParseException extends Exception {

  public CSVParseException(String message) {
    super(message);
  }

  public CSVParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
