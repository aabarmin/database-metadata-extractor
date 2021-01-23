package ru.mydesignstudio.database.metadata.extractor.output.provider;

import ru.mydesignstudio.database.metadata.extractor.output.Label;

import java.util.Collections;
import java.util.Set;

public interface LabelProvider {
  default Set<Label> provide() {
    return Collections.emptySet();
  }
}
