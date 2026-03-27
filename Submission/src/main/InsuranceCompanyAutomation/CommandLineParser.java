public class CommandLineParser implements ICommandLineParser {

  @Override
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

  private String getNextArg(String[] args, int i, String flag) throws InvalidArgumentException {
    if (i + 1 >= args.length || args[i + 1].startsWith("--")) {
      throw new InvalidArgumentException(flag + " requires a value but none was given.");
    }
    return args[i + 1];
  }

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
