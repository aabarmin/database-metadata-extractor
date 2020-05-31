package ru.mydesignstudio.database.metadata.extractor.extractors.constraint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ConstraintsExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_constraints.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting constraints for table {} in schema {}", tableName, schemaName);

    metadata.setConstraints(helper.extract(extractQuery, new Object[] { tableName, schemaName }, ConstraintModel.class));

    log.debug("Extracted {} constraints", metadata.getConstraints().size());
  }
}
