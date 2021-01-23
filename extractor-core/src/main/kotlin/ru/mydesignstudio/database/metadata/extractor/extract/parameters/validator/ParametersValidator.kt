package ru.mydesignstudio.database.metadata.extractor.extract.parameters.validator

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters

/**
 * Validates parameters, ex checks if all the sources are supported.
 */
interface ParametersValidator {
    /**
     * Validate if a given parameters object is valid.
     */
    fun validate(parameters: ExtractParameters): ValidationResult
}