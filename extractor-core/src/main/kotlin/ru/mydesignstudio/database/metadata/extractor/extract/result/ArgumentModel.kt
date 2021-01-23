package ru.mydesignstudio.database.metadata.extractor.extract.result

data class ArgumentModel(
        val schemaName: String,
        val procedureName: String,
        val argumentName: String,
        val inOut: String,
        val dataType: String,
        val dataLength: Int,
        val dataPrecision: Int,
        val dataScale: Int,
        val defaulted: String,
        val defaultValue: String
)