package ru.mydesignstudio.database.metadata.extractor.extractors.pk;

import lombok.Data;

@Data
public class PrimaryKeyModel {
  private String primaryKey;
  private String schemaName;
  private String constraintName;
  private String tableName;
  private String columns;
}
