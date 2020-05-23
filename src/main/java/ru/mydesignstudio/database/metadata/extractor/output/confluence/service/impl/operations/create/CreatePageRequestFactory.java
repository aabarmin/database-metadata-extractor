package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceAncestor;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceBody;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceSpace;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.ConfluenceStorage;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreatePageRequest;

@Component
public class CreatePageRequestFactory {
  @Autowired
  private HtmlSanitizer htmlSanitizer;

  @Autowired
  private TitleSanitizer titleSanitizer;

  public CreatePageRequest createRequest(@NonNull String title, @NonNull String content, @NonNull String space) {
    return createRequest(title, content, space, null);
  }

  public CreatePageRequest createRequest(@NonNull String title, @NonNull String content, @NonNull String space, @Nullable Integer parentId) {
    return CreatePageRequest.builder()
        .title(titleSanitizer.sanitize(title))
        .type("page")
        .ancestors(createAncestors(parentId))
        .space(new ConfluenceSpace(space))
        .body(new ConfluenceBody(new ConfluenceStorage(htmlSanitizer.sanitize(content), "storage")))
        .build();
  }

  private List<ConfluenceAncestor> createAncestors(Integer parentId) {
    if (parentId == null) {
      return null;
    }
    return Collections.singletonList(new ConfluenceAncestor(parentId));
  }
}
