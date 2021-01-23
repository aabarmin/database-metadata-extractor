package ru.mydesignstudio.database.metadata.extractor.extract.result

data class ConstraintModel(
        val constraintType: String,
        val schemaName: String,
        val constraintName: String,
        val tableName: String,
        val columns: String,
        val rOwner: String,
        val rConstraintName: String
)