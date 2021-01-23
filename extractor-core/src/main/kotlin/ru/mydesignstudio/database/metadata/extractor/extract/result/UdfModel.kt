package ru.mydesignstudio.database.metadata.extractor.extract.result

data class UdfModel(val schemaName: String,
                    val functionName: String,
                    val returnType: String,
                    val arguments: String)