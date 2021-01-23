package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.service.ConfluenceCredentials;

import java.util.function.Function;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceCredentialsHelper {
  @Autowired
  private BasicAuthenticationHeaderFactory headerFactory;

  @Autowired
  private ConfluenceCredentials credentials;

  public  <T> T withCredentials(Function<HttpHeaders, T> withCredentials) {
    final HttpHeaders headers = headerFactory.build(credentials.getUsername(), credentials.getPassword());
    return withCredentials.apply(headers);
  }
}
