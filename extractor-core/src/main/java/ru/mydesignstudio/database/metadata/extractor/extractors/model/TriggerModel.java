package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class TriggerModel {
  private String triggerSchemaName;
  private String triggerName;
  private String triggerType;
  private String triggeringEvent;
  private String schemaName;
  private String objectName;
  private String objectType;
  private String status;
  private String script;
}
