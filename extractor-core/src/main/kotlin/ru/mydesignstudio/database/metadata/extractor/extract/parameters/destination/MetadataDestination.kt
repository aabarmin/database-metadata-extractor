package ru.mydesignstudio.database.metadata.extractor.extract.parameters.destination

/**
 * Currently, it's just a dummy class that will describe a destination where the extracted
 * metadata should be published.
 */
data class MetadataDestination(
        val destination: String,
        val params: Map<String, Object>
)