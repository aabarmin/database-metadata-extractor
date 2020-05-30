package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConfluenceUriBuilder.class)
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.type=server",
    "confluence.port=50080",
    "confluence.host=localhost",
    "confluence.protocol=http",
})
class ConfluenceUriBuilderServerTest {
  @Autowired
  private ConfluenceUriBuilder builder;

  @Test
  void check_contextStarts() {
    assertNotNull(builder);
  }

  @Test
  void build_shouldReturnWikiAtTheBeginning() {
    final URI uri = builder.buildWithTailingSlash();
    final String url = uri.toASCIIString();

    assertThat(url).contains("/rest/");
  }
}