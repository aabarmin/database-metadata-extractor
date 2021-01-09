package ru.mydesignstudio.database.metadata.extractor.output.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
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

  @PostConstruct
  public void init() {
    log.info("Confluence protocol: {}", confluencePort);
    log.info("Confluence host: {}", confluenceHost);
    log.info("Confluence port: {}", confluencePort);
    log.info("Confluence type: {}", confluenceType);
  }

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
