package ru.mydesignstudio.database.metadata.extractor.extractors.view.referenced;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Component
public class ViewReferencedExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_views_referenced.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    metadata.setViewsReferenced(helper.extract(query, new Object[]{ tableName, schemaName }, ViewReferencedModel.class));
  }
}
