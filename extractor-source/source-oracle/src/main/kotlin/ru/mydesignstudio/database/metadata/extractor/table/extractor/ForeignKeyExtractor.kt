package ru.mydesignstudio.database.metadata.extractor.table.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ForeignKeyModel

@Component
class ForeignKeyExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider) : ExtractorBase() {
    fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ForeignKeyModel> {
        val query = queryProvider.provide("classpath:sql/extract_foreign_keys.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(metadata.name, schema.name)) { rs ->
            ForeignKeyModel(
                    foreignKey = getString(rs, "foreign_key"),
                    constraintType = getString(rs, "constraint_type"),
                    schemaName = getString(rs, "schema_name"),
                    constraintName = getString(rs, "constraint_name"),
                    tableName = getString(rs, "table_name"),
                    columns = getString(rs, "columns"),
                    rOwner = getString(rs, "r_owner"),
                    rConstraintName = getString(rs, "r_constraint_name")
            )
        }
    }
}