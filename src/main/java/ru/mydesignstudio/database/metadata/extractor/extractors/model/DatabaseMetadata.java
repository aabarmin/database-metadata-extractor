package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import java.util.List;
import lombok.Data;
import ru.mydesignstudio.database.metadata.extractor.extractors.job.JobModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.procedure.ProcedureModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.procedure.argument.ArgumentModel;

@Data
public class DatabaseMetadata {
  private String schemaName;
  private List<ProcedureModel> procedures;
  private List<ArgumentModel> arguments;
  private List<JobModel> jobs;
}
