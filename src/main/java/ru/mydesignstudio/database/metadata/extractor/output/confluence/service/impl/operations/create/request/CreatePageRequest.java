package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePageRequest {
  private String type;
  private String title;
  private List<ConfluenceAncestor> ancestors;
  private ConfluenceSpace space;
  private ConfluenceBody body;
}
