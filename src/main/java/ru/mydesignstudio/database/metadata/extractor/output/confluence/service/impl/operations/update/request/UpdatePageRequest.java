package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.request;

import lombok.Builder;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceBody;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceVersion;

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
