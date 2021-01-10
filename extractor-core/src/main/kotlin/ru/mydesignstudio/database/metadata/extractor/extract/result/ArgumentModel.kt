package ru.mydesignstudio.database.metadata.extractor.extract.result

class ArgumentModel(
        val schemaName: String = "",
        val procedureName: String = "",
        val argumentName: String = "",
        val inOut: String = "",
        val dataType: String = "",
        val dataLength: Int = 0,
        val dataPrecision: Int = 0,
        val dataScale: Int = 0,
        val defaulted: String = "",
        val defaultValue: String = ""
) {
}