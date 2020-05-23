package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceCredentials;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceFactory;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.ConfluenceCreateDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.delete.ConfluenceDeleteDelegate;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.ConfluenceFindDelegate;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceRestFactory implements ConfluenceFactory {
  @Autowired
  private ConfluenceFindDelegate findDelegate;

  @Autowired
  private ConfluenceDeleteDelegate deleteDelegate;

  @Autowired
  private ConfluenceCreateDelegate createDelegate;

  @Override
  public Confluence create(ConfluenceCredentials credentials) {
    return new ConfluenceRestImpl(
        findDelegate,
        deleteDelegate,
        createDelegate
    );
  }
}
