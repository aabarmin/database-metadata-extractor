package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfluenceStorage {
  private String value;
  private String representation = "storage";
}
