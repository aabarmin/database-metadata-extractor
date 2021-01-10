package ru.mydesignstudio.database.metadata.extractor.extract.result

class ReferenceModel(
        var childTable: String,
        var childColumn: String,
        var constraintName: String,
        var parentTable: String,
        var parentColumn: String)