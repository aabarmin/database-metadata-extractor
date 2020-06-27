package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.Output;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.provider.CommonLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.provider.DatabaseLabelProvider;

@Slf4j
@Component
public class DatabaseMetadataHtmlOutput {
  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private CommonLabelProvider commonLabelProvider;

  @Autowired
  private DatabaseLabelProvider databaseLabelProvider;
  
  @SneakyThrows
  public Output output(@NonNull DatabaseMetadata metadata, @NonNull Path outputFolder) {
    log.info("Generating HTML output for schema {}", metadata.getSchemaName());

    final Path outputFile = outputFolder.resolve(metadata.getSchemaName() + ".html");
    Files.deleteIfExists(outputFile);
    Files.createFile(outputFile);

    log.debug("Output to file {}", outputFile);

    final Context context = new Context();
    context.setVariable("metadata", metadata);
    final String content = templateEngine.process("database-template", context);

    Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);

    return new Output("Database" + " " + metadata.getSchemaName(), outputFile, getLabels());
  }

  private Set<Label> getLabels() {
    final Set<Label> labels = new HashSet<>();
    labels.addAll(commonLabelProvider.provide());
    labels.addAll(databaseLabelProvider.provide());
    return labels;
  }
}
