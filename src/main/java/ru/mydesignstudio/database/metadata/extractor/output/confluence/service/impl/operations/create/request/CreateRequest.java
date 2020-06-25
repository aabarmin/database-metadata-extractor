package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRequest {
  private String title;
  private String content;
  private String space;
  private Integer parentId;
}
