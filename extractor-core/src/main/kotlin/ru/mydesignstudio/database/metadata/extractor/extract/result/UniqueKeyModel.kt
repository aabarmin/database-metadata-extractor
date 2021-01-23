package ru.mydesignstudio.database.metadata.extractor.extract.result

data class UniqueKeyModel(val owner: String,
                          val constraintName: String,
                          val tableName: String,
                          val columnName: String,
                          val uniqueKey: String)