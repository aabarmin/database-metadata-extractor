package ru.mydesignstudio.database.metadata.extractor.extract.result

data class ScriptModel(val schemaName: String,
                       val viewName: String,
                       val script: String)