package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.delete;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import ru.mydesignstudio.database.metadata.extractor.config.ObjectMapperConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.RestConfiguration;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.CreatePageRequestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.HtmlSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.TitleSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.UpdatePageRequestFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {
    ConfluenceFindDelegate.class,
    ConfluenceDeleteDelegate.class,
    ConfluenceCreateDelegate.class,
    ConfluenceUpdateDelegate.class,
    UpdatePageRequestFactory.class,
    ConfluenceUriBuilder.class,
    HtmlSanitizer.class,
    TitleSanitizer.class,
    CreatePageRequestFactory.class,
    ConfluenceCredentialsHelper.class,
    BasicAuthenticationHeaderFactory.class,
    RestConfiguration.class,
    ObjectMapperConfiguration.class
})
class ConfluenceDeleteDelegateTest {
  @Autowired
  private ConfluenceDeleteDelegate unitUnderTest;

  private WireMockServer mockServer;

  @BeforeEach
  void setUp() {
    mockServer = new WireMockServer(options().dynamicPort());
    mockServer.start();

    configureFor(mockServer.port());
}

  private ConfluenceParams confluenceParams() {
    return ConfluenceParams.builder()
        .confluenceType("cloud")
        .username("username")
        .password("password")
        .confluenceProtocol("http")
        .confluencePort(mockServer.port())
        .confluenceHost("localhost")
        .build();
  }

  @AfterEach
  void tearDown() {
    mockServer.stop();
  }

  @Test
  void check_contextStarts() {
    assertThat(unitUnderTest).isNotNull();
  }

  @Test
  void delete_shouldSendRequest() {
    stubFor(
        delete(urlEqualTo("/wiki/rest/api/content/1234"))
            .withBasicAuth(confluenceParams().getUsername(), confluenceParams().getPassword())
            .willReturn(
                aResponse().withStatus(204)
            )
    );

    final boolean response = unitUnderTest.delete("1234", confluenceParams());

    assertThat(response).isTrue();
    verify(exactly(1), deleteRequestedFor(urlEqualTo("/wiki/rest/api/content/1234")));
  }
}