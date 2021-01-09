package ru.mydesignstudio.database.metadata.extractor.extractors.udf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.UdfModel;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class UdfExtractor implements TableMetadataAppender {
  @StringResource("classpath:sql/extract_udfs.sql")
  private String extractQuery;

  @Autowired
  private ExtractHelper helper;

  @Override
  public void append(TableMetadata metadata, String schemaName, String tableName) {
    log.info("Extracting UDFs for table {} in schema {}", tableName, schemaName);

    metadata.setUdfs(helper.extract(extractQuery, new Object[] { tableName, schemaName }, UdfModel.class));

    log.debug("Extracted {} UDFs", metadata.getUdfs().size());
  }
}
