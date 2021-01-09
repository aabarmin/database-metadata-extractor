package ru.mydesignstudio.database.metadata.extractor.extractors;

import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;

public interface TableMetadataAppender {
  void append(TableMetadata metadata, String schemaName, String tableName);
}
