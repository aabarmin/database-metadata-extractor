package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import java.net.URI;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;

@Slf4j
@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceCreateDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private CreatePageRequestFactory requestFactory;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  public CreateResponse create(@NonNull CreateRequest createRequest) {
    log.info("Creating content with title {} in space {}", createRequest.getTitle(), createRequest.getSpace());
    log.debug("Content: {}", createRequest.getContent());
    log.debug("Parent id: {}", createRequest.getParentId());

    return credentialsHelper.withCredentials(httpHeaders -> {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      final CreatePageRequest request = requestFactory.createRequest(createRequest);
      final HttpEntity<CreatePageRequest> entity = new HttpEntity<>(request, httpHeaders);

      final ResponseEntity<CreateResponse> responseEntity = restTemplate
          .exchange(createUrl(), HttpMethod.POST, entity, CreateResponse.class);

      return responseEntity.getBody();
    });
  }

  @SneakyThrows
  private URI createUrl() {
    return uriBuilder.buildWithTailingSlash();
  }
}
