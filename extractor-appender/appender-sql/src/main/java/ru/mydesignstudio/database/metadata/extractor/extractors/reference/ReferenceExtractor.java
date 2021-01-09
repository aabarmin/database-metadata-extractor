package ru.mydesignstudio.database.metadata.extractor.extractors.reference;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.ReferenceModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
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
