package ru.mydesignstudio.database.metadata.extractor.output.provider;

import java.util.Collections;
import java.util.Set;
import ru.mydesignstudio.database.metadata.extractor.output.Label;

public interface LabelProvider {
  default Set<Label> provide() {
    return Collections.emptySet();
  }
}
