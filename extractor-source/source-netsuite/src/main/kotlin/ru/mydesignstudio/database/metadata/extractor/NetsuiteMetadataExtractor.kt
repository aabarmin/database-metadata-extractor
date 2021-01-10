package ru.mydesignstudio.database.metadata.extractor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extractors.DatabaseMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.extractors.SourceMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.parameters.source.MetadataSource

@Component
class NetsuiteMetadataExtractor : DatabaseMetadataExtractor {
    override fun getSourceType(): String {
        return "NETSUITE"
    }

    override fun extract(source: MetadataSource): List<DatabaseMetadata> {
        // TODO, provide an implementation here
        return emptyList()
    }

    @Autowired
    override fun register(registry: SourceMetadataExtractorRegistry) {
        registry.register(this)
    }
}