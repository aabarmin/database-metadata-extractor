package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceAncestor;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceBody;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceSpace;

@Data
@Builder
public class CreatePageRequest {
  private String type;
  private String title;
  private List<ConfluenceAncestor> ancestors;
  private ConfluenceSpace space;
  private ConfluenceBody body;
}
