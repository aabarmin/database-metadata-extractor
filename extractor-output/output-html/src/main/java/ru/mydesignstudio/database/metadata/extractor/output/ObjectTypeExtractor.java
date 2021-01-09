package ru.mydesignstudio.database.metadata.extractor.output;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import com.google.common.base.Preconditions;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TypeModel;

/**
 * Extracts metadata type - if this metadata belongs to view or table. If there is no
 * enough metadata, the Unknown value is returned.
 */
@Component
public class ObjectTypeExtractor {
  public String extract(@NonNull TableMetadata metadata) {
    Preconditions.checkNotNull(metadata);

    return Optional.ofNullable(metadata)
        .map(TableMetadata::getTypes)
        .map(Collection::iterator)
        .filter(Iterator::hasNext)
        .map(Iterator::next)
        .map(TypeModel::getObjectType)
        .orElse("Unknown");
  }
}
