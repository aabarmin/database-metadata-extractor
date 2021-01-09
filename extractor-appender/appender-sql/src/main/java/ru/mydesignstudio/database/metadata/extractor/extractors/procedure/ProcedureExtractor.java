package ru.mydesignstudio.database.metadata.extractor.extractors.procedure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.DatabaseMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.ProcedureModel;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class ProcedureExtractor implements DatabaseMetadataAppender {
  @StringResource("classpath:sql/extract_procedures.sql")
  private String query;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(DatabaseMetadata metadata, String schemaName) {
    log.info("Extracting stored procedures from schema {}", schemaName);

    metadata.setProcedures(helper.extract(query, new Object[] { schemaName }, ProcedureModel.class));

    log.debug("Extracted {} stored procedures", metadata.getProcedures().size());
  }
}
