package InsuranceCompanyAutomation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class generates templates for every customer in the information file
 */
public class FileGenerator {

  /**
   * The method generates template output for every customer row from the customer information file
   * Output files should be named consistently, e.g. email_lastname_firstname.txt, letter_lastname_firstname.txt
   * @param templatePath path to the template file
   * @param rows         parsed rows from customer information file
   * @param outputDir    directory to write output files into
   * @param prefix       filename prefix, e.g. "email" or "letter"
   * @throws TemplateException if processing fails for any row
   */
  public void generate(String templatePath, List<Map<String, String>> rows, String outputDir, String prefix)
      throws TemplateException {
    // Load the template
    TemplateProcessor templateProcessor = new TemplateProcessor();
    String template = templateProcessor.load(templatePath);

      for(int i = 0; i < rows.size(); i++){
          Map<String, String> customer = rows.get(i);
          String templateForCustomer = templateProcessor.render(template, customer);
          String customerName = customer.get("last_name") + "_" + customer.get("first_name");
          String fileName = prefix + "_" + customerName + ".txt";

          //Write the output template and store it to the outputDir path
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputDir + File.separator +fileName))){
            writer.write(templateForCustomer);

        } catch (IOException ioe) {
            throw new TemplateException("Something went wrong", ioe);
        }
      }
  }

}
