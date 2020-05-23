package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
  public List<Output> output(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata) {
    final List<Output> results = new ArrayList<>();
    for (DatabaseMetadata metadata : databaseMetadata) {
      results.add(databaseOutput.output(metadata, outputFolder));
    }
  public void output(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata) {
    if (outputMode.equals("single")) {
      for (DatabaseMetadata databaseItem : databaseMetadata) {
        for (TableMetadata tableItem : tableMetadata) {
            if (databaseItem.getSchemaName().equals(tableItem.getSchemaName())) {
              singleOutput.output(databaseItem,tableItem, outputFolder);
            }
        }
      }

    } else {
      for (DatabaseMetadata metadata : databaseMetadata) {
        databaseOutput.output(metadata, outputFolder);
      }

      for (TableMetadata metadata : tableMetadata) {
        tableOutput.output(metadata, outputFolder);
      }
    for (TableMetadata metadata : tableMetadata) {
      results.add(tableOutput.output(metadata, outputFolder));
    }
    return results;
  }
}
