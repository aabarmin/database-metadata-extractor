package ru.mydesignstudio.database.metadata.extractor.source

import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource

/**
 * Component that extracts metadata from the provided metadata source.
 */
interface SourceMetadataExtractor {
    /**
     * Extract metadata of the source level.
     */
    fun extract(source: MetadataSource): List<DatabaseMetadata>
}