package ru.mydesignstudio.database.metadata.extractor.output.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConfluenceUriBuilder.class)
class ConfluenceUriBuilderCloudTest {
  @Autowired
  private ConfluenceUriBuilder builder;

  private ConfluenceParams confluenceParams() {
    return ConfluenceParams.builder()
        .confluenceType("cloud")
        .username("username")
        .password("password")
        .confluenceProtocol("http")
        .confluencePort(80)
        .confluenceHost("host")
        .build();
  }

  @Test
  void check_contextStarts() {
    assertNotNull(builder);
  }

  @Test
  void build_shouldReturnWikiAtTheBeginning() {
    final URI uri = builder.buildWithTailingSlash(confluenceParams());
    final String url = uri.toASCIIString();

    assertThat(url).contains("/wiki/");
  }
}