package ru.mydesignstudio.database.metadata.extractor.extractors.type;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class TypeExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_type.sql")
  private String typeQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting types for table {} in schema {}", tableName, schemaName);

    metadata.setTypes(helper.extract(typeQuery, new Object[]{tableName, schemaName}, TypeModel.class));

    log.debug("Extracted {} types", metadata.getTypes().size());
  }
}