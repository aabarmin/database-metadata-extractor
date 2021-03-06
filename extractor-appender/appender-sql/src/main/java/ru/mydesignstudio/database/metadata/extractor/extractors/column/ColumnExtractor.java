package ru.mydesignstudio.database.metadata.extractor.extractors.column;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ColumnExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_columns.sql")
  private String columnsQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting columns from {} {}", schemaName, tableName);

    metadata.setColumns(helper.extract(columnsQuery, new Object[]{tableName, schemaName}, ColumnModel.class));

    log.debug("Extracted {} columns", metadata.getColumns().size());
  }
}
