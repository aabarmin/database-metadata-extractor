package ru.mydesignstudio.database.metadata.extractor.table.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.TriggerModel

@Component
class TriggerExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider) : ExtractorBase() {
    fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): Collection<TriggerModel> {
        val query = queryProvider.provide("classpath:sql/extract_triggers.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(metadata.name, schema.name)) { rs ->
            TriggerModel(
                    triggerSchemaName = getString(rs, "trigger_schema_name"),
                    triggerName = getString(rs, "trigger_name"),
                    triggerType = getString(rs, "trigger_type"),
                    triggeringEvent = getString(rs, "triggering_event"),
                    schemaName = getString(rs, "schema_name"),
                    objectName = getString(rs, "object_name"),
                    objectType = getString(rs, "object_type"),
                    status = getString(rs, "status"),
                    script = getString(rs, "script")
            )
        }
    }
}