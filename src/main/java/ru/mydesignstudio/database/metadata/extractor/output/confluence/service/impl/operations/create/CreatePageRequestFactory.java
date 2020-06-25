package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceAncestor;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceBody;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceSpace;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceStorage;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;

@Component
public class CreatePageRequestFactory {
  @Autowired
  private HtmlSanitizer htmlSanitizer;

  @Autowired
  private TitleSanitizer titleSanitizer;

  public CreatePageRequest createRequest(@NonNull CreateRequest request) {
    return CreatePageRequest.builder()
        .title(titleSanitizer.sanitize(request.getTitle()))
        .type("page")
        .ancestors(createAncestors(request.getParentId()))
        .space(new ConfluenceSpace(request.getSpace()))
        .body(new ConfluenceBody(new ConfluenceStorage(htmlSanitizer.sanitize(request.getContent()), "storage")))
        .metadata(createMetadata(request))
        .build();
  }

  private ConfluenceMetadata createMetadata(@NonNull CreateRequest request) {
    return ConfluenceMetadata.builder()
        .labels(request.getLabels())
        .build();
  }

  private List<ConfluenceAncestor> createAncestors(@Nullable Integer parentId) {
    if (parentId == null) {
      return null;
    }
    return Collections.singletonList(new ConfluenceAncestor(parentId));
  }
}
