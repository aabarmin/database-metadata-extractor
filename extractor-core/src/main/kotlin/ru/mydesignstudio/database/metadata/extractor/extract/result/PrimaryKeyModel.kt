package ru.mydesignstudio.database.metadata.extractor.extract.result

data class PrimaryKeyModel(
        val primaryKey: String,
        val schemaName: String,
        val constraintName: String,
        val tableName: String,
        val columns: String)