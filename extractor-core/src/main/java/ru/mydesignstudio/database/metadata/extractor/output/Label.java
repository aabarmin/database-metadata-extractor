package ru.mydesignstudio.database.metadata.extractor.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Label {
  private String prefix;
  private String name;
}
