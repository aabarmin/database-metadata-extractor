package ru.mydesignstudio.database.metadata.extractor.parameters;

import lombok.Data;

import java.util.List;

@Data
public class MetadataSource {
  private String source;
  private List<MetadataSchema> schemas;
}
