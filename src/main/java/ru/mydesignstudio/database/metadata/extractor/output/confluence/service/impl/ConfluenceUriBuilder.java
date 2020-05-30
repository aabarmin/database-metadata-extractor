package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceUriBuilder {
  @Value("${confluence.host}")
  private String confluenceHost;

  @Value("${confluence.port}")
  private int confluencePort;

  @Value("${confluence.protocol}")
  private String confluenceProtocol;

  @Value("${confluence.type}")
  private String confluenceType;

  @SneakyThrows
  public URI buildWithTailingSlash() {
    final List<String> urlParts = new ArrayList<>();
    if (StringUtils.equals(confluenceType, "cloud")) {
      urlParts.add("wiki");
    }
    urlParts.addAll(Arrays.asList(
        "rest",
        "api",
        "content"
    ));
    return new URIBuilder()
        .setScheme(confluenceProtocol)
        .setHost(confluenceHost)
        .setPort(confluencePort)
        .setPath(StringUtils.join(urlParts, "/") + "/")
        .build();
  }

  public URI build(Map<String, String> parameters) {
    return build(parameters, Collections.emptyList());
  }

  public URI build(List<String> segments) {
    return build(Collections.emptyMap(), segments);
  }

  @SneakyThrows
  public URI build(Map<String, String> parameters, List<String> additionalPathSegments) {
    final URIBuilder builder = new URIBuilder()
        .setScheme(confluenceProtocol)
        .setHost(confluenceHost)
        .setPort(confluencePort)
        .setPathSegments(buildSegments(additionalPathSegments));

    for (Entry<String, String> entry : parameters.entrySet()) {
      builder.addParameter(entry.getKey(), entry.getValue());
    }

    return builder.build();
  }

  private List<String> buildSegments(List<String> additionalSegments) {
    final List<String> segments = new ArrayList<>();
    if (StringUtils.equals(confluenceType, "cloud")) {
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
