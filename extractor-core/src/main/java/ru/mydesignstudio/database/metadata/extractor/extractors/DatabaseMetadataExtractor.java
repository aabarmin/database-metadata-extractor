package ru.mydesignstudio.database.metadata.extractor.extractors;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.parameters.MetadataSource;

import java.util.List;

public interface DatabaseMetadataExtractor {
  List<DatabaseMetadata> extract(@NonNull List<MetadataSource> sources);
}
