package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.ConfluenceFindDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.FindResponse;

@Data
@RequiredArgsConstructor
public class ConfluenceRestImpl implements Confluence {
  private final ConfluenceFindDelegate findDelegate;
  private final ConfluenceDeleteDelegate deleteDelegate;
  private final ConfluenceCreateDelegate createDelegate;

  @Override
  public FindResponse find(String title, String space) {
    return findDelegate.find(title, space);
  }

  @Override
  public CreateResponse create(String title, String content, String space, Integer parentId) {
    return createDelegate.create(title, content, space, parentId);
  }

  @Override
  public boolean delete(String contentId) {
    return deleteDelegate.delete(contentId);
  }


}
