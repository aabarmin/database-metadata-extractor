package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update;

import static com.google.common.base.Preconditions.checkNotNull;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceBody;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceStorage;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model.ConfluenceVersion;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.HtmlSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.TitleSanitizer;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.request.UpdatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.update.request.UpdateRequest;

@Component
public class UpdatePageRequestFactory {
  @Autowired
  private TitleSanitizer titleSanitizer;

  @Autowired
  private HtmlSanitizer htmlSanitizer;

  public UpdatePageRequest create(@NonNull UpdateRequest request) {
    checkNotNull(request, "Request should not be null");

    return UpdatePageRequest.builder()
        .id(request.getId())
        .version(createVersion(request))
        .type("page")
        .title(titleSanitizer.sanitize(request.getTitle()))
        .status("current")
        .body(createBody(request))
        .metadata(createMetadata(request))
        .build();
  }

  private ConfluenceVersion createVersion(@NonNull UpdateRequest request) {
    return new ConfluenceVersion(request.getVersion());
  }

  private ConfluenceMetadata createMetadata(@NonNull UpdateRequest request) {
    return ConfluenceMetadata.builder()
        .labels(request.getLabels())
        .build();
  }

  private ConfluenceBody createBody(@NonNull UpdateRequest request) {
    final String content = htmlSanitizer.sanitize(request.getContent());
    return new ConfluenceBody(new ConfluenceStorage(content, "storage"));
  }
}
