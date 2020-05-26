package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.Output;
import ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml.PlantUmlMarkupGenerator;
import ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml.PlantUmlPngGenerator;

@Component
public class PlantUmlOutput {
  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private PlantUmlMarkupGenerator markupGenerator;
  @Autowired
  private PlantUmlPngGenerator pngGenerator;

  @SneakyThrows
  public Output output(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata,
      Path outputFolder) {

    final Path outputFile = outputFolder.resolve("plan_uml.html");
    Files.deleteIfExists(outputFile);
    Files.createFile(outputFile);

    final Context context = new Context();
    context.setVariable("markup", markupGenerator.generate(databaseMetadata, tableMetadata));
    final String content = templateEngine.process("plant-uml-template", context);
    Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
    pngGenerator.generatePng(markupGenerator.generate(databaseMetadata, tableMetadata), outputFolder);

    return new Output("ERD " + LocalDateTime.now(), outputFile);
  }
}
