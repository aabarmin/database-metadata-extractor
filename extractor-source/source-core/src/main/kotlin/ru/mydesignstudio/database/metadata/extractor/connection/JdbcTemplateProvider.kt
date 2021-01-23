package ru.mydesignstudio.database.metadata.extractor.connection

import org.springframework.jdbc.core.JdbcTemplate
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection

interface JdbcTemplateProvider {
    fun provide(connection: MetadataSourceConnection): JdbcTemplate
}