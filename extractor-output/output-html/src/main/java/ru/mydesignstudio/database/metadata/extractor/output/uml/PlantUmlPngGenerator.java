package ru.mydesignstudio.database.metadata.extractor.output.uml;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.stereotype.Component;

@Component
public class PlantUmlPngGenerator {

    @SneakyThrows
    public void generatePng (@NonNull String plantUmlContent, @NonNull Path directoryPath) {
        final Path outputFile = directoryPath.resolve("erd.png");
        Files.deleteIfExists(outputFile);
        Files.createFile(outputFile);

        final OutputStream png = Files.newOutputStream(outputFile);
        SourceStringReader reader = new SourceStringReader(plantUmlContent);
        reader.generateImage(png);
    }

}
