package InsuranceCompanyAutomation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * This class processes the template. It contains method which read a template file and method which process the template by replacing the placeholder
 */
public class TemplateProcessor {

  /**
   * This method loads the template
   * @param templatePath the template dir path
   * @return the template
   */
  public String load(String templatePath) throws TemplateException {
     StringBuilder template = new StringBuilder();
    //Read the template file
    try(BufferedReader file = new BufferedReader(new FileReader(templatePath))){
      String line = file.readLine();
      while(line != null){
          template.append(line).append(System.lineSeparator());
          line = file.readLine();
      }
    } catch (FileNotFoundException fnfe) {
      throw new TemplateException("File cannot be found!", fnfe);
    } catch (IOException ioe) {
      throw new TemplateException("Something else went wrong", ioe);
    }
    return template.toString();
  }


  /**
   * This method renders the template by replacing the placeholders
   * @param template the template to be rendered
   * @param customer customer extracted from customer information file, key: header, value: info content
   * @return template with the placeholders replaced by specific customers information
   */
  public String render(String template, Map<String, String> customer){
    if(customer == null){
      throw new IllegalArgumentException("Invalid Input");
    }
    for(Map.Entry<String, String> entry : customer.entrySet()){
      String key = entry.getKey();
      String value = entry.getValue();

      if(value == null){
        value = "";
      }
      String placeholder = "[[" + key + "]]";
      template = template.replace(placeholder, value);
    }
    return template;
  }

}

