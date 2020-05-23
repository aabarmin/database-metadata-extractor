package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;

@Component
public class DatabaseMetadataHtmlOutput {
  @Autowired
  private TemplateEngine templateEngine;

  @SneakyThrows
  public void output(@NonNull DatabaseMetadata metadata, @NonNull Path outputFolder) {
    final Path outputFile = outputFolder.resolve(metadata.getSchemaName() + ".html");
    Files.deleteIfExists(outputFile);
    Files.createFile(outputFile);

    final Context context = new Context();
    context.setVariable("metadata", metadata);
    final String content = templateEngine.process("database-template", context);

    Files.writeString(outputFile, content, Charset.forName("UTF-8"));
  }
}
