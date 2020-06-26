package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import ru.mydesignstudio.database.metadata.extractor.config.ConfluenceConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.ObjectMapperConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.RestConfiguration;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceCredentials;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceRestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.CreatePageRequestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.HtmlSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.TitleSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.UpdatePageRequestFactory;

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
    ConfluenceConfiguration.class,
    ConfluenceRestFactory.class,
    RestConfiguration.class,
    ObjectMapperConfiguration.class
})
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.type=cloud",
    "confluence.port=50080",
    "confluence.host=localhost",
    "confluence.protocol=http",
    "confluence.username=username",
    "confluence.password=password"
})
class ConfluenceDeleteDelegateTest {
  @Autowired
  private ConfluenceCredentials credentials;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  @Autowired
  private ConfluenceDeleteDelegate unitUnderTest;

  private WireMockServer mockServer;

  @BeforeEach
  void setUp() {
    mockServer = new WireMockServer(options().dynamicPort());
    mockServer.start();

    configureFor(mockServer.port());

    ReflectionTestUtils.setField(uriBuilder, "confluencePort", mockServer.port());
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
            .withBasicAuth(credentials.getUsername(), credentials.getPassword())
            .willReturn(
                aResponse().withStatus(204)
            )
    );

    final boolean response = unitUnderTest.delete("1234");

    assertThat(response).isTrue();
    verify(exactly(1), deleteRequestedFor(urlEqualTo("/wiki/rest/api/content/1234")));
  }
}