package ru.mydesignstudio.database.metadata.extractor.database.reference;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ReferenceModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ReferenceExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_references.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting references for table {} in schema {}", tableName, schemaName);

    metadata.setReferences(helper.extract(extractQuery, new Object[]{ tableName, schemaName }, ReferenceModel.class));

    log.debug("Extracted {} references", metadata.getReferences().size());
  }
}
