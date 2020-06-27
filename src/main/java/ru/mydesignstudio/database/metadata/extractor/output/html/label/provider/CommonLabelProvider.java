package ru.mydesignstudio.database.metadata.extractor.output.html.label.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonLabelProvider extends LabelProviderBase {
  @Value("${confluence.label.common.values:}")
  private String values;

  @Value("${confluence.label.common.prefix:}")
  private String prefix;

  @Override
  public String getValues() {
    return values;
  }

  @Override
  public String getPrefix() {
    return prefix;
  }
}
