import java.util.Map;

/**
 * Processes a template by replacing [[placeholder]] tokens with row values.
 */

public interface ITemplateProcessor {

  /**
   * Loads a template file and substitutes all [[header]] placeholders.
   *
   * @param templatePath path to the template file
   * @param row          a map of header -> value for one CSV row
   * @return the fully substituted string
   * @throws TemplateException if the file cannot be read or a placeholder has no matching header
   */
  String process(String templatePath, Map<String, String> row) throws TemplateException;

}
