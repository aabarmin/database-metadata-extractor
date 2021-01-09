package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.mydesignstudio.database.metadata.extractor.output.Label;
import ru.mydesignstudio.database.metadata.extractor.output.service.ConfluenceCredentials;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.UpdatePageRequestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.request.UpdateRequest;

@RestClientTest(components = {
    ConfluenceUpdateDelegate.class
})
@AutoConfigureWebClient(registerRestTemplate = true)
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.port=443",
    "confluence.host=host",
    "confluence.type=cloud",
    "confluence.protocol=https"
})
class ConfluenceUpdateDelegateTest {
  @Configuration
  @Import({
      ConfluenceUpdateDelegate.class,
      ConfluenceCredentialsHelper.class,
      BasicAuthenticationHeaderFactory.class,
      ConfluenceUriBuilder.class,
      UpdatePageRequestFactory.class,
      TitleSanitizer.class,
      HtmlSanitizer.class
  })
  static class ConfigurationForTest {

  }

  @Autowired
  private ConfluenceUpdateDelegate unitUnderTest;

  @MockBean
  private ConfluenceCredentials confluenceCredentials;

  @Autowired
  private MockRestServiceServer mockServer;

  @Value("classpath:update_response.json")
  private Resource updateResponse;

  @Test
  void check_contextStarts() {
    assertAll(
        () -> assertNotNull(unitUnderTest),
        () -> assertNotNull(mockServer)
    );
  }

  @Test
  void update_shouldSendPutRequest() {
    mockServer.expect(requestTo("https://host:443/wiki/rest/api/content/42"))
        .andExpect(method(HttpMethod.PUT))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(header("Accept", is(MediaType.APPLICATION_JSON_VALUE)))
        .andExpect(jsonPath("id", is("42")))
        .andExpect(jsonPath("title", is("Updated title")))
        .andExpect(jsonPath("type", is("page")))
        .andExpect(jsonPath("status", is("current")))
        .andExpect(jsonPath("version.number", is(2)))
        .andExpect(jsonPath("body.storage.value", is("Body text")))
        .andExpect(jsonPath("body.storage.representation", is("storage")))
        .andExpect(jsonPath("metadata.labels", iterableWithSize(1)))
        .andExpect(jsonPath("metadata.labels[0].prefix", is("global")))
        .andExpect(jsonPath("metadata.labels[0].name", is("value")))
        .andRespond(withSuccess(updateResponse, MediaType.APPLICATION_JSON));

    final UpdateRequest request = UpdateRequest.builder()
        .id(String.valueOf(42))
        .version(2)
        .title("Updated title")
        .content("<body>Body text</body>")
        .labels(Sets.newHashSet(new Label("global", "value")))
        .build();

    final CreateResponse response = unitUnderTest.update(request);

    mockServer.verify();

    assertAll(
        () -> assertNotNull(response)
    );
  }
}