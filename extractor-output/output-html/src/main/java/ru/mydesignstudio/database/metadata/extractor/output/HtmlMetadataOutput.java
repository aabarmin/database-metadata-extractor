package ru.mydesignstudio.database.metadata.extractor.output;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.destination.OutputDestination;
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class HtmlMetadataOutput implements OutputDestination {
  @Autowired
  private SinglePageHtmlOutput singleOutput;

  @Autowired
  private PlantUmlOutput plantUmlOutput;

  @Override
  public boolean isValidParams(@NotNull Map<String, String> params) {
    if (!params.containsKey("targetDirectory")) {
      log.warn("targetDirectory property is not present in the HTML output params");
      return false;
    }
    return true;
  }

  @NotNull
  @Override
  public Collection<Output> output(@NotNull DatabaseMetadata metadata, @NotNull Map<String, String> params) {
    final Path outputDirectory = createOutputDirectories(params.get("targetDirectory"));
    final Collection<Output> outputs = Lists.newArrayList();

    outputs.add(outputPlantUml(metadata, outputDirectory));
    outputs.addAll(outputHtml(metadata, outputDirectory));

    return outputs;
  }

  private Collection<Output> outputHtml(DatabaseMetadata metadata, Path outputDirectory) {
    return singleOutput.output(metadata, outputDirectory);
  }

  /**
   * Generate output file for PlantUML.
   * @param metadata
   * @param outputDirectory
   */
  private Output outputPlantUml(DatabaseMetadata metadata, Path outputDirectory) {
    return plantUmlOutput.output(metadata, outputDirectory);
  }

  @SneakyThrows
  private Path createOutputDirectories(String targetDirectory) {
    final Path path = Paths.get(targetDirectory);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
    return path;
  }

  @NotNull
  @Override
  public String getDestinationType() {
    return "HTML";
  }
}
