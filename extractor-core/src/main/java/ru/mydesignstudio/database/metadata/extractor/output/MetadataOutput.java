package ru.mydesignstudio.database.metadata.extractor.output;

import java.util.List;
import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;

public interface MetadataOutput {
  // TODO, Parameters should be taken into account here, like labels.
  List<Output> output(@NonNull List<DatabaseMetadata> databaseMetadata, @NonNull List<TableMetadata> tableMetadata);
}
