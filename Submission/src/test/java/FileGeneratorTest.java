import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileGeneratorTest {

  private FileGenerator generator;

  @TempDir
  Path tempDir;

  @BeforeEach
  public void setUp() {
    generator = new FileGenerator();
  }

  @Test
  public void testGenerateSingleCustomer() throws Exception {
    // create a temp template file
    Path templateFile = tempDir.resolve("template.txt");
    Files.writeString(templateFile, "Hello [[first_name]] [[last_name]]");

    // create one customer row
    List<Map<String, String>> rows = new ArrayList<>();
    Map<String, String> customer = new HashMap<>();
    customer.put("first_name", "John");
    customer.put("last_name", "Doe");
    rows.add(customer);

    // call generate
    generator.generate(templateFile.toString(), rows, tempDir.toString(), "email");

    // expected file name
    Path outputFile = tempDir.resolve("email_Doe_John.txt");

    // verify file created
    assertTrue(Files.exists(outputFile));

    // verify content
    String content = Files.readString(outputFile);
    assertEquals("Hello John Doe\n", content);
  }

  @Test
  public void testGenerateMultipleCustomers() throws Exception {
    Path templateFile = tempDir.resolve("template.txt");
    Files.writeString(templateFile, "Hi [[first_name]]");

    List<Map<String, String>> rows = new ArrayList<>();

    Map<String, String> c1 = new HashMap<>();
    c1.put("first_name", "John");
    c1.put("last_name", "Doe");

    Map<String, String> c2 = new HashMap<>();
    c2.put("first_name", "Jane");
    c2.put("last_name", "Smith");

    rows.add(c1);
    rows.add(c2);

    generator.generate(templateFile.toString(), rows, tempDir.toString(), "letter");

    Path f1 = tempDir.resolve("letter_Doe_John.txt");
    Path f2 = tempDir.resolve("letter_Smith_Jane.txt");

    assertTrue(Files.exists(f1));
    assertTrue(Files.exists(f2));

    assertEquals("Hi John\n", Files.readString(f1));
    assertEquals("Hi Jane\n", Files.readString(f2));
  }

  @Test
  public void testGenerateWithEmptyRows() throws Exception {
    Path templateFile = tempDir.resolve("template.txt");
    Files.writeString(templateFile, "Hello [[first_name]]");

    List<Map<String, String>> rows = new ArrayList<>();

    generator.generate(templateFile.toString(), rows, tempDir.toString(), "email");

    // no files should be created
    try (var stream = Files.list(tempDir)) {
      // only template file exists
      assertEquals(1, stream.count());
    }
  }

  @Test
  public void testGenerateInvalidTemplatePath() {
    List<Map<String, String>> rows = new ArrayList<>();

    assertThrows(TemplateException.class, () ->
        generator.generate("non_existent.txt", rows, tempDir.toString(), "email")
    );
  }

  @Test
  public void testGenerateWithMissingCustomerFieldInTemplate() throws Exception {
    Path templateFile = tempDir.resolve("template.txt");
    Files.writeString(templateFile, "Hello [[first_name]] [[last_name]]");
    List<Map<String, String>> rows = new ArrayList<>();
    Map<String, String> customer = new HashMap<>();
    customer.put("first_name", "John");
    rows.add(customer);

    generator.generate(templateFile.toString(), rows, tempDir.toString(), "email");

    Path outputFile = tempDir.resolve("email_null_John.txt");
    assertTrue(Files.exists(outputFile));
    assertEquals("Hello John [[last_name]]\n", Files.readString(outputFile));
  }

}
