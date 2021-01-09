package ru.mydesignstudio.database.metadata.extractor.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.DatabaseMetadataExtractor;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataExtractor;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.MetadataOutput;
import ru.mydesignstudio.database.metadata.extractor.parameters.Parameters;
import ru.mydesignstudio.database.metadata.extractor.parameters.ParametersReader;

import java.util.List;

@Slf4j
@Component
public class ApplicationRunner implements CommandLineRunner {
  @Autowired
  private TableMetadataExtractor tableExtractor;

  @Autowired
  private DatabaseMetadataExtractor databaseExtractor;

  @Autowired
  private MetadataOutput metadataOutput;

  @Autowired
  private ParametersReader parametersReader;

  @Override
  public void run(String... args) throws Exception {
    log.info("Reading parameters file");
    final Parameters parameters = parametersReader.read();
    log.info("Done");

    log.info("Reading database metadata");
    final List<DatabaseMetadata> databaseMetadata = databaseExtractor.extract(parameters.getSources());
    log.info("Done");

    log.info("Reading tables metadata");
    final List<TableMetadata> tableMetadata = tableExtractor.extract(parameters.getSources());
    log.info("Done");

    log.info("Output");
    metadataOutput.output(databaseMetadata, tableMetadata);
    log.info("Done");
  }
}
