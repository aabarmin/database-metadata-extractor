package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete;

import java.net.URI;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceDeleteDelegate {
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

  public boolean delete(@NonNull String contentId) {
    return credentialsHelper.withCredentials(httpHeaders -> {
      final ResponseEntity<String> responseEntity = restTemplate
          .exchange(createDeleteUrl(contentId), HttpMethod.DELETE, new HttpEntity<>(httpHeaders),
              String.class);

      return responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT);
    });
  }

  @SneakyThrows
  private URI createDeleteUrl(@NonNull String contentId) {
    return new URIBuilder()
        .setScheme(confluenceProtocol)
        .setHost(confluenceHost)
        .setPort(confluencePort)
        .setPathSegments("wiki", "rest", "api", "content", contentId)
        .build();
  }
}
