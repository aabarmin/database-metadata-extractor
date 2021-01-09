package ru.mydesignstudio.database.metadata.extractor.extractors;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.Pair;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;

import java.util.List;

public interface TableMetadataExtractor {
  // TODO, introduce data model to replace List<Pair<String, String>>
  List<TableMetadata> extract(@NonNull List<Pair<String, String>> values);
}
