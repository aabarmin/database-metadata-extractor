package ru.mydesignstudio.database.metadata.extractor.extract.parameters.source

/**
 * Describes some database to extract metadata from.
 */
data class MetadataSchema(
        val name: String,
        val objects: Collection<MetadataObject>,
        val labels: MetadataSchemaLabels
)