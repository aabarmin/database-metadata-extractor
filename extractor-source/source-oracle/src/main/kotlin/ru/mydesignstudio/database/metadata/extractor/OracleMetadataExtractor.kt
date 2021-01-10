package ru.mydesignstudio.database.metadata.extractor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extractors.DatabaseMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.extractors.SourceMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.parameters.source.MetadataSource
import java.util.stream.Collectors

@Component
class OracleMetadataExtractor @Autowired constructor(
        private val appends: List<DatabaseMetadataAppender>
) : DatabaseMetadataExtractor {

    override fun extract(@NonNull source: MetadataSource): List<DatabaseMetadata> {
        return source.schemas
                .stream()
                .map(MetadataSchema::name)
                .map { schemaName: String -> this.extract(schemaName) }
                .collect(Collectors.toList())
    }

    private fun extract(@NonNull schemaName: String): DatabaseMetadata {
        val metadata = DatabaseMetadata()
        metadata.schemaName = schemaName
        for (appender in appends) {
            appender.append(metadata, schemaName)
        }
        return metadata
    }

    override fun getSourceType(): String {
        return "ORACLE"
    }

    @Autowired
    override fun register(registry: SourceMetadataExtractorRegistry) {
        registry.register(this)
    }
}