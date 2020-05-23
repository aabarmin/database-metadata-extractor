package ru.mydesignstudio.database.metadata.extractor.extractors;

import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;

public interface DatabaseMetadataAppender {
  void append(DatabaseMetadata metadata, String schemaName);
}
