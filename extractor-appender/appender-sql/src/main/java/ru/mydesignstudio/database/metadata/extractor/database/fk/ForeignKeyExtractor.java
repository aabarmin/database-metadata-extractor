package ru.mydesignstudio.database.metadata.extractor.database.fk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ForeignKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ForeignKeyExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_foreign_keys.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting foreign keys for table {} in schema {}", tableName, schemaName);

    metadata.setForeignKeys(helper.extract(extractQuery, new Object[]{tableName, schemaName}, ForeignKeyModel.class));

    log.debug("Extracted {} foreign keys", metadata.getForeignKeys().size());
  }
}
