package ru.mydesignstudio.database.metadata.extractor.extract.parameters.source

/**
 * Describes table or view to extract metadata from.
 */
data class MetadataObject(val name: String, val labels: List<MetadataLabel>)