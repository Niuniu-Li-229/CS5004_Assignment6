import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


public class TemplateProcessorTest {
  private TemplateProcessor processor;

  @TempDir
  Path tempDir;

  @BeforeEach
  public void setUp() {
    processor = new TemplateProcessor();
  }

  @Test
  public void testLoad() throws TemplateException, IOException {
    Path templateFile = tempDir.resolve("template.txt");
    Files.writeString(templateFile, "Hello [[first_name]]\nxxxx");

    String result = processor.load(templateFile.toString());

    assertEquals("Hello [[first_name]]" + System.lineSeparator() + "xxxx" + System.lineSeparator(), result);
  }

  @Test
  public void testLoadFileNotFound() {
    Path missingFile = tempDir.resolve("missing.txt");
    assertEquals(false, Files.exists(missingFile));

    TemplateException exception = assertThrows(
        TemplateException.class,
        () -> processor.load(missingFile.toString())
    );
    assertEquals("File cannot be found!", exception.getMessage());
  }

  @Test
  public void testRender(){
    String template = "Hello [[first_name]] [[last_name]], your email is [[email]]";

    Map<String, String> customer = new HashMap<>();
    customer.put("first_name", "John");
    customer.put("last_name", "Doe");
    customer.put("email", "john@example.com");

    String result = processor.render(template, customer);

    assertEquals("Hello John Doe, your email is john@example.com", result);
  }


  @Test
  public void testRenderWithInvalidInput() {
    String template = "Hello [[first_name]] [[last_name]]";
    assertThrows(IllegalArgumentException.class,() -> processor.render(template, null));
  }

  @Test
  public void testRenderWithNullCustomerValue(){
    String template = "Hello [[first_name]] [[last_name]]";
    Map<String, String> customer = new HashMap<>();
    customer.put("first_name", "John");
    customer.put("last_name", null);
    assertEquals("Hello John ", processor.render(template,customer));
  }

  @Test
  public void testRenderWithRepeatedPlaceholder() {
    String template = "[[first_name]] says hi to [[first_name]]";
    Map<String, String> customer = new HashMap<>();
    customer.put("first_name", "John");

    assertEquals("John says hi to John", processor.render(template, customer));
  }


}
