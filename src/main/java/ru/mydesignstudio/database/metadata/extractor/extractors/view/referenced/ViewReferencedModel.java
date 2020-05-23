package ru.mydesignstudio.database.metadata.extractor.extractors.view.referenced;

import lombok.Data;

@Data
public class ViewReferencedModel {
  private String viewSchema;
  private String viewName;
  private String referencedSchema;
  private String referencedName;
  private String referencedType;
}
