package ru.mydesignstudio.database.metadata.extractor.extract.helper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.connection.JdbcTemplateProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection

@Component
class ExtractHelperProviderImpl @Autowired constructor(
        private val templateProvider: JdbcTemplateProvider
) : ExtractHelperProvider {

    override fun provide(connection: MetadataSourceConnection): ExtractHelper {
        return ExtractHelper(templateProvider.provide(connection))
    }
}