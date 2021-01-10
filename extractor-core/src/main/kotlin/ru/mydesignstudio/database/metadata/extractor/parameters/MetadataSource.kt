package ru.mydesignstudio.database.metadata.extractor.parameters

class MetadataSource(
        val source: String,
        val connection: MetadataSourceConnection,
        val schemas: List<MetadataSchema>
        ) {

}