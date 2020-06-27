package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import java.net.URI;
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
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.request.UpdatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.request.UpdateRequest;

@Slf4j
@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceUpdateDelegate {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  @Autowired
  private UpdatePageRequestFactory requestFactory;


  public CreateResponse update(@NonNull UpdateRequest request) {
    checkNotNull(request, "Request should not be null");

    log.info("Updating content with id {}", request.getId());
    log.debug("Title {}", request.getTitle());
    log.debug("Content {}", request.getContent());
    log.debug("Version {}", request.getVersion());

    return credentialsHelper.withCredentials(httpHeaders -> {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      httpHeaders.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));

      final UpdatePageRequest updateRequest = requestFactory.create(request);

      final HttpEntity<UpdatePageRequest> entity = new HttpEntity<>(updateRequest, httpHeaders);
      final URI uri = uriBuilder.build(Lists.newArrayList(request.getId()));

      final ResponseEntity<CreateResponse> responseEntity = restTemplate
          .exchange(uri, HttpMethod.PUT, entity, CreateResponse.class);

      return responseEntity.getBody();
    });
  }


}
