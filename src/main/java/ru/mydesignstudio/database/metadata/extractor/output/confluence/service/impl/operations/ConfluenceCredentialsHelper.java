package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceCredentials;

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
