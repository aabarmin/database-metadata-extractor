package ru.mydesignstudio.database.metadata.extractor.extract.parameters.validator

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters
import ru.mydesignstudio.database.metadata.extractor.registry.DestinationRegistry
import ru.mydesignstudio.database.metadata.extractor.registry.SourceMetadataExtractorRegistry

/**
 * Basic implementation of database metadata validator. Actually, it relies on the registry
 * of all the metadata extractors.
 */
@Component
class BasicParametersValidator constructor (
        private val sourceRegistry: SourceMetadataExtractorRegistry,
        private val destinationRegistry: DestinationRegistry
) : ParametersValidator {

    override fun validate(parameters: ExtractParameters): ValidationResult {
        for (source in parameters.sources) {
            if (!sourceRegistry.hasExtractor(source.source)) {
                return ValidationResult.failure("There is no database metadata extractor for type ${source.source}")
            }
        }
        for (destination in parameters.destinations) {
            if (!destinationRegistry.hasDestination(destination.destination)) {
                return ValidationResult.failure("There is no destination for type {${destination.destination}}")
            }

            if (!destinationRegistry.getDestination(destination.destination).isValidParams(destination.params)) {
                return ValidationResult.failure("Params for destination ${destination.destination} aren't valid")
            }
        }
        return ValidationResult.ok()
    }
}