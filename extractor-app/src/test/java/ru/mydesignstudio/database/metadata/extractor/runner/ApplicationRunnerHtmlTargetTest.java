package ru.mydesignstudio.database.metadata.extractor.runner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.database.metadata.extractor.output.ConfluenceOutput;
import ru.mydesignstudio.database.metadata.extractor.output.HtmlMetadataOutput;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
    "output.target=html",
    "output.html.folder=./html_output"
})
class ApplicationRunnerHtmlTargetTest {
  @Autowired
  private ApplicationContext context;

  @MockBean
  private JdbcTemplate jdbcTemplate;

  @Test
  void context_shouldBeHtmlOutput() {
    context.getBean(HtmlMetadataOutput.class);
  }

  @Test
  void context_shouldNotHaveConfluenceOutput() {
    assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(ConfluenceOutput.class));
  }
}