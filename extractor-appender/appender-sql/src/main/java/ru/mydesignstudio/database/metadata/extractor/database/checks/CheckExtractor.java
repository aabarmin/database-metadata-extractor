package ru.mydesignstudio.database.metadata.extractor.database.checks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.CheckModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
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
