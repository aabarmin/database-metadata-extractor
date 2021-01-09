package ru.mydesignstudio.database.metadata.extractor.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.DatabaseMetadataExtractor;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataExtractor;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.Pair;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.MetadataOutput;
import ru.mydesignstudio.database.metadata.extractor.parameters.Parameters;
import ru.mydesignstudio.database.metadata.extractor.parameters.ParametersReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
    final List<DatabaseMetadata> databaseMetadata = databaseExtractor.extract(parameters.getSchemas());
    log.info("Done");

    log.info("Reading tables metadata");
    final List<TableMetadata> tableMetadata = tableExtractor.extract(toPairs(parameters));
    log.info("Done");

    log.info("Output");
    metadataOutput.output(databaseMetadata, tableMetadata);
    log.info("Done");
  }

  private List<Pair<String, String>> toPairs(@NonNull Parameters parameters) {
    final List<Pair<String, String>> tables = new ArrayList<>();

    for (Entry<String, List<String>> entry : parameters.getTables().entrySet()) {
      for (String tableName : entry.getValue()) {
        tables.add(new Pair<>(entry.getKey(), tableName));
      }
    }

    return tables;
  }
}
