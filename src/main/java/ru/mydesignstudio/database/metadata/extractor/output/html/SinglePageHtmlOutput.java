package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.type.TypeModel;
import ru.mydesignstudio.database.metadata.extractor.output.Output;

@Slf4j
@Component
public class SinglePageHtmlOutput {

  @Autowired
  private TemplateEngine templateEngine;

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
        getObjectType(tableMetadata) + " " + databaseMetadata.getSchemaName() + "." + tableMetadata
            .getTableName(), outputFile);
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
