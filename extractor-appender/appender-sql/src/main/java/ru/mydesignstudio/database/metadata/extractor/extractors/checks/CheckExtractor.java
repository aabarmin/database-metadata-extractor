package ru.mydesignstudio.database.metadata.extractor.extractors.checks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.CheckModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Component
public class CheckExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_checks.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    metadata.setChecks(helper.extract(extractQuery, new Object[] { tableName, schemaName }, CheckModel.class));
  }
}
