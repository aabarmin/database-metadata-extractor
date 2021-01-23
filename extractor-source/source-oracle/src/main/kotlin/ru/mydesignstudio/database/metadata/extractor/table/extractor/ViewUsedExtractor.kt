package ru.mydesignstudio.database.metadata.extractor.table.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ViewUsedModel

@Component
class ViewUsedExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider) : ExtractorBase() {
    fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ViewUsedModel> {
        val query = queryProvider.provide("classpath:sql/extract_views_used.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(metadata.name, schema.name)) { rs ->
            ViewUsedModel(
                    referencingObject = getString(rs, "referencing_object"),
                    referencingType = getString(rs, "referencing_type"),
                    referencedObject = getString(rs, "referenced_object"),
                    referencedType = getString(rs, "referenced_type")
            )
        }
    }
}