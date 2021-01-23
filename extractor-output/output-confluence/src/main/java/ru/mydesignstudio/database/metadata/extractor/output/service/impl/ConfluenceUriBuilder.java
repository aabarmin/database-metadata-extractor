package ru.mydesignstudio.database.metadata.extractor.output.service.impl;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
@Component
public class ConfluenceUriBuilder {
  @SneakyThrows
  public URI buildWithTailingSlash(@NonNull ConfluenceParams params) {
    final List<String> urlParts = new ArrayList<>();
    if (StringUtils.equals(params.getConfluenceType(), "cloud")) {
      urlParts.add("wiki");
    }
    urlParts.addAll(Arrays.asList(
        "rest",
        "api",
        "content"
    ));
    return new URIBuilder()
        .setScheme(params.getConfluenceProtocol())
        .setHost(params.getConfluenceHost())
        .setPort(params.getConfluencePort())
        .setPath(StringUtils.join(urlParts, "/") + "/")
        .build();
  }

  public URI build(Map<String, String> parameters, @NonNull ConfluenceParams params) {
    return build(parameters, Collections.emptyList(), params);
  }

  public URI build(List<String> segments, @NonNull ConfluenceParams params) {
    return build(Collections.emptyMap(), segments, params);
  }

  @SneakyThrows
  public URI build(Map<String, String> parameters, List<String> additionalPathSegments, @NonNull ConfluenceParams params) {
    final URIBuilder builder = new URIBuilder()
        .setScheme(params.getConfluenceProtocol())
        .setHost(params.getConfluenceHost())
        .setPort(params.getConfluencePort())
        .setPathSegments(buildSegments(additionalPathSegments, params));

    for (Entry<String, String> entry : parameters.entrySet()) {
      builder.addParameter(entry.getKey(), entry.getValue());
    }

    return builder.build();
  }

  private List<String> buildSegments(List<String> additionalSegments, @NonNull ConfluenceParams params) {
    final List<String> segments = new ArrayList<>();
    if (StringUtils.equals(params.getConfluenceType(), "cloud")) {
      segments.add("wiki");
    }
    segments.addAll(Arrays.asList(
        "rest",
        "api",
        "content"
    ));
    segments.addAll(additionalSegments);
    return segments;
  }
}
