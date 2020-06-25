package ru.mydesignstudio.database.metadata.extractor.output.html.label;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TableLabelProvider implements LabelProvider {
  @Value("${confluence.label.table.values:}")
  private String values;

  @Value("${confluence.label.table.prefix:}")
  private String prefix;

  private Set<Label> labels;

  @PostConstruct
  public void init() {
    labels = Arrays.stream(StringUtils.split(values, ","))
        .map(StringUtils::trimToEmpty)
        .filter(StringUtils::isNotBlank)
        .map(value -> new Label(prefix, value))
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Label> provide() {
    return labels;
  }
}
