package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.Output;

@Component
public class TableMetadataHtmlOutput {
  @Autowired
  private TemplateEngine templateEngine;

  @SneakyThrows
  public Output output(@NonNull TableMetadata metadata, @NonNull Path outputFolder) {
    final Path outputFile = outputFolder.resolve(metadata.getSchemaName() + "_" + metadata.getTableName() + ".html");
    Files.deleteIfExists(outputFile);
    Files.createFile(outputFile);

    final Context context = new Context();
    context.setVariable("metadata", metadata);
    final String content = templateEngine.process("table-template", context);

    Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);

    return new Output(metadata.getSchemaName() + "_" + metadata.getTableName(), outputFile);
  }
}
