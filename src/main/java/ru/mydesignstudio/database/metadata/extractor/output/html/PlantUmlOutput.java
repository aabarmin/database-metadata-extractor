package ru.mydesignstudio.database.metadata.extractor.output.html;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.Output;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.CommonLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.DiagramLabelProvider;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;
import ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml.PlantUmlMarkupGenerator;
import ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml.PlantUmlPngGenerator;

@Slf4j
@Component
public class PlantUmlOutput {
  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private PlantUmlMarkupGenerator markupGenerator;

  @Autowired
  private PlantUmlPngGenerator pngGenerator;

  @Autowired
  private CommonLabelProvider commonLabelProvider;

  @Autowired
  private DiagramLabelProvider diagramLabelProvider;

  @SneakyThrows
  public Output output(@NonNull List<DatabaseMetadata> databaseMetadata,
      @NonNull List<TableMetadata> tableMetadata,
      @NonNull Path outputFolder) {

    log.info("Generating PlantUML output for tables {}", tableMetadata);

    final Path outputFile = outputFolder.resolve("plan_uml.html");
    Files.deleteIfExists(outputFile);
    Files.createFile(outputFile);

    final Context context = new Context();
    context.setVariable("markup", markupGenerator.generate(tableMetadata));
    final String content = templateEngine.process("plant-uml-template", context);
    Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
    pngGenerator
        .generatePng(markupGenerator.generate(tableMetadata), outputFolder);

    return new Output("ERD " + LocalDateTime.now(), outputFile, getLabels());
  }

  private Set<Label> getLabels() {
    final Set<Label> labels = new HashSet<>();
    labels.addAll(commonLabelProvider.provide());
    labels.addAll(diagramLabelProvider.provide());
    return labels;
  }
}
