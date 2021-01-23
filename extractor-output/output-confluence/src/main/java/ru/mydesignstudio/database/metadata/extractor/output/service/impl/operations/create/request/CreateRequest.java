package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request;

import lombok.Builder;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.output.Label;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class CreateRequest {
  private String title;
  private String content;
  private String space;
  private Integer parentId;
  @Builder.Default
  private Set<Label> labels = new HashSet<>();
}
