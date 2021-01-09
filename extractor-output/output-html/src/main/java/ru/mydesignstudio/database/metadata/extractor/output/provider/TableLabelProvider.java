package ru.mydesignstudio.database.metadata.extractor.output.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TableLabelProvider extends LabelProviderBase {
  @Value("${confluence.label.table.values:}")
  private String values;

  @Value("${confluence.label.table.prefix:}")
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
