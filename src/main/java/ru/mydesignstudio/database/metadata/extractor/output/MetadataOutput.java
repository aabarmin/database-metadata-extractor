package ru.mydesignstudio.database.metadata.extractor.output;

import java.util.List;
import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;

public interface MetadataOutput {
  List<Output> output(@NonNull List<DatabaseMetadata> databaseMetadata, @NonNull List<TableMetadata> tableMetadata);
}
