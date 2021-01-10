package ru.mydesignstudio.database.metadata.extractor.extractors.procedure.argument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.DatabaseMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.ArgumentModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Component
public class ArgumentExtractor implements DatabaseMetadataAppender {
  @StringResource("classpath:sql/extract_procedure_arguments.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(DatabaseMetadata metadata, String schemaName) {
    metadata.setArguments(helper.extract(query, new Object[] { schemaName }, ArgumentModel.class));
  }
}
