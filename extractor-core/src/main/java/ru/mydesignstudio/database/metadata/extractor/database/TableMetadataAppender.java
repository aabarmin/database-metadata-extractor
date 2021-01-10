package ru.mydesignstudio.database.metadata.extractor.database;

import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;

public interface TableMetadataAppender {
  void append(TableMetadata metadata, String schemaName, String tableName);
}
