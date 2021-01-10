package ru.mydesignstudio.database.metadata.extractor.extract.parameters.source

/**
 * Labels attached to the particular schema.
 */
class MetadataSchemaLabels(
        val table: List<MetadataLabel>,
        val view: List<MetadataLabel>,
        val schema: List<MetadataLabel>,
        val diagram: List<MetadataLabel>
) {
}