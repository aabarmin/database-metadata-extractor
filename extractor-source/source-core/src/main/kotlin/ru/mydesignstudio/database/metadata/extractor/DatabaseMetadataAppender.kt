package ru.mydesignstudio.database.metadata.extractor

import kotlin.Unit;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata

interface DatabaseMetadataAppender {
    fun append(metadata: DatabaseMetadata, schemaName: String): Unit
}