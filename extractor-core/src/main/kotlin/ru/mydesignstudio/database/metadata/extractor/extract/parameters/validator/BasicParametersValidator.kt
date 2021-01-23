package ru.mydesignstudio.database.metadata.extractor.extract.parameters.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters
import ru.mydesignstudio.database.metadata.extractor.registry.SourceMetadataExtractorRegistry

/**
 * Basic implementation of database metadata validator. Actually, it relies on the registry
 * of all the metadata extractors.
 */
@Component
class BasicParametersValidator @Autowired constructor (
        private val registry: SourceMetadataExtractorRegistry
) : ParametersValidator {

    override fun validate(parameters: ExtractParameters): ValidationResult {
        for (source in parameters.sources) {
            if (!registry.hasExtractor(source.source)) {
                return ValidationResult.failure("There is no database metadata extractor for type ${source.source}")
            }
        }
        // TODO, validate destinations as well
        return ValidationResult.ok()
    }
}