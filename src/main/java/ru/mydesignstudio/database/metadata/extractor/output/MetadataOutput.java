package ru.mydesignstudio.database.metadata.extractor.output;

import java.util.List;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;

public interface MetadataOutput {
  void output(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata);
}
