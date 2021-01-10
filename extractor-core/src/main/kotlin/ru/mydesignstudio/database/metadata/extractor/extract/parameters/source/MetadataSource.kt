package ru.mydesignstudio.database.metadata.extractor.extract.parameters.source

/**
 * Parameters for the single data source.
 */
class MetadataSource(
        val source: String,
        val connection: MetadataSourceConnection,
        val schemas: List<MetadataSchema>
        ) {

}