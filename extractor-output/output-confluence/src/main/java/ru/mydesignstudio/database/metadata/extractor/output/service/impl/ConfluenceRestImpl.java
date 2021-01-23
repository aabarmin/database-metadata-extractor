package ru.mydesignstudio.database.metadata.extractor.output.service.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.ConfluenceUpdateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.request.UpdateRequest;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class ConfluenceRestImpl implements Confluence {
  @Autowired
  private ConfluenceFindDelegate findDelegate;

  @Autowired
  private ConfluenceDeleteDelegate deleteDelegate;

  @Autowired
  private ConfluenceCreateDelegate createDelegate;

  @Autowired
  private ConfluenceUpdateDelegate updateDelegate;


  @Override
  public FindResponse find(@NonNull String title, @NonNull ConfluenceParams params) {
    checkNotNull(title, "Title should not be null");
    checkNotNull(params.getSpace(), "Space should not be null");

    return findDelegate.find(title, params.getSpace(), params);
  }

  @Override
  public CreateResponse create(@NonNull CreateRequest request, @NonNull ConfluenceParams params) {
    checkNotNull(request, "Request should not be null");

    return createDelegate.create(request, params);
  }

  @Override
  public CreateResponse update(@NonNull UpdateRequest request, @NonNull ConfluenceParams params) {
    checkNotNull(request, "Request should not be null");

    return updateDelegate.update(request, params);
  }

  @Override
  public boolean delete(@NonNull String contentId, @NonNull ConfluenceParams params) {
    checkNotNull(contentId, "Content id should not be null");

    return deleteDelegate.delete(contentId, params);
  }
}
