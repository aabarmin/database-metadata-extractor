package ru.mydesignstudio.database.metadata.extractor.extract.parameters.validator

/**
 * Result of the configuration parameters validation.
 */
class ValidationResult private constructor(val isValid: Boolean, val message: String) {
    companion object {
        @JvmStatic
        fun ok(): ValidationResult {
            return ValidationResult(true, "OK")
        }

        @JvmStatic
        fun failure(message: String): ValidationResult {
            return ValidationResult(false, message)
        }
    }
}