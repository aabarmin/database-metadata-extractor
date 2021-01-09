package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create;

import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizer {
  @SneakyThrows
  public String sanitize(@NonNull String content) {
    // really bad solution, but it's too late to write something better
    return StringUtils.substringBefore(StringUtils.substringAfter(content, "<body>"), "</body>");
  }
}
