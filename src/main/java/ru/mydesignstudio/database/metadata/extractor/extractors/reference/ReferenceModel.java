package ru.mydesignstudio.database.metadata.extractor.extractors.reference;

import lombok.Data;

@Data
public class ReferenceModel {
  private String childTable;
  private String childColumn;
  private String constraintName;
  private String parentTable;
  private String parentColumn;
}
