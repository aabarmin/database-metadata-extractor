package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import java.net.URI;
import java.util.Arrays;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceCreateDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private CreatePageRequestFactory requestFactory;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${confluence.host}")
  private String confluenceHost;

  @Value("${confluence.port}")
  private int confluencePort;

  @Value("${confluence.protocol}")
  private String confluenceProtocol;

  public CreateResponse create(@NonNull String title, @NonNull String content, @NonNull String space, @Nullable Integer parentId) {
    return credentialsHelper.withCredentials(httpHeaders -> {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      final CreatePageRequest request = requestFactory.createRequest(title, content, space, parentId);
      final HttpEntity<CreatePageRequest> entity = new HttpEntity<>(request, httpHeaders);

      final ResponseEntity<CreateResponse> responseEntity = restTemplate
          .exchange(createUrl(), HttpMethod.POST, entity, CreateResponse.class);

      return responseEntity.getBody();
    });
  }

  @SneakyThrows
  private URI createUrl() {
    return new URIBuilder()
        .setScheme(confluenceProtocol)
        .setHost(confluenceHost)
        .setPort(confluencePort)
        .setPath(StringUtils.join(Arrays.asList("wiki", "rest", "api", "content"), "/") + "/")
        .build();
  }
}
