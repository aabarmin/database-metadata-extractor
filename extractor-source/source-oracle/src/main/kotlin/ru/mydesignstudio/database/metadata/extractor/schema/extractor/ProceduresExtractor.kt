package ru.mydesignstudio.database.metadata.extractor.schema.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ProcedureModel

@Component
class ProceduresExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider
) : ExtractorBase() {
    fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ProcedureModel> {
        val query = queryProvider.provide("classpath:sql/extract_procedures.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(schema.name)) { rs ->
            ProcedureModel(
                    schemaName = getString(rs, "schema_name"),
                    procedureName = getString(rs, "procedure_name"),
                    arguments = getString(rs, "arguments")
            )
        }
    }
}