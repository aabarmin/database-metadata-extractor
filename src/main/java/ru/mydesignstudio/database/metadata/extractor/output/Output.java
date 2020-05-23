package ru.mydesignstudio.database.metadata.extractor.output;

import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output {
  private String title;
  private Path filePath;
}
