package ru.mydesignstudio.database.metadata.extractor.extractors;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.Pair;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;

@Component
public class TableMetadataExtractor {
  @Autowired
  private List<TableMetadataAppender> appenders;

  public List<TableMetadata> extract(@NonNull List<Pair<String, String>> values) {
    return values.stream()
        .map(pair -> extract(pair.getKey(), pair.getValue()))
        .collect(Collectors.toList());
  }

  private TableMetadata extract(@NonNull String schemaName, @NonNull String tableName) {
    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName(tableName);
    metadata.setSchemaName(schemaName);
    for (TableMetadataAppender appender : appenders) {
      appender.append(metadata, schemaName, tableName);
    }
    return metadata;
  }
}
