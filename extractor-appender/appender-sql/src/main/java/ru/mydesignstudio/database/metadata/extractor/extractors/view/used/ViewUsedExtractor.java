package ru.mydesignstudio.database.metadata.extractor.extractors.view.used;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.ViewUsedModel;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ViewUsedExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_views_used.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting used views for table {} in schema {}", tableName, schemaName);

    metadata.setViewsUsed(helper.extract(query, new Object[]{ tableName, schemaName }, ViewUsedModel.class));

    log.debug("Extracted {} used views", metadata.getViewsUsed().size());
  }
}
