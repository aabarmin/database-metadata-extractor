package ru.mydesignstudio.database.metadata.extractor.runner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.TemplateEngine;
import ru.mydesignstudio.database.metadata.extractor.config.ConfluenceConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.RestConfiguration;
import ru.mydesignstudio.database.metadata.extractor.output.ConfluenceOutput;
import ru.mydesignstudio.database.metadata.extractor.output.Output;
import ru.mydesignstudio.database.metadata.extractor.output.HtmlMetadataOutput;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.parent.page.id=",
    "confluence.host=test.atlassian.net",
    "confluence.username=",
    "confluence.password=",
    "confluence.port=443",
    "output.html.folder=./html_output"
})
class ApplicationRunnerConfluenceTargetTest {
  @Autowired
  private ApplicationContext context;

  @MockBean
  private JdbcTemplate jdbcTemplate;

  @Test
  void context_shouldHaveHtmlOutput() {
    context.getBean(HtmlMetadataOutput.class);
  }

  @Test
  void context_shouldHaveConfluenceOutput() {
    context.getBean(ConfluenceOutput.class);
  }

  @TestConfiguration
  @Import({ConfluenceConfiguration.class, RestConfiguration.class})
  @ComponentScan(basePackageClasses = Output.class)
  public static class TestConfig {
    @MockBean
    private TemplateEngine templateEngine;
  }
}