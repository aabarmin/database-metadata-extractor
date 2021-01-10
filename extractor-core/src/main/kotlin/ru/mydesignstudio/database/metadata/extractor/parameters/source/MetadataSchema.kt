package ru.mydesignstudio.database.metadata.extractor.parameters.source

/**
 * Describes some database to extract metadata from.
 */
class MetadataSchema(
        val name: String,
        val objects: List<MetadataObject>,
        val labels: MetadataSchemaLabels
) {

}