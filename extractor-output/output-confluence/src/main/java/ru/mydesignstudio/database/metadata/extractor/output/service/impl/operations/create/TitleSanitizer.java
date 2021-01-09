package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create;

import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TitleSanitizer {
  public String sanitize(@NonNull String title) {
    return StringUtils.remove(title, "$");
  }
}
