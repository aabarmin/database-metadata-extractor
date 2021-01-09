package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class ForeignKeyModel {
  private String foreignKey;
  private String constraintType;
  private String schemaName;
  private String constraintName;
  private String tableName;
  private String columns;
  private String rOwner;
  private String rConstraintName;
}
