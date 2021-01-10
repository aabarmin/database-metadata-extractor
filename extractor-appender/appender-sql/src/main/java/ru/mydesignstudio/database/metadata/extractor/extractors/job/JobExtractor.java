package ru.mydesignstudio.database.metadata.extractor.extractors.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.DatabaseMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.JobModel;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class JobExtractor implements DatabaseMetadataAppender {
  @StringResource("classpath:sql/extract_jobs.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(DatabaseMetadata metadata, String schemaName) {
    log.info("Extracting scheduled jobs for schema {}", schemaName);

    metadata.setJobs(helper.extract(query, new Object[] { schemaName }, JobModel.class));

    log.debug("Extracted {} jobs", metadata.getJobs().size());
  }
}
