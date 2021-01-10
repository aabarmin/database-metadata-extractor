package ru.mydesignstudio.database.metadata.extractor.parameters.source

/**
 * Parameters for the single data source.
 */
class MetadataSource(
        val source: String,
        val connection: MetadataSourceConnection,
        val schemas: List<MetadataSchema>
        ) {

}