package ru.mydesignstudio.database.metadata.extractor.parameters

class MetadataSchema(
        val name: String,
        val tables: List<MetadataTable>,
        val labels: List<String>
) {

}