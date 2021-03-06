package ru.mydesignstudio.database.metadata.extractor.parameters;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Parameters {
  private List<String> schemas;
  private Map<String, List<String>> tables;
}
