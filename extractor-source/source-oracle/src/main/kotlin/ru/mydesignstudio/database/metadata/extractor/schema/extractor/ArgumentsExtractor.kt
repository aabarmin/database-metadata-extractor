package ru.mydesignstudio.database.metadata.extractor.schema.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ArgumentModel

@Component
class ArgumentsExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider
) : ExtractorBase() {
    fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ArgumentModel> {
        val query = queryProvider.provide("classpath:sql/extract_procedure_arguments.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(schema.name)) { rs ->
            ArgumentModel(
                    schemaName = getString(rs, "schema_name"),
                    procedureName = getString(rs, "procedure_name"),
                    argumentName = getString(rs, "argument_name"),
                    inOut = getString(rs, "in_out"),
                    dataType = getString(rs, "data_type"),
                    dataLength = getInt(rs, "data_length"),
                    dataPrecision = getInt(rs, "data_precision"),
                    dataScale = rs.getInt("data_scale"),
                    defaulted = getString(rs, "defaulted"),
                    defaultValue = getString(rs, "default_value")
            )
        }
    }
}