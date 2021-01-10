package ru.mydesignstudio.database.metadata.extractor.database

import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource
import ru.mydesignstudio.database.metadata.extractor.registry.DatabaseMetadataExtractorRegistry

/**
 * Component that is responsible for extraction of database-level metadata.
 */
interface DatabaseMetadataExtractor {
    /**
     * Type of the source this metadata extractor supports, ex Oracle, Netsuite, MySQL, etc.
     */
    fun getSourceType(): String

    /**
     * Extract metadata from all instances of a given source.
     */
    fun extract(source: MetadataSource): List<DatabaseMetadata>

    /**
     * Any implementation of this interface must be registered in the registry.
     */
    fun register(registry: DatabaseMetadataExtractorRegistry): Unit
}