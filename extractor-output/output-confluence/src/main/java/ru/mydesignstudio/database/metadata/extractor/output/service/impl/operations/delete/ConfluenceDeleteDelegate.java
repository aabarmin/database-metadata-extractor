package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.delete;

import java.net.URI;
import java.util.Collections;
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
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.ConfluenceCredentialsHelper;

@Slf4j
@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceDeleteDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  public boolean delete(@NonNull String contentId) {
    log.info("Removing content with id {}", contentId);

    return credentialsHelper.withCredentials(httpHeaders -> {
      final ResponseEntity<String> responseEntity = restTemplate
          .exchange(createDeleteUrl(contentId), HttpMethod.DELETE, new HttpEntity<>(httpHeaders),
              String.class);

      return responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT);
    });
  }

  private URI createDeleteUrl(@NonNull String contentId) {
    return uriBuilder.build(Collections.singletonList(contentId));
  }
}
