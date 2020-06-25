package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.type.TypeModel;
import ru.mydesignstudio.database.metadata.extractor.output.Output;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.CommonLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.TableLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.ViewLabelProvider;

@Slf4j
@Component
public class SinglePageHtmlOutput {

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private CommonLabelProvider commonLabelProvider;

  @Autowired
  private ViewLabelProvider viewLabelProvider;

  @Autowired
  private TableLabelProvider tableLabelProvider;

  @SneakyThrows
  public Output output(DatabaseMetadata databaseMetadata, TableMetadata tableMetadata, Path outputFolder) {
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
    return labels;
  }

  private boolean isView(TableMetadata tableMetadata) {
    return StringUtils.equalsIgnoreCase("View", getObjectType(tableMetadata));
  }

  private boolean isTable(TableMetadata tableMetadata) {
    return StringUtils.equalsIgnoreCase("Table", getObjectType(tableMetadata));
  }

  private String getPageTitle(DatabaseMetadata databaseMetadata, TableMetadata tableMetadata) {
    return getObjectType(tableMetadata) + " " + databaseMetadata.getSchemaName() + "." + tableMetadata
        .getTableName();
  }

  private String getObjectType(TableMetadata tableMetadata) {
    return Optional.ofNullable(tableMetadata)
        .map(TableMetadata::getTypes)
        .map(Collection::iterator)
        .filter(Iterator::hasNext)
        .map(Iterator::next)
        .map(TypeModel::getObjectType)
        .orElse("Unknown");
  }

}
