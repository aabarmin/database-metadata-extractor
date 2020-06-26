package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.mydesignstudio.database.metadata.extractor.config.ConfluenceConfiguration;
import ru.mydesignstudio.database.metadata.extractor.config.ObjectMapperConfiguration;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceRestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.BasicAuthenticationHeaderFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.CreatePageRequestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.HtmlSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.TitleSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.UpdatePageRequestFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete.ConfluenceDeleteDelegate;

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
    ConfluenceRestFactory.class,
    ObjectMapperConfiguration.class
})
@AutoConfigureWebClient(registerRestTemplate = true)
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.type=cloud",
    "confluence.port=80",
    "confluence.host=host",
    "confluence.protocol=http",
    "confluence.username=username",
    "confluence.password=password"
})
class ConfluenceFindDelegateTest {
  @Value("classpath:find_response.json")
  private Resource findResponse;

  @Autowired
  private ConfluenceFindDelegate unitUnderTest;

  @Autowired
  private MockRestServiceServer mockServer;

  @Test
  void check_contextStarts() {
    assertAll(
        () -> assertNotNull(mockServer),
        () -> assertNotNull(unitUnderTest)
    );
  }

  @Test
  void find_shouldMakeRequests() throws Exception {
    mockServer.expect(requestTo(startsWith("http://host:80/wiki/rest/api/content")))
        .andExpect(method(HttpMethod.GET))
        .andExpect(queryParam("spaceKey", is("space")))
        .andExpect(queryParam("title", is("title")))
        .andExpect(queryParam("expand", is("version")))
        .andRespond(withSuccess(findResponse, MediaType.APPLICATION_JSON));

    final FindResponse response = unitUnderTest.find("title", "space");

    mockServer.verify();

    assertAll(
        () -> assertNotNull(response),
        () -> assertNotNull(response.getResults()),
        () -> assertThat(response.getResults(), iterableWithSize(1)),
        () -> assertNotNull(response.getResults().get(0).getVersion()),
        () -> assertEquals(2, response.getResults().get(0).getVersion().getNumber())
    );
  }
}