package ru.mydesignstudio.database.metadata.extractor.output;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output {
  private String title;
  private Path filePath;
  private Set<Label> labels = new HashSet<>();
}
