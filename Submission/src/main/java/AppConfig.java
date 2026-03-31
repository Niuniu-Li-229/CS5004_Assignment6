// --------
// SHARED DATA: InsuranceCompanyAutomation.AppConfig.java
// Produced by Wanjing, consumed by Yuxi
// --------

/**
 * This class contains the application configuration.
 */
public class AppConfig {

  private final String csvFilePath;
  private final String outputDir;
  private final String emailTemplatePath;   // null if --email not requested
  private final String letterTemplatePath;  // null if --letter not requested

  /**
   * Constructor for AppConfig
   * @param csvFilePath path for csv file
   * @param outputDir   directory for output
   * @param emailTemplatePath path to the email template
   * @param letterTemplatePath  path to the letter template
   */
  public AppConfig(String csvFilePath, String outputDir,
      String emailTemplatePath, String letterTemplatePath) {
    this.csvFilePath = csvFilePath;
    this.outputDir = outputDir;
    this.emailTemplatePath = emailTemplatePath;
    this.letterTemplatePath = letterTemplatePath;
  }

  /**
   * Getter for csv file path
   * @return the path to the csv file
   */
  public String getCsvFilePath() {
    return csvFilePath;
  }

  /**
   * Getter for output directory
   * @return  the directory to the output
   */
  public String getOutputDir() {
    return outputDir;
  }

  /**
   * Getter for the email template path
   * @return  the path for the email template
   */
  public String getEmailTemplatePath() {
    return emailTemplatePath;
  }

  /**
   * Getter for the letter template path
   * @return  the path for the letter template
   */
  public String getLetterTemplatePath() {
    return letterTemplatePath;
  }

  /**
   * Check if has valid email template
   * @return true if emailTemplatePath is non-null, i.e. --email was provided
   */
  public boolean shouldGenerateEmail() {
    return emailTemplatePath != null;
  }

  /**
   * Check if has valid letter template
   * @return true if letterTemplatePath is non-null, i.e. --email was provided
   */
  public boolean shouldGenerateLetter() {
    return letterTemplatePath != null;
  }
}
