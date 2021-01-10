package ru.mydesignstudio.database.metadata.extractor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.registry.DatabaseMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource

/**
 * Source metadata extractor that delegates execution to extractors for the particular sources.
 */
@Component
class SourceMetadataExtractorImpl @Autowired constructor(
        private val registry: DatabaseMetadataExtractorRegistry
) : SourceMetadataExtractor {

    override fun extract(source: MetadataSource): List<DatabaseMetadata> {
        return registry.getExtractor(source.source).extract(source)
    }
}