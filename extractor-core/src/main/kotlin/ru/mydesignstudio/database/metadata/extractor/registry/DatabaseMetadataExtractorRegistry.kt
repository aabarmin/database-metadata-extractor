package ru.mydesignstudio.database.metadata.extractor.registry

import ru.mydesignstudio.database.metadata.extractor.database.DatabaseMetadataExtractor

/**
 * Registry that stores information about all registered database metadata extractors.
 */
interface DatabaseMetadataExtractorRegistry {
    /**
     * Register a single extractor.
     */
    fun register(extractor: DatabaseMetadataExtractor): Unit

    /**
     * Check if is there any extractor that can extract metadata of a given type.
     */
    fun hasExtractor(type: String): Boolean

    /**
     * Get an extractor for the given database type.
     */
    fun getExtractor(type: String): DatabaseMetadataExtractor
}