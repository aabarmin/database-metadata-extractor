package ru.mydesignstudio.database.metadata.extractor.extractors;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;

@Component
public class DatabaseMetadataExtractor {
  @Autowired
  private List<DatabaseMetadataAppender> appenders;

  public List<DatabaseMetadata> extract(@NonNull List<String> schemas) {
    return schemas.stream()
        .map(schema -> extract(schema))
        .collect(Collectors.toList());
  }

  private DatabaseMetadata extract(@NonNull String schemaName) {
    final DatabaseMetadata metadata = new DatabaseMetadata();
    metadata.setSchemaName(schemaName);
    for (DatabaseMetadataAppender appender : appenders) {
      appender.append(metadata, schemaName);
    }
    return metadata;
  }
}
