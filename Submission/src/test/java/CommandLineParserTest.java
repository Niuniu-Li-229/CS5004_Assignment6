import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for CommandLineParser argument parsing and validation.
 */
public class CommandLineParserTest {

  private CommandLineParser parser;

  @BeforeEach
  public void setUp() {
    parser = new CommandLineParser();
  }

  @Test
  public void testValidEmailOnly() throws InvalidArgumentException {
    String[] args = {"--email", "--email-template", "email.txt",
        "--output-dir", "out", "--csv-file", "data.csv"};
    AppConfig cfg = parser.parse(args);
    assertTrue(cfg.shouldGenerateEmail());
    assertFalse(cfg.shouldGenerateLetter());
    assertEquals("email.txt", cfg.getEmailTemplatePath());
    assertEquals("out", cfg.getOutputDir());
    assertEquals("data.csv", cfg.getCsvFilePath());
  }

  @Test
  public void testValidLetterOnly() throws InvalidArgumentException {
    String[] args = {"--letter", "--letter-template", "letter.txt",
        "--output-dir", "out", "--csv-file", "data.csv"};
    AppConfig cfg = parser.parse(args);
    assertFalse(cfg.shouldGenerateEmail());
    assertTrue(cfg.shouldGenerateLetter());
    assertEquals("letter.txt", cfg.getLetterTemplatePath());
  }

  @Test
  public void testValidEmailAndLetter() throws InvalidArgumentException {
    String[] args = {"--email", "--email-template", "email.txt",
        "--letter", "--letter-template", "letter.txt",
        "--output-dir", "out", "--csv-file", "data.csv"};
    AppConfig cfg = parser.parse(args);
    assertTrue(cfg.shouldGenerateEmail());
    assertTrue(cfg.shouldGenerateLetter());
  }

  @Test
  public void testArgsInDifferentOrder() throws InvalidArgumentException {
    // Args may arrive in any order
    String[] args = {"--csv-file", "data.csv", "--output-dir", "out",
        "--email-template", "email.txt", "--email"};
    AppConfig cfg = parser.parse(args);
    assertTrue(cfg.shouldGenerateEmail());
    assertEquals("data.csv", cfg.getCsvFilePath());
  }

  @Test
  public void testMissingCsvFile() {
    String[] args = {"--email", "--email-template", "email.txt", "--output-dir", "out"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testMissingOutputDir() {
    String[] args = {"--email", "--email-template", "email.txt", "--csv-file", "data.csv"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testNeitherEmailNorLetter() {
    String[] args = {"--output-dir", "out", "--csv-file", "data.csv"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testEmailFlagWithoutTemplate() {
    String[] args = {"--email", "--output-dir", "out", "--csv-file", "data.csv"};
    InvalidArgumentException ex = assertThrows(InvalidArgumentException.class,
        () -> parser.parse(args));
    assertTrue(ex.getMessage().contains("--email-template"));
  }

  @Test
  public void testLetterFlagWithoutTemplate() {
    String[] args = {"--letter", "--output-dir", "out", "--csv-file", "data.csv"};
    InvalidArgumentException ex = assertThrows(InvalidArgumentException.class,
        () -> parser.parse(args));
    assertTrue(ex.getMessage().contains("--letter-template"));
  }

  @Test
  public void testEmailTemplateWithoutEmailFlag() {
    String[] args = {"--email-template", "email.txt",
        "--output-dir", "out", "--csv-file", "data.csv"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testLetterTemplateWithoutLetterFlag() {
    String[] args = {"--letter-template", "letter.txt",
        "--output-dir", "out", "--csv-file", "data.csv"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testEmailTemplateWithNoValue() {
    // --email-template at end of args with no path following
    String[] args = {"--email", "--email-template",
        "--output-dir", "out", "--csv-file", "data.csv"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testOutputDirWithNoValue() {
    String[] args = {"--email", "--email-template", "email.txt",
        "--csv-file", "data.csv", "--output-dir"};
    assertThrows(InvalidArgumentException.class, () -> parser.parse(args));
  }

  @Test
  public void testUnknownFlag() {
    String[] args = {"--email", "--email-template", "email.txt",
        "--output-dir", "out", "--csv-file", "data.csv", "--unknown"};
    InvalidArgumentException ex = assertThrows(InvalidArgumentException.class,
        () -> parser.parse(args));
    assertTrue(ex.getMessage().contains("--unknown"));
  }

  @Test
  public void testUsageMessageContainsError() {
    String msg = CommandLineParser.usageMessage("something went wrong");
    assertTrue(msg.contains("something went wrong"));
    assertTrue(msg.contains("--email"));
    assertTrue(msg.contains("--csv-file"));
  }
}