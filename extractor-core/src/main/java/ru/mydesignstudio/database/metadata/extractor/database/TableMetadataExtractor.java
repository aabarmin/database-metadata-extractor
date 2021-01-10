package ru.mydesignstudio.database.metadata.extractor.database;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource;

import java.util.List;

public interface TableMetadataExtractor {
  List<TableMetadata> extract(@NonNull List<MetadataSource> sources);
}
