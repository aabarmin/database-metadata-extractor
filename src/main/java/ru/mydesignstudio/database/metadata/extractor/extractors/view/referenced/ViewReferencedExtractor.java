package ru.mydesignstudio.database.metadata.extractor.extractors.view.referenced;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ViewReferencedExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_views_referenced.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting referenced views for table {} in schema {}", tableName, schemaName);

    metadata.setViewsReferenced(helper.extract(query, new Object[]{ tableName, schemaName }, ViewReferencedModel.class));

    log.debug("Extracted {} referenced views", metadata.getViewsReferenced().size());
  }
}
