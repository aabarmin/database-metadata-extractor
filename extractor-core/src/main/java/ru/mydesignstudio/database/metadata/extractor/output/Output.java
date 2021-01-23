package ru.mydesignstudio.database.metadata.extractor.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output {
  private String title;
  private Path filePath;
  private Set<Label> labels = new HashSet<>();
}
