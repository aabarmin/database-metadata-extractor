package ru.mydesignstudio.database.metadata.extractor.registry

import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor

/**
 * Registry that stores information about all registered database metadata extractors.
 */
interface SourceMetadataExtractorRegistry {
    /**
     * Register a single extractor.
     */
    fun register(extractor: SourceMetadataExtractor): Unit

    /**
     * Check if is there any extractor that can extract metadata of a given type.
     */
    fun hasExtractor(type: String): Boolean

    /**
     * Get an extractor for the given database type.
     */
    fun getExtractor(type: String): SourceMetadataExtractor
}