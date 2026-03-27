package InsuranceCompanyAutomation;

/** Parses and validates command line arguments. */
public interface ICommandLineParser {
  /**
   * Parses the raw args array into a validated InsuranceCompanyAutomation.AppConfig.
   * @param args the command line arguments
   * @return a fully validated InsuranceCompanyAutomation.AppConfig
   * @throws InvalidArgumentException if args are missing, illegal, or incomplete
   */
  AppConfig parse(String[] args) throws InvalidArgumentException;
}

