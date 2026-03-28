// --------
// SHARED DATA: InsuranceCompanyAutomation.AppConfig.java
// Produced by Wanjing, consumed by Yuxi
// --------

public class AppConfig {

  private final String csvFilePath;
  private final String outputDir;
  private final String emailTemplatePath;   // null if --email not requested
  private final String letterTemplatePath;  // null if --letter not requested

  public AppConfig(String csvFilePath, String outputDir,
      String emailTemplatePath, String letterTemplatePath) {
    this.csvFilePath = csvFilePath;
    this.outputDir = outputDir;
    this.emailTemplatePath = emailTemplatePath;
    this.letterTemplatePath = letterTemplatePath;
  }

  public String getCsvFilePath() {
    return csvFilePath;
  }

  public String getOutputDir() {
    return outputDir;
  }

  public String getEmailTemplatePath() {
    return emailTemplatePath;
  }

  public String getLetterTemplatePath() {
    return letterTemplatePath;
  }

  public boolean shouldGenerateEmail() {
    return emailTemplatePath != null;
  }

  public boolean shouldGenerateLetter() {
    return letterTemplatePath != null;
  }
}
