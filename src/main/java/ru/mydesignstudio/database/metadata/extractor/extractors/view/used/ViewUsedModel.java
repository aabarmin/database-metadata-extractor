package ru.mydesignstudio.database.metadata.extractor.extractors.view.used;

import lombok.Data;

@Data
public class ViewUsedModel {
  private String tableName;
  private String type;
  private String referencingObject;
  private String referencingType;
}
