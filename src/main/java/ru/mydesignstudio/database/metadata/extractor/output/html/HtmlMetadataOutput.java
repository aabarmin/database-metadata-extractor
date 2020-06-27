package ru.mydesignstudio.database.metadata.extractor.output.html;

import static com.google.common.base.Preconditions.checkNotNull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.MetadataOutput;
import ru.mydesignstudio.database.metadata.extractor.output.Output;

@Component
public class HtmlMetadataOutput implements MetadataOutput {

  @Autowired
  private DatabaseMetadataHtmlOutput databaseOutput;

  @Autowired
  private TableMetadataHtmlOutput tableOutput;

  @Autowired
  private SinglePageHtmlOutput singleOutput;

  @Autowired
  private PlantUmlOutput plantUmlOutput;

  @Value("${output.html.folder}")
  private String outputFolderName;

  @Value("${output.html.mode}")
  private String outputMode;

  private Path outputFolder;

  @PostConstruct
  public void init() throws Exception {
    outputFolder = Paths.get(outputFolderName);
    if (!Files.exists(outputFolder)) {
      Files.createDirectories(outputFolder);
    }
  }

  @Override
  public List<Output> output(@NonNull List<DatabaseMetadata> databaseMetadata, @NonNull List<TableMetadata> tableMetadata) {
    checkNotNull(databaseMetadata, "Database metadata should not be null");
    checkNotNull(tableMetadata, "Table metadata should not be null");
    
    final List<Output> outputs = new ArrayList<>();
    outputs.add(plantUmlOutput.output(databaseMetadata, tableMetadata, outputFolder));

    if (outputMode.equals("single")) {
      outputs.addAll(singleFileOutput(databaseMetadata, tableMetadata));
    } else {
      outputs.addAll(outputMultipleFiles(databaseMetadata, tableMetadata));
    }
    return outputs;
  }

  private List<Output> singleFileOutput(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata) {
    final List<Output> results = new ArrayList<>();

    for (DatabaseMetadata databaseItem : databaseMetadata) {
      for (TableMetadata tableItem : tableMetadata) {
        if (databaseItem.getSchemaName().equals(tableItem.getSchemaName())) {
          results.add(singleOutput.output(databaseItem, tableItem, outputFolder));
        }
      }
    }

    return results;
  }

  private List<Output> outputMultipleFiles(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata) {
    final List<Output> results = new ArrayList<>();

    for (DatabaseMetadata metadata : databaseMetadata) {
      results.add(databaseOutput.output(metadata, outputFolder));
    }

    for (TableMetadata metadata : tableMetadata) {
      results.add(tableOutput.output(metadata, outputFolder));
    }

    return results;
  }
}
