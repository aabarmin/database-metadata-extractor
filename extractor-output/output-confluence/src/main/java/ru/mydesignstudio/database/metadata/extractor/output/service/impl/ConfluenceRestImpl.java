package ru.mydesignstudio.database.metadata.extractor.output.service.impl;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.mydesignstudio.database.metadata.extractor.output.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.request.UpdateRequest;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
@RequiredArgsConstructor
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
