package ru.mydesignstudio.database.metadata.extractor.schema

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata

/**
 * Extracts metadata from a particular schema of the Oracle database.
 */
interface SchemaMetadataExtractor {
    /**
     * Extract metadata from a single Oracle schema.
     */
    fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): DatabaseMetadata
}