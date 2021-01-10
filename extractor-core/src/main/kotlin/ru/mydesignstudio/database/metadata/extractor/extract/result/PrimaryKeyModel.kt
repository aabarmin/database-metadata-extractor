package ru.mydesignstudio.database.metadata.extractor.extract.result

class PrimaryKeyModel(
        var primaryKey: String,
        var schemaName: String,
        var constraintName: String,
        var tableName: String,
        var columns: String)