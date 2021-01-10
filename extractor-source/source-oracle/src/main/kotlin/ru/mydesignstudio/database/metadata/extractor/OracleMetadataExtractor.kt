package ru.mydesignstudio.database.metadata.extractor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.database.DatabaseMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.registry.DatabaseMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource
import java.util.stream.Collectors

// TODO, refactor this class, it looks messy
// TODO, this class should not rely on a global JdbcTemplate, data source should be configured from scratch every time
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
        TODO("Rewrite this code")
        /*
        val metadata = DatabaseMetadata()
        metadata.schemaName = schemaName
        for (appender in appends) {
            appender.append(metadata, schemaName)
        }
        return metadata
         */
    }

    override fun getSourceType(): String {
        return "ORACLE"
    }

    @Autowired
    override fun register(registry: DatabaseMetadataExtractorRegistry) {
        registry.register(this)
    }
}