package InsuranceCompanyAutomation;

/**
 * Thrown when a template file cannot be read or a placeholder is unresolvable.
 */
public class TemplateException extends Exception {

  public TemplateException(String message) {
    super(message);
  }

  public TemplateException(String message, Throwable cause) {
    super(message, cause);
  }

}
