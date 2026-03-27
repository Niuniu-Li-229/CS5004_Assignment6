package InsuranceCompanyAutomation;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Entry point for the Insurance Company Communication Automation program.
 */
public class Main {

  public static void main(String[] args) {
    CommandLineParser cmdParser = new CommandLineParser();
    AppConfig config;
    try {
      config = cmdParser.parse(args);
    } catch (InvalidArgumentException e) {
      System.err.println(CommandLineParser.usageMessage(e.getMessage()));
      System.exit(1);
      return;
    }

    CSVParser csvParser = new CSVParser();
    List<Map<String, String>> rows;
    try {
      rows = csvParser.parse(config.getCsvFilePath());
    } catch (CSVParseException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
      System.exit(1);
      return;
    }

    File outputDir = new File(config.getOutputDir());
    if (!outputDir.exists()) {
      outputDir.mkdirs();
    }

    FileGenerator generator = new FileGenerator();
    if (config.shouldGenerateEmail()) {
      try {
        generator.generate(config.getEmailTemplatePath(), rows,
            config.getOutputDir(), "email");
      } catch (TemplateException e) {
        System.err.println("Error generating emails: " + e.getMessage());
        System.exit(1);
      }
    }

    if (config.shouldGenerateLetter()) {
      try {
        generator.generate(config.getLetterTemplatePath(), rows,
            config.getOutputDir(), "letter");
      } catch (TemplateException e) {
        System.err.println("Error generating letters: " + e.getMessage());
        System.exit(1);
      }
    }

    System.out.println("Done! Files written to: " + config.getOutputDir());
  }
}