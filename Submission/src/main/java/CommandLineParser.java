/**
 * This class is the parser for the command line.
 */
public class CommandLineParser {

  /**
   * Loop through the user input and pass the arguments to AppConfig accordingly
   * @param args  user input for the arguments
   * @return  valid AppConfig
   * @throws InvalidArgumentException if the inputs are invalid
   */
  public AppConfig parse(String[] args) throws InvalidArgumentException {
    String csvFile = null;
    String outputDir = null;
    String emailTemplate = null;
    String letterTemplate = null;
    boolean emailFlag = false;
    boolean letterFlag = false;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "--csv-file":
          csvFile = getNextArg(args, i++, "--csv-file");
          break;
        case "--output-dir":
          outputDir = getNextArg(args, i++, "--output-dir");
          break;
        case "--email":
          emailFlag = true;
          break;
        case "--email-template":
          emailTemplate = getNextArg(args, i++, "--email-template");
          break;
        case "--letter":
          letterFlag = true;
          break;
        case "--letter-template":
          letterTemplate = getNextArg(args, i++, "--letter-template");
          break;
        default:
          throw new InvalidArgumentException("Unknown argument: " + args[i]);
      }
    }

    validate(csvFile, outputDir, emailFlag, emailTemplate, letterFlag, letterTemplate);

    return new AppConfig(csvFile, outputDir, emailFlag ? emailTemplate : null,
        letterFlag ? letterTemplate : null);
  }

  /**
   * Builds a formatted error and usage message for display to the user.
   * @param errorDetail the error message user encountered when running the program
   * @return template input for users to follow
   */
  public static String usageMessage(String errorDetail) {
    return "Error: " + errorDetail + "\n\n" +
        "Usage:\n" +
        "  --email                        Generate email messages. Requires --email-template.\n" +
        "  --email-template <path/to/file> A filename for the email template.\n" +
        "  --letter                       Generate letters. Requires --letter-template.\n" +
        "  --letter-template <path/to/file> A filename for the letter template.\n" +
        "  --output-dir <path/to/folder>  The folder to store all generated files. Required.\n" +
        "  --csv-file <path/to/file>      The CSV file to process. Required.\n\n" +
        "Examples:\n" +
        "  --email --email-template email-template.txt --output-dir emails --csv-file customer.csv\n" +
        "  --letter --letter-template letter-template.txt --output-dir letters --csv-file customer.csv\n";
  }

  /**
   * Returns the value token that follows a flag, or throws if none is present.
   * @param args  user input
   * @param i ith part of the argument
   * @param flag  flag for invalid input
   * @return  ith element in the argument that can be passed as an input
   * @throws InvalidArgumentException if invalid input
   */
  private String getNextArg(String[] args, int i, String flag) throws InvalidArgumentException {
    if (i + 1 >= args.length || args[i + 1].startsWith("--")) {
      throw new InvalidArgumentException(flag + " requires a value but none was given.");
    }
    return args[i + 1];
  }

  /**
   * Validate the user's input, true if --email was provided
   * @param csvFile csv file that have the customer info
   * @param outputDir output directory that store the output
   * @param emailFlag flag if use an email or not
   * @param emailTemplate email template to use if using email
   * @param letterFlag  flag if use a letter or not
   * @param letterTemplate  letter template to use if using letter
   * @throws InvalidArgumentException if the input is invalid
   */
  private void validate(String csvFile, String outputDir, boolean emailFlag, String emailTemplate,
      boolean letterFlag, String letterTemplate) throws InvalidArgumentException {

    if (csvFile == null) {
      throw new InvalidArgumentException("--csv-file is required.");
    }

    if (outputDir == null) {
      throw new InvalidArgumentException("--output-dir is required.");
    }

    if (!emailFlag && !letterFlag) {
      throw new InvalidArgumentException("At least one of --email or --letter must be specified.");
    }

    if (emailFlag && emailTemplate == null) {
      throw new InvalidArgumentException("--email provided but no --email-template was given.");
    }

    if (letterFlag && letterTemplate == null) {
      throw new InvalidArgumentException("--letter provided but no --letter-template was given.");
    }

    if (!emailFlag && emailTemplate != null) {
      throw new InvalidArgumentException("--email-template provided but --email was not given.");
    }

    if (!letterFlag && letterTemplate != null) {
      throw new InvalidArgumentException("--letter-template provided but --letter was not given.");
    }
  }

}
