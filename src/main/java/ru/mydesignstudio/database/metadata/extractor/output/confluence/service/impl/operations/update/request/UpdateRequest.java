package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.request;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;

@Data
@Builder
public class UpdateRequest {
  private String id;
  private String title;
  private String content;
  private String space;
  private Integer parentId;
  private Integer version;
  @Builder.Default
  private Set<Label> labels = new HashSet<>();
}
