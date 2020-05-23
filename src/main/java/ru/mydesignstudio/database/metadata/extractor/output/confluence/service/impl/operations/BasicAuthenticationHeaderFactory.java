package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations;

import java.nio.charset.Charset;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthenticationHeaderFactory {
  public HttpHeaders build(@NonNull String login, @NonNull String password) {
    final String auth = login + ":" + password;
    final byte[] encoded = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
    final String authHeader = "Basic " + new String(encoded);

    final HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authHeader);
    return headers;
  }
}
