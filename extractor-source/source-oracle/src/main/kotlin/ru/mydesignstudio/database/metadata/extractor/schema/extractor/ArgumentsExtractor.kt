package ru.mydesignstudio.database.metadata.extractor.schema.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.helper.ExtractHelperProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.ArgumentModel
import ru.mydesignstudio.database.metadata.extractor.extract.result.ProcedureModel

@Component
class ArgumentsExtractor (
        private val helperProvider: ExtractHelperProvider,
        private val queryProvider: QueryProvider
) {
    fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): Collection<ArgumentModel> {
        val query = queryProvider.provide("classpath:sql/extract_procedure_arguments.sql")
        val helper = helperProvider.provide(connection)
        return helper.extract(query, listOf(schema.name), ArgumentModel::class.java)
    }
}