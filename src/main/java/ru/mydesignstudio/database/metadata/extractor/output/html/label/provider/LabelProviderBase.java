package ru.mydesignstudio.database.metadata.extractor.output.html.label.provider;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;

public abstract class LabelProviderBase implements LabelProvider {
  private Set<Label> labels;

  protected abstract String getPrefix();

  protected abstract String getValues();

  @PostConstruct
  public void init() {
    labels = Arrays.stream(StringUtils.split(getValues(), ","))
        .map(StringUtils::trimToEmpty)
        .filter(StringUtils::isNotBlank)
        .map(value -> new Label(getPrefix(), value))
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Label> provide() {
    return labels;
  }
}
