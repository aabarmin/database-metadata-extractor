package ru.mydesignstudio.database.metadata.extractor

import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.schema.SchemaMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor

@Component
class OracleMetadataExtractor @Autowired constructor(
        private val schemaExtractor: SchemaMetadataExtractor
) : SourceMetadataExtractor {

    override fun extract(source: MetadataSource): List<DatabaseMetadata> {
        if (!isSupported(source)) {
            throw IllegalArgumentException("Given source of type ${source.source} but this extractor supports " +
                    "only sources of type ${getSourceType()}")
        }
        return source.schemas.map { schemaExtractor.extract(it, source.connection) }
    }

    private fun isSupported(source: MetadataSource): Boolean {
        return StringUtils.equalsIgnoreCase(getSourceType(), source.source)
    }

    override fun getSourceType(): String {
        return "ORACLE"
    }
}