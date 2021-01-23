package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.delete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.ConfluenceCredentialsHelper;

import java.net.URI;
import java.util.Collections;

@Slf4j
@Component
public class ConfluenceDeleteDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  public boolean delete(@NonNull String contentId, @NonNull ConfluenceParams params) {
    log.info("Removing content with id {}", contentId);

    return credentialsHelper.withCredentials(params, httpHeaders -> {
      final ResponseEntity<String> responseEntity = restTemplate
          .exchange(createDeleteUrl(contentId, params), HttpMethod.DELETE, new HttpEntity<>(httpHeaders),
              String.class);

      return responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT);
    });
  }

  private URI createDeleteUrl(@NonNull String contentId, @NonNull ConfluenceParams params) {
    return uriBuilder.build(Collections.singletonList(contentId), params);
  }
}
