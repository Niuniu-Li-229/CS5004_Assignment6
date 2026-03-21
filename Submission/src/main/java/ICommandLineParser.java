/** Parses and validates command line arguments. */
public interface ICommandLineParser {
  /**
   * Parses the raw args array into a validated AppConfig.
   * @param args the command line arguments
   * @return a fully validated AppConfig
   * @throws InvalidArgumentException if args are missing, illegal, or incomplete
   */
  AppConfig parse(String[] args) throws InvalidArgumentException;
}

