package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.MetadataOutput;

@Component
@ConditionalOnProperty(name = "output.target", havingValue = "html", matchIfMissing = false)
public class HtmlMetadataOutput implements MetadataOutput {
  @Autowired
  private DatabaseMetadataHtmlOutput databaseOutput;

  @Autowired
  private TableMetadataHtmlOutput tableOutput;

  @Value("${output.html.folder}")
  private String outputFolderName;

  private Path outputFolder;

  @PostConstruct
  public void init() throws Exception {
    outputFolder = Paths.get(outputFolderName);
    if (!Files.exists(outputFolder)) {
      Files.createDirectories(outputFolder);
    }
  }

  @Override
  public void output(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata) {
    for (DatabaseMetadata metadata : databaseMetadata) {
      databaseOutput.output(metadata, outputFolder);
    }

    for (TableMetadata metadata : tableMetadata) {
      tableOutput.output(metadata, outputFolder);
    }
  }
}
