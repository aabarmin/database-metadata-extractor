package ru.mydesignstudio.database.metadata.extractor.table.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.UniqueKeyModel

@Component
class UniqueKeyExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider) : ExtractorBase() {
    fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): Collection<UniqueKeyModel> {
        val query = queryProvider.provide("classpath:sql/extract_unique_keys.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(metadata.name, schema.name)) { rs ->
            UniqueKeyModel(
                    owner = getString(rs, "owner"),
                    constraintName = getString(rs, "constraint_name"),
                    tableName = getString(rs, "table_name"),
                    columnName = getString(rs, "column_name"),
                    uniqueKey = getString(rs, "unique_key")
            )
        }
    }
}