package ru.mydesignstudio.database.metadata.extractor.database.constraint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ConstraintModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
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
