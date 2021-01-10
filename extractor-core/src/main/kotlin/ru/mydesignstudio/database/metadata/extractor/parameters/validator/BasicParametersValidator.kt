package ru.mydesignstudio.database.metadata.extractor.parameters.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extractors.SourceMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.parameters.Parameters

/**
 * Basic implementation of database metadata validator. Actually, it relies on the registry
 * of all the metadata extractors.
 */
@Component
class BasicParametersValidator @Autowired constructor (
        val registry: SourceMetadataExtractorRegistry
) : ParametersValidator {

    override fun validate(parameters: Parameters): ValidationResult {
        for (source in parameters.sources) {
            if (!registry.hasExtractor(source.source)) {
                return ValidationResult.failure("There is no database metadata extractor for type ${source.source}")
            }
        }
        return ValidationResult.ok()
    }
}