package ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml;

import lombok.SneakyThrows;
import lombok.Value;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class PlantUmlPngGenerator {

    @SneakyThrows
    public void generatePng (String markup, Path path) {
        final Path outputFile = path.resolve("erd.png");
        Files.deleteIfExists(outputFile);
        Files.createFile(outputFile);

        OutputStream png = Files.newOutputStream(outputFile);
        String source = markup;

        SourceStringReader reader = new SourceStringReader(source);
        String desc = reader.generateImage(png);
    }

}
