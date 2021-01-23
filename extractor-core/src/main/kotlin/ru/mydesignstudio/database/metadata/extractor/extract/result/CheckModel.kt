package ru.mydesignstudio.database.metadata.extractor.extract.result

data class CheckModel(
        val schemaName: String,
        val constraintName: String,
        val tableName: String,
        val columnName: String,
        val constraint: String,
        val status: String
)