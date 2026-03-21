import java.util.List;
import java.util.Map;

/**
 * Generates output files for all rows using the given template.
 */

public interface IFileGenerator {

  /**
   * For each row, processes the template and writes a file to outputDir. Output files should be
   * named consistently, e.g. email_1.txt, email_2.txt
   *
   * @param templatePath path to the template file
   * @param rows         parsed CSV rows from ICSVParser
   * @param outputDir    directory to write output files into
   * @param prefix       filename prefix, e.g. "email" or "letter"
   * @throws TemplateException if processing fails for any row
   */
  void generate(String templatePath, List<Map<String, String>> rows, String outputDir,
      String prefix) throws TemplateException;
}
