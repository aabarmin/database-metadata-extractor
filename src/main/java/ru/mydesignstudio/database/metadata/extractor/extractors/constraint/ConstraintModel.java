package ru.mydesignstudio.database.metadata.extractor.extractors.constraint;

import lombok.Data;

@Data
public class ConstraintModel {
  private String constraintType;
  private String schemaName;
  private String constraintName;
  private String tableName;
  private String columns;
  private String rOwner;
  private String rConstraintName;
}
