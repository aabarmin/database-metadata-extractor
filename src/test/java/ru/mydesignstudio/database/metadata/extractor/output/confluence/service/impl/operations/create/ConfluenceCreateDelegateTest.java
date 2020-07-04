package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.mydesignstudio.database.metadata.extractor.config.ConfluenceConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.ObjectMapperConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.RestConfiguration;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceCredentials;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceRestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.UpdatePageRequestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.hamcrest.Matchers.*;

@RestClientTest(components = {
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
    ConfluenceRestFactory.class
})
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.type=cloud",
    "confluence.port=50080",
    "confluence.host=host",
    "confluence.protocol=http",
    "confluence.username=username",
    "confluence.password=password"
})
@AutoConfigureWebClient(registerRestTemplate = true)
class ConfluenceCreateDelegateTest {
  @Autowired
  private ConfluenceCreateDelegate unitUnderTest;

  @Value("classpath:create_response.json")
  private Resource createResponse;

  @Value("classpath:add_label_response.json")
  private Resource addLabelResponse;

  @Autowired
  private MockRestServiceServer mockServer;

  @Test
  void check_contextStarts() {
    assertAll(
            () -> assertNotNull(unitUnderTest),
            () -> assertNotNull(mockServer)
    );
  }

  @Test
  void createWithoutLabels_shouldSendSingleRequest() throws Exception {
    mockServer.expect(requestTo("http://host:50080/wiki/rest/api/content/"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("title", is("title")))
            .andExpect(jsonPath("type", is("page")))
            .andRespond(withSuccess(createResponse, MediaType.APPLICATION_JSON));

    final CreateResponse response = unitUnderTest.create(CreateRequest.builder()
            .content("content")
            .title("title")
            .space("space")
            .build());

    mockServer.verify();

    assertAll(
            () -> assertNotNull(response),
            () -> assertEquals("12345", response.getId())
    );
  }

  @Test
  void createWithLabels_shouldSendTwoRequests() {
    mockServer.expect(requestTo("http://host:50080/wiki/rest/api/content/"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("title", is("title")))
            .andExpect(jsonPath("type", is("page")))
            .andRespond(withSuccess(createResponse, MediaType.APPLICATION_JSON));

    mockServer.expect(requestTo("http://host:50080/wiki/rest/api/content/12345/label"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header("Authorization", is(notNullValue())))
            .andExpect(jsonPath("$[0].prefix", is("global")))
            .andExpect(jsonPath("$[0].name", is(oneOf("label1", "label2"))))
            .andExpect(jsonPath("$[1].name", is(oneOf("label1", "label2"))))
            .andRespond(withSuccess(addLabelResponse, MediaType.APPLICATION_JSON));

    final CreateResponse response = unitUnderTest.create(CreateRequest.builder()
            .content("content")
            .title("title")
            .space("space")
            .labels(Sets.newHashSet(
                    new Label("global", "label1"),
                    new Label("global", "label2")
            ))
            .build());

    mockServer.verify();

    assertAll(
            () -> assertNotNull(response),
            () -> assertEquals("12345", response.getId())
    );
  }
}