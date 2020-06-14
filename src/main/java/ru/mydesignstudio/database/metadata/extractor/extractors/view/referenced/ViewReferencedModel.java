package ru.mydesignstudio.database.metadata.extractor.extractors.view.referenced;

import lombok.Data;

@Data
public class ViewReferencedModel {
  private String referencingObject;
  private String referencingType;
  private String referencedObject;
  private String referencedType;
}
