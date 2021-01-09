package ru.mydesignstudio.database.metadata.extractor.extractors;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.parameters.MetadataSchema;
import ru.mydesignstudio.database.metadata.extractor.parameters.MetadataSource;

@Component
public class SimpleDatabaseMetadataExtractor implements DatabaseMetadataExtractor {
  @Autowired
  private List<DatabaseMetadataAppender> appenders;

  @Override
  public List<DatabaseMetadata> extract(@NonNull List<MetadataSource> sources) {
    return sources.stream()
        .map(source -> extract(source))
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private List<DatabaseMetadata> extract(@NonNull MetadataSource source) {
    return source.getSchemas()
        .stream()
        .map(MetadataSchema::getName)
        .map(this::extract)
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
