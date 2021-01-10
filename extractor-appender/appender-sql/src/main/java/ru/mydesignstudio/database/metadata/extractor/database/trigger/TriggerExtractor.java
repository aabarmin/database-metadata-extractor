package ru.mydesignstudio.database.metadata.extractor.database.trigger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TriggerModel;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class TriggerExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_triggers.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting triggers for table {} in schema {}", tableName, schemaName);

    metadata.setTriggers(helper.extract(extractQuery, new Object[] { tableName, schemaName }, TriggerModel.class));

    log.debug("Extracted {} triggers", metadata.getTriggers().size());
  }
}
