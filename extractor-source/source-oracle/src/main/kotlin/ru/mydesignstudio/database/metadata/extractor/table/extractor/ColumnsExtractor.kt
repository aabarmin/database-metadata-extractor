package ru.mydesignstudio.database.metadata.extractor.table.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.helper.ExtractHelperProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ColumnModel

@Component
class ColumnsExtractor (
        private val helperProvider: ExtractHelperProvider,
        private val queryProvider: QueryProvider) {
    fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ColumnModel> {
        val query = queryProvider.provide("classpath:sql/extract_columns.sql")
        return helperProvider.provide(connection)
                .extract(query, listOf(metadata.name, schema.name), ColumnModel::class.java)
    }
}