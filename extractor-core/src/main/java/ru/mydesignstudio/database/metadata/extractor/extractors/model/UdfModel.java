package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class UdfModel {
  private String schemaName;
  private String functionName;
  private String returnType;
  private String arguments;
}
