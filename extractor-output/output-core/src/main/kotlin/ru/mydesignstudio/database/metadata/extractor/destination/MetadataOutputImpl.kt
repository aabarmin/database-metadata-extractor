package ru.mydesignstudio.database.metadata.extractor.destination

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.registry.DestinationRegistry

@Component
class MetadataOutputImpl(
        private val registry: DestinationRegistry
) : MetadataOutput {
    override fun output(metadata: List<DatabaseMetadata>, params: ExtractParameters) {
        for (destination in params.destinations) {
            for (metadataItem in metadata) {
                val output = registry.getDestination(destination.destination)
                output.output(metadataItem)
            }
        }
    }
}