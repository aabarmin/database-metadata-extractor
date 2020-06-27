package ru.mydesignstudio.database.metadata.extractor.output.html.label.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiagramLabelProvider extends LabelProviderBase {
  @Value("${confluence.label.diagram.values:}")
  private String values;

  @Value("${confluence.label.diagram.prefix:}")
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
