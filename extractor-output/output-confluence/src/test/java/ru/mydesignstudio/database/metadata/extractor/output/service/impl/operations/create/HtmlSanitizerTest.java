package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create;

import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HtmlSanitizerTest {
  @InjectMocks
  private HtmlSanitizer unitUnderTest;

  @Test
  void check_contextStarts() {
    assertThat(unitUnderTest).isNotNull();
  }

  @Test
  void sanitize_headerIsRemoved() throws Exception {
    final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
    final Resource resource = resourceLoader.getResource("classpath:sanitizer_input.html");
    @Cleanup final InputStream htmlStream = resource.getInputStream();

    final String pageContent = IOUtils.toString(htmlStream, Charset.forName("UTF-8"));

    final String processedContent = unitUnderTest.sanitize(pageContent);

    assertThat(processedContent).doesNotContain("<body>")
        .doesNotContain("</body>")
        .doesNotContain("<html>")
        .doesNotContain("</html>");
  }
}