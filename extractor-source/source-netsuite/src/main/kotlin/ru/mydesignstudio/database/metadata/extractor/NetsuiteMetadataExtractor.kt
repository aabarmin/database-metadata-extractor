package ru.mydesignstudio.database.metadata.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor

@Component
class NetsuiteMetadataExtractor : SourceMetadataExtractor {
    override fun getSourceType(): String {
        return "NETSUITE"
    }

    override fun extract(source: MetadataSource): List<DatabaseMetadata> {
        // TODO, provide an implementation here
        return emptyList()
    }
}