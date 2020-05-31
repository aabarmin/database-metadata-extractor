package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.mydesignstudio.database.metadata.extractor.config.ConfluenceConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.ObjectMapperConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.RestConfiguration;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceCredentials;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceRestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.ConfluenceFindDelegate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ConfluenceFindDelegate.class,
    ConfluenceDeleteDelegate.class,
    ConfluenceCreateDelegate.class,
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
class ConfluenceCreateDelegateTest {
  @Autowired
  private ConfluenceCreateDelegate unitUnderTest;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  @Autowired
  private ConfluenceCredentials credentials;

  @Autowired
  private ObjectMapper objectMapper;

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
  void create_shouldWorkCorrectly() throws Exception {
    final CreateResponse createResponse = new CreateResponse();
    createResponse.setId("1234");
    createResponse.setTitle("title");
    createResponse.setType("page");

    stubFor(
        post(urlEqualTo("/wiki/rest/api/content/"))
            .withBasicAuth(credentials.getUsername(), credentials.getPassword())
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(objectMapper.writeValueAsString(createResponse))
            )
    );

    final CreateResponse response = unitUnderTest.create("title", "content", "space", null);

    assertThat(response).isNotNull();
    verify(exactly(1), postRequestedFor(urlEqualTo("/wiki/rest/api/content/")));
  }
}