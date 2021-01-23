package ru.mydesignstudio.database.metadata.extractor.output.service.impl.model;

import lombok.Builder;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.output.Label;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ConfluenceMetadata {
  @Builder.Default
  private Set<Label> labels = new HashSet<>();
}
