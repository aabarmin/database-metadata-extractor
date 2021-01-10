package ru.mydesignstudio.database.metadata.extractor.database;

import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema;
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource;
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject;

@Component
public class SimpleTableMetadataExtractor implements TableMetadataExtractor {
  @Autowired
  private List<TableMetadataAppender> appenders;

  @Override
  public List<TableMetadata> extract(List<MetadataSource> sources) {
    final List<TableMetadata> tableMetadata = Lists.newArrayList();
    for (MetadataSource source : sources) {
      for (MetadataSchema schema : source.getSchemas()) {
        for (MetadataObject table : schema.getObjects()) {
          tableMetadata.add(extract(schema.getName(), table.getName()));
        }
      }
    }
    return tableMetadata;
  }

  private TableMetadata extract(@NonNull String schemaName, @NonNull String tableName) {
    // TODO, rewrite this code
    /*
    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName(tableName);
    metadata.setSchemaName(schemaName);
    for (TableMetadataAppender appender : appenders) {
      appender.append(metadata, schemaName, tableName);
    }
    return metadata;
     */
    throw new UnsupportedOperationException();
  }
}
