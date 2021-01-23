package ru.mydesignstudio.database.metadata.extractor.extract.parameters

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.destination.MetadataDestination
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource

/**
 * Extract and output parameters.
 */
data class ExtractParameters(val sources: List<MetadataSource>,
                             val destinations: List<MetadataDestination>)