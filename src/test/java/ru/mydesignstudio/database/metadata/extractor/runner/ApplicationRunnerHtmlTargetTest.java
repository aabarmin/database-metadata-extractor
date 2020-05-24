package ru.mydesignstudio.database.metadata.extractor.runner;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.ConfluenceOutput;
import ru.mydesignstudio.database.metadata.extractor.output.html.HtmlMetadataOutput;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
    "output.target=html",
    "output.html.folder=./html_output"
})
class ApplicationRunnerHtmlTargetTest {
  @Autowired
  private ApplicationContext context;

  @Test
  void context_shouldBeHtmlOutput() {
    context.getBean(HtmlMetadataOutput.class);
  }

  @Test
  void context_shouldNotHaveConfluenceOutput() {
    assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(ConfluenceOutput.class));
  }
}