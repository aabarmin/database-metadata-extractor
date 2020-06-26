package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import static com.google.common.base.Preconditions.checkNotNull;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceBody;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceStorage;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceVersion;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.UpdatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.UpdateRequest;

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
