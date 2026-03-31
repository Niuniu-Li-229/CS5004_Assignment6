import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for AppConfig getters and shouldGenerate* convenience methods.
 */
public class AppConfigTest {

  /**
   * Test Getter getCsvFilePath
   */
  @Test
  public void testGetCsvFilePath() {
    AppConfig cfg = new AppConfig("data.csv", "out", "email.txt", "letter.txt");
    assertEquals("data.csv", cfg.getCsvFilePath());
  }

  /**
   * Test Getter getOutputDir
   */
  @Test
  public void testGetOutputDir() {
    AppConfig cfg = new AppConfig("data.csv", "out", "email.txt", "letter.txt");
    assertEquals("out", cfg.getOutputDir());
  }

  /**
   * Test Getter getEmailTemplate
   */
  @Test
  public void testGetEmailTemplatePath() {
    AppConfig cfg = new AppConfig("data.csv", "out", "email.txt", null);
    assertEquals("email.txt", cfg.getEmailTemplatePath());
  }

  /**
   * Test Getter getLetterTemplate
   */
  @Test
  public void testGetLetterTemplatePath() {
    AppConfig cfg = new AppConfig("data.csv", "out", null, "letter.txt");
    assertEquals("letter.txt", cfg.getLetterTemplatePath());
  }

  /**
   * Test shouldGenerateEmail
   */
  @Test
  public void testShouldGenerateEmailTrueWhenPathProvided() {
    AppConfig cfg = new AppConfig("data.csv", "out", "email.txt", null);
    assertTrue(cfg.shouldGenerateEmail());
  }

  /**
   * Test shouldGenerateEmail
   */
  @Test
  public void testShouldGenerateEmailFalseWhenPathNull() {
    AppConfig cfg = new AppConfig("data.csv", "out", null, null);
    assertFalse(cfg.shouldGenerateEmail());
  }

  /**
   * Test shouldGenerateLetter
   */
  @Test
  public void testShouldGenerateLetterTrueWhenPathProvided() {
    AppConfig cfg = new AppConfig("data.csv", "out", null, "letter.txt");
    assertTrue(cfg.shouldGenerateLetter());
  }

  /**
   * Test shouldGenerateLetter
   */
  @Test
  public void testShouldGenerateLetterFalseWhenPathNull() {
    AppConfig cfg = new AppConfig("data.csv", "out", null, null);
    assertFalse(cfg.shouldGenerateLetter());
  }

  /**
   * Test when both template are provided
   */
  @Test
  public void testBothTemplatesProvided() {
    AppConfig cfg = new AppConfig("data.csv", "out", "email.txt", "letter.txt");
    assertTrue(cfg.shouldGenerateEmail());
    assertTrue(cfg.shouldGenerateLetter());
  }
}