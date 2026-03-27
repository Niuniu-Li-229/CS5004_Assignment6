package InsuranceCompanyAutomation;

import java.util.List;
import java.util.Map;

/**
 * Parses a CSV file into a list of rows, each row being a header->value map.
 */

public interface ICSVParser {

  /**
   * Reads the CSV file at the given path.
   *
   * @param filePath path to the CSV file
   * @return list of rows; each row maps column header to its value
   * @throws CSVParseException if the file cannot be read or is malformed
   */
  List<Map<String, String>> parse(String filePath) throws CSVParseException;
}

