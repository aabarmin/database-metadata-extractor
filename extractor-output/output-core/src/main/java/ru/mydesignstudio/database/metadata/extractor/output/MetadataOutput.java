package ru.mydesignstudio.database.metadata.extractor.output;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;

import java.util.List;

@Deprecated
public interface MetadataOutput {
  // TODO, Parameters should be taken into account here, like labels.
  @Deprecated
  List<Output> output(@NonNull List<DatabaseMetadata> databaseMetadata, @NonNull List<TableMetadata> tableMetadata);
}
