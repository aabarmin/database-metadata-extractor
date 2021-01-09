package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class JobModel {
  private String schemaName;
  private String jobName;
  private String jobStyle;
  private String jobType;
  private String jobAction;
  private String startDate;
  private String schedule;
  private String lastStartDate;
  private String nextRunDate;
  private String state;
}
