package ru.mydesignstudio.database.metadata.extractor.extractors

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.parameters.source.MetadataSource

/**
 * Source metadata extractor that delegates execution to extractors for the particular sources.
 */
@Component
class DelegatingSourceMetadataExtractor @Autowired constructor(
        private val registry: SourceMetadataExtractorRegistry
) : SourceMetadataExtractor {

    override fun extract(source: MetadataSource): List<DatabaseMetadata> {
        return registry.getExtractor(source.source).extract(source)
    }
}