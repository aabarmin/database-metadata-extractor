package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.ConfluenceUriBuilder;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.ConfluenceCredentialsHelper;
import ru.mydesignstudio.database.metadata.extractor.utils.Maps;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceFindDelegate {
  @Autowired
  private ConfluenceCredentialsHelper credentialsHelper;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfluenceUriBuilder uriBuilder;

  public FindResponse find(String title, String space) {
    return credentialsHelper.withCredentials(httpHeaders -> {
      final ResponseEntity<FindResponse> response = restTemplate
          .exchange(createFindUrl(title, space), HttpMethod.GET, new HttpEntity<>(httpHeaders),
              FindResponse.class);
      return response.getBody();
    });
  }

  private URI createFindUrl(String title, String space) {
    return uriBuilder.build(Maps.of(
        "title", title,
        "spaceKey", space,
        "expand", "history"
    ));
  }
}
