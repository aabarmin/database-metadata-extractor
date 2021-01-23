package ru.mydesignstudio.database.metadata.extractor

import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata

interface DatabaseMetadataAppender {
    fun append(metadata: DatabaseMetadata, schemaName: String): Unit
}