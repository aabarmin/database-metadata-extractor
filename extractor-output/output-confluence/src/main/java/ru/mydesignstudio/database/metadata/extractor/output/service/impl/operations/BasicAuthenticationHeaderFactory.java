package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

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
