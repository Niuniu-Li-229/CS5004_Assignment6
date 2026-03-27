import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVParser implements ICSVParser {

  @Override
  public List<Map<String, String>> parse(String filePath) throws CSVParseException {
    List<Map<String, String>> rows = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String headerLine = reader.readLine();
      if (headerLine == null) {
        throw new CSVParseException("CSV file is empty: " + filePath);
      }
      List<String> headers = parseLine(headerLine);

      String line;
      while ((line = reader.readLine()) != null) {
        if (line.isBlank()) {
          continue;
        }
        List<String> fields = parseLine(line);

        Map<String, String> row = new LinkedHashMap<>();
        for (int i = 0; i < headers.size(); i++) {
          row.put(headers.get(i), i < fields.size() ? fields.get(i) : "");
        }
        rows.add(row);
      }
    } catch (IOException e) {
      throw new CSVParseException("Could not read file: " + filePath, e);
    }

    return rows;
  }

  List<String> parseLine(String line) {
    List<String> fields = new ArrayList<>();
    StringBuilder current = new StringBuilder();
    boolean inQuotes = false;

    for (char c : line.toCharArray()) {
      if (c == '"') {
        inQuotes = !inQuotes;
      } else if (c == ',' && !inQuotes) {
        fields.add(current.toString().trim());
        current.setLength(0);
      } else {
        current.append(c);
      }
    }

    fields.add(current.toString().trim());

    return fields;
  }
}