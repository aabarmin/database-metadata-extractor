package ru.mydesignstudio.database.metadata.extractor.output;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.provider.CommonLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.provider.DatabaseLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.provider.TableLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.provider.ViewLabelProvider;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class SinglePageHtmlOutput {

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private ObjectTypeExtractor objectTypeExtractor;

  @Autowired
  private CommonLabelProvider commonLabelProvider;

  @Autowired
  private ViewLabelProvider viewLabelProvider;

  @Autowired
  private TableLabelProvider tableLabelProvider;

  @Autowired
  private DatabaseLabelProvider databaseLabelProvider;

  @SneakyThrows
  public Output output(@NonNull DatabaseMetadata databaseMetadata, @NonNull TableMetadata tableMetadata, @NonNull Path outputFolder) {
    Preconditions.checkNotNull(databaseMetadata, "Database metadata should not be null");
    Preconditions.checkNotNull(tableMetadata, "Table metadata should not be null");
    Preconditions.checkNotNull(outputFolder, "Output folder should not be null");

    log.info("Generating HTML output for table {} in schema {}", tableMetadata.getTableName(), tableMetadata.getSchemaName());

    final Path outputFile = outputFolder.resolve(databaseMetadata.getSchemaName() + "." + tableMetadata.getTableName() + ".html");
    Files.deleteIfExists(outputFile);
    Files.createFile(outputFile);

    log.debug("Output to file {}", outputFile);

    final Context context = new Context();
    context.setVariable("databaseMetadata", databaseMetadata);
    context.setVariable("tableMetadata", tableMetadata);

    final String content = templateEngine.process("database-table-template", context);
    Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);

    return new Output(
        getPageTitle(databaseMetadata, tableMetadata),
        outputFile,
        getLabels(tableMetadata)
    );
  }

  private Set<Label> getLabels(TableMetadata tableMetadata) {
    final Set<Label> labels = new HashSet<>();
    labels.addAll(commonLabelProvider.provide());
    if (isView(tableMetadata)) {
      labels.addAll(viewLabelProvider.provide());
    }
    if (isTable(tableMetadata)) {
      labels.addAll(tableLabelProvider.provide());
    }
    labels.addAll(databaseLabelProvider.provide());
    return labels;
  }

  private boolean isView(TableMetadata tableMetadata) {
    return StringUtils.equalsIgnoreCase("View", objectTypeExtractor.extract(tableMetadata));
  }

  private boolean isTable(TableMetadata tableMetadata) {
    return StringUtils.equalsIgnoreCase("Table", objectTypeExtractor.extract(tableMetadata));
  }

  private String getPageTitle(DatabaseMetadata databaseMetadata, TableMetadata tableMetadata) {
    return objectTypeExtractor.extract(tableMetadata) + " " + databaseMetadata.getSchemaName() + "." + tableMetadata
        .getTableName();
  }

}
