package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class ProcedureModel {
  private String schemaName;
  private String procedureName;
  private String arguments;
}
