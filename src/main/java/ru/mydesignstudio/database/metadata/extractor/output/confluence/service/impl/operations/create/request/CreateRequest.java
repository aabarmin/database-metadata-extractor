package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;

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
