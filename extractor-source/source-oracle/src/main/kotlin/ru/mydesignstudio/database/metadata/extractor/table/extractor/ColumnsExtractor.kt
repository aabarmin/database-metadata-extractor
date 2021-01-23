package ru.mydesignstudio.database.metadata.extractor.table.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ColumnModel

@Component
class ColumnsExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider) : ExtractorBase() {
    fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ColumnModel> {
        val query = queryProvider.provide("classpath:sql/extract_columns.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(metadata.name, schema.name)) { rs ->
            ColumnModel(
                    schemaName = getString(rs, "schema_name"),
                    tableName = getString(rs, "table_name"),
                    columnName = getString(rs, "column_name"),
                    columnId = getInt(rs, "column_id"),
                    dataType = getString(rs, "data_type"),
                    dataTypeExt = getString(rs, "data_type_ext"),
                    dataLength = getInt(rs, "data_length"),
                    dataPrecision = getInt(rs, "data_precision"),
                    dataScale = getInt(rs, "data_scale"),
                    nullable = getString(rs, "nullable"),
                    defaultDefinition = getString(rs, "default_value"),
                    primaryKey = getString(rs, "primary_key"),
                    foreignKey = getString(rs, "foreign_key"),
                    uniqueKey = getString(rs, "unique_key"),
                    checkConstraint = getString(rs, "check_constraint"),
                    comments = getString(rs, "comments")
            )
        }
    }
}