package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class ViewUsedModel {
  private String referencingObject;
  private String referencingType;
  private String referencedObject;
  private String referencedType;
}
