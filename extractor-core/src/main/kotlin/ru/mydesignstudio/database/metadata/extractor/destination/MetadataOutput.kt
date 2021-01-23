package ru.mydesignstudio.database.metadata.extractor.destination

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata

interface MetadataOutput {
    fun output(metadata: List<DatabaseMetadata>, params: ExtractParameters): Unit
}