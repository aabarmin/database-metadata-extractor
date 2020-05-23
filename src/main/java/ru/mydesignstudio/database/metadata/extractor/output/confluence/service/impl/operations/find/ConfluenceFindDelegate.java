package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find;

import java.net.URI;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceFindDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${confluence.host}")
  private String confluenceHost;

  @Value("${confluence.port}")
  private int confluencePort;

  @Value("${confluence.protocol}")
  private String confluenceProtocol;

  public FindResponse find(String title, String space) {
    return credentialsHelper.withCredentials(httpHeaders -> {
      final ResponseEntity<FindResponse> response = restTemplate
          .exchange(createFindUrl(title, space), HttpMethod.GET, new HttpEntity<>(httpHeaders),
              FindResponse.class);
      return response.getBody();
    });
  }

  @SneakyThrows
  private URI createFindUrl(String title, String space) {
    return new URIBuilder()
        .setScheme(confluenceProtocol)
        .setHost(confluenceHost)
        .setPort(confluencePort)
        .setPathSegments("wiki", "rest", "api", "content")
        .addParameter("title", title)
        .addParameter("spaceKey", space)
        .addParameter("expand", "history")
        .build();
  }
}
