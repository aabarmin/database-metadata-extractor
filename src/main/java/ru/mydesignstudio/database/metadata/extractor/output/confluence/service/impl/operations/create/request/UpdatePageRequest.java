package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePageRequest {
  private String id;
  private String title;
  private String type;
  private String status;
  private ConfluenceVersion version;
  private ConfluenceBody body;
  private ConfluenceMetadata metadata;
}
