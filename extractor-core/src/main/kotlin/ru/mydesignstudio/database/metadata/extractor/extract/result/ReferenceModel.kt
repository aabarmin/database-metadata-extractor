package ru.mydesignstudio.database.metadata.extractor.extract.result

class ReferenceModel(
        val childTable: String,
        val childColumn: String,
        val constraintName: String,
        val parentTable: String,
        val parentColumn: String)