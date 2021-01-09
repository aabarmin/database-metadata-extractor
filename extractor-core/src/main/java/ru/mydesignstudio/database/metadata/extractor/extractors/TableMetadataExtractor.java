package ru.mydesignstudio.database.metadata.extractor.extractors;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.parameters.MetadataSource;

import java.util.List;

public interface TableMetadataExtractor {
  List<TableMetadata> extract(@NonNull List<MetadataSource> sources);
}
