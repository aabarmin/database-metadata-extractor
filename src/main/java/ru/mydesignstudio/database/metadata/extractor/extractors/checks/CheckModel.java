package ru.mydesignstudio.database.metadata.extractor.extractors.checks;

import lombok.Data;

@Data
public class CheckModel {
  private String schemaName;
  private String constraintName;
  private String tableName;
  private String columnName;
  private String constraint;
  private String status;
}
