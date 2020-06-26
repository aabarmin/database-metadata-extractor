package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import lombok.Data;
import lombok.NonNull;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.UpdateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.FindResponse;

@Data
public class ConfluenceRestImpl implements Confluence {
  private final ConfluenceFindDelegate findDelegate;
  private final ConfluenceDeleteDelegate deleteDelegate;
  private final ConfluenceCreateDelegate createDelegate;
  private final ConfluenceUpdateDelegate updateDelegate;


  @Override
  public FindResponse find(@NonNull String title, @NonNull String space) {
    checkNotNull(title, "Title should not be null");
    checkNotNull(space, "Space should not be null");

    return findDelegate.find(title, space);
  }

  @Override
  public CreateResponse create(@NonNull CreateRequest request) {
    checkNotNull(request, "Request should not be null");

    return createDelegate.create(request);
  }

  @Override
  public CreateResponse update(@NonNull UpdateRequest request) {
    checkNotNull(request, "Request should not be null");

    return updateDelegate.update(request);
  }

  @Override
  public boolean delete(@NonNull String contentId) {
    checkNotNull(contentId, "Content id should not be null");

    return deleteDelegate.delete(contentId);
  }

}
