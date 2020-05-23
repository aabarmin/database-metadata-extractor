package ru.mydesignstudio.database.metadata.extractor.extractors.column;

import lombok.Data;

@Data
public class ColumnModel {
  private int columnId;
  private String schemaName;
  private String tableName;
  private String dataType;
  private int dataLength;
  private Integer dataPrecision;
  private Integer dataScale;
  private String nullable;
  private String defaultDefinition;
  private String comments;
}
