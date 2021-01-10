package ru.mydesignstudio.database.metadata.extractor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.database.DatabaseMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.registry.DatabaseMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource

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
    override fun register(registry: DatabaseMetadataExtractorRegistry) {
        registry.register(this)
    }
}