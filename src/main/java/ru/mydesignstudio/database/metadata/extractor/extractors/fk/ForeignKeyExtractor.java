package ru.mydesignstudio.database.metadata.extractor.extractors.fk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

import java.util.List;

@Component
public class ForeignKeyExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_foreign_keys.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    List<ForeignKeyModel> foreignKeys = helper.extract(extractQuery, new Object[]{tableName, schemaName}, ForeignKeyModel.class);
    metadata.setForeignKeys(foreignKeys);
  }
}
