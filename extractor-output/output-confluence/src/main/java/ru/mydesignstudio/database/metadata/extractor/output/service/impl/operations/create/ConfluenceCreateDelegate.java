package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create;

import com.google.common.collect.Lists;
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
import ru.mydesignstudio.database.metadata.extractor.output.Label;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.response.CreateResponse;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    checkNotNull(createRequest, "Create request should not be null");

    log.info("Creating content with title {} in space {}", createRequest.getTitle(), createRequest.getSpace());
    log.debug("Content: {}", createRequest.getContent());
    log.debug("Parent id: {}", createRequest.getParentId());

    return credentialsHelper.withCredentials(httpHeaders -> {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      final CreatePageRequest request = requestFactory.createRequest(createRequest);
      final HttpEntity<CreatePageRequest> createEntity = new HttpEntity<>(request, httpHeaders);

      final ResponseEntity<CreateResponse> responseEntity = restTemplate
              .exchange(createUrl(), HttpMethod.POST, createEntity, CreateResponse.class);

      final CreateResponse createResponse = responseEntity.getBody();

      if (hasLabels(createRequest)) {
        final HttpEntity<Set<Label>> addLabelsEntity = new HttpEntity<>(createRequest.getLabels(), httpHeaders);
        final ResponseEntity<Map> addLabelsResponse = restTemplate
                .exchange(addLabelsUrl(createResponse), HttpMethod.POST, addLabelsEntity, Map.class);

        checkArgument(addLabelsResponse.getStatusCode().is2xxSuccessful(), "Labels weren't added");
      }

      return createResponse;
    });
  }

  @SneakyThrows
  private URI addLabelsUrl(@NonNull CreateResponse createResponse) {
    return uriBuilder.build(Lists.newArrayList(
            createResponse.getId(),
            "label"
    ));
  }

  private boolean hasLabels(@NonNull CreateRequest createRequest) {
    return !createRequest.getLabels().isEmpty();
  }

  @SneakyThrows
  private URI createUrl() {
    return uriBuilder.buildWithTailingSlash();
  }
}
