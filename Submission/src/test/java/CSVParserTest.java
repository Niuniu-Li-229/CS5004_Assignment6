import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for CSVParser, covering parse() and parseLine() directly.
 */
public class CSVParserTest {

  private CSVParser csvParser;

  @TempDir
  Path tempDir;

  @BeforeEach
  public void setUp() {
    csvParser = new CSVParser();
  }

  @Test
  public void testParseLineSimple() {
    List<String> result = csvParser.parseLine("\"first_name\",\"last_name\",\"email\"");
    assertEquals(List.of("first_name", "last_name", "email"), result);
  }

  @Test
  public void testParseLineCommaInsideQuotes() {
    // "Chemel, James L Cpa" must be treated as one field
    List<String> result = csvParser.parseLine("\"Art\",\"Chemel, James L Cpa\",\"art@example.com\"");
    assertEquals(3, result.size());
    assertEquals("Chemel, James L Cpa", result.get(1));
  }

  @Test
  public void testParseLineSingleField() {
    List<String> result = csvParser.parseLine("\"only\"");
    assertEquals(List.of("only"), result);
  }

  @Test
  public void testParseLineEmptyField() {
    // middle field is ""
    List<String> result = csvParser.parseLine("\"a\",\"\",\"b\"");
    assertEquals(List.of("a", "", "b"), result);
  }

  @Test
  public void testParseSingleDataRow() throws Exception {
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv,
        "\"first_name\",\"last_name\"\n" +
            "\"Art\",\"Venere\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());

    assertEquals(1, rows.size());
    assertEquals("Art",    rows.get(0).get("first_name"));
    assertEquals("Venere", rows.get(0).get("last_name"));
  }

  @Test
  public void testParseMultipleRows() throws Exception {
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv,
        "\"first_name\",\"last_name\"\n" +
            "\"Art\",\"Venere\"\n" +
            "\"James\",\"Reign\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());

    assertEquals(2, rows.size());
    assertEquals("James", rows.get(1).get("first_name"));
  }

  @Test
  public void testParseFieldWithCommaInsideQuotes() throws Exception {
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv,
        "\"first_name\",\"company_name\"\n" +
            "\"Art\",\"Chemel, James L Cpa\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());

    assertEquals("Chemel, James L Cpa", rows.get(0).get("company_name"));
  }

  @Test
  public void testParseSkipsBlankLines() throws Exception {
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv,
        "\"first_name\",\"last_name\"\n" +
            "\n" +
            "\"Art\",\"Venere\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());

    assertEquals(1, rows.size());
  }

  @Test
  public void testParseHeadersPreservedAsKeys() throws Exception {
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv,
        "\"first_name\",\"last_name\",\"email\"\n" +
            "\"Art\",\"Venere\",\"art@venere.org\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());
    Map<String, String> row = rows.get(0);

    assertTrue(row.containsKey("first_name"));
    assertTrue(row.containsKey("last_name"));
    assertTrue(row.containsKey("email"));
  }

  @Test
  public void testParseRowWithFewerFieldsThanHeaders() throws Exception {
    // your code fills missing fields with "" — verify that behavior
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv,
        "\"first_name\",\"last_name\",\"email\"\n" +
            "\"Art\",\"Venere\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());

    assertEquals("", rows.get(0).get("email"));
  }

  @Test
  public void testParseEmptyFileThrows() throws Exception {
    Path csv = tempDir.resolve("empty.csv");
    Files.writeString(csv, "");

    assertThrows(CSVParseException.class, () -> csvParser.parse(csv.toString()));
  }

  @Test
  public void testParseNonExistentFileThrows() {
    assertThrows(CSVParseException.class,
        () -> csvParser.parse(tempDir.resolve("missing.csv").toString()));
  }

  @Test
  public void testParseHeaderOnlyReturnsEmptyList() throws Exception {
    // file has a header row but zero data rows
    Path csv = tempDir.resolve("test.csv");
    Files.writeString(csv, "\"first_name\",\"last_name\"\n");

    List<Map<String, String>> rows = csvParser.parse(csv.toString());

    assertTrue(rows.isEmpty());
  }
}