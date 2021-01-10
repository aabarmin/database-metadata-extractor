package ru.mydesignstudio.database.metadata.extractor.database.pk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.PrimaryKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class PrimaryKeyExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_primary_keys.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting primary keys for table {} from schema {}", tableName, schemaName);

    metadata.setPrimaryKeys(helper.extract(extractQuery, new Object[] { tableName, schemaName }, PrimaryKeyModel.class));

    log.debug("Extracted {} primary keys", metadata.getPrimaryKeys().size());
  }
}
