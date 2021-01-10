package ru.mydesignstudio.database.metadata.extractor.database.column;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
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
