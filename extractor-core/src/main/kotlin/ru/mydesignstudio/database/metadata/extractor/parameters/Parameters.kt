package ru.mydesignstudio.database.metadata.extractor.parameters

import ru.mydesignstudio.database.metadata.extractor.parameters.destination.MetadataDestination
import ru.mydesignstudio.database.metadata.extractor.parameters.source.MetadataSource

/**
 * Extract and output parameters.
 */
class Parameters(
        val sources: List<MetadataSource>,
        val destinations: List<MetadataDestination>
        ) {

}