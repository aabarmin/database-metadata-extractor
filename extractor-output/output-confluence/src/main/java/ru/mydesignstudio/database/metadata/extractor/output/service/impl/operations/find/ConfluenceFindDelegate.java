package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.utils.Maps;

import java.net.URI;

@Slf4j
@Component
public class ConfluenceFindDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  public FindResponse find(String title, String space, @NonNull ConfluenceParams params) {
    log.info("Searching for the page with title {} in space {}", title, space);

    return credentialsHelper.withCredentials(params, httpHeaders -> {
      final ResponseEntity<FindResponse> response = restTemplate
          .exchange(createFindUrl(title, space, params), HttpMethod.GET, new HttpEntity<>(httpHeaders),
              FindResponse.class);
      return response.getBody();
    });
  }

  private URI createFindUrl(String title, String space, @NonNull ConfluenceParams params) {
    return uriBuilder.build(Maps.of(
        "title", title,
        "spaceKey", space,
        "expand", "version"
    ), params);
  }
}
