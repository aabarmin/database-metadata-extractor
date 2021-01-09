package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import java.util.Collection;
import lombok.Data;

@Data
public class DatabaseMetadata {
  private String schemaName;
  private Collection<ProcedureModel> procedures;
  private Collection<ArgumentModel> arguments;
  private Collection<JobModel> jobs;
}
