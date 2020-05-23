package ru.mydesignstudio.database.metadata.extractor.extractors.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.DatabaseMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Component
public class JobExtractor implements DatabaseMetadataAppender {
  @StringResource("classpath:sql/extract_jobs.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(DatabaseMetadata metadata, String schemaName) {
    metadata.setJobs(helper.extract(query, new Object[] { schemaName }, JobModel.class));
  }
}
