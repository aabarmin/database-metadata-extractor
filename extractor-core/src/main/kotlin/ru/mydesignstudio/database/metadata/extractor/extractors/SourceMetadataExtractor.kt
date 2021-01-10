package ru.mydesignstudio.database.metadata.extractor.extractors

import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.parameters.source.MetadataSource

/**
 * Component that extracts metadata from the provided metadata source.
 */
interface SourceMetadataExtractor {
    /**
     * Extract metadata of the source level.
     */
    fun extract(source: MetadataSource): List<DatabaseMetadata>
}