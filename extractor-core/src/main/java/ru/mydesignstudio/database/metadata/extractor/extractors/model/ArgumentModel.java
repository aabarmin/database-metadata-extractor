package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class ArgumentModel {
  private String schemaName;
  private String procedureName;
  private String argumentName;
  private String inOut;
  private String dataType;
  private Integer dataLength;
  private Integer dataPrecision;
  private Integer dataScale;
  private String defaulted;
  private String defaultValue;
}
