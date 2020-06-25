package ru.mydesignstudio.database.metadata.extractor.output.html.label;

import java.util.Collections;
import java.util.Set;

public interface LabelProvider {
  default Set<Label> provide() {
    return Collections.emptySet();
  }
}
