package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;

import java.util.function.Function;

@Component
public class ConfluenceCredentialsHelper {
  @Autowired
  private BasicAuthenticationHeaderFactory headerFactory;

  public  <T> T withCredentials(ConfluenceParams credentials, Function<HttpHeaders, T> withCredentials) {
    final HttpHeaders headers = headerFactory.build(credentials.getUsername(), credentials.getPassword());
    return withCredentials.apply(headers);
  }
}
