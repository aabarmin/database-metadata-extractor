package ru.mydesignstudio.database.metadata.extractor.connection

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import javax.sql.DataSource

interface DataSourceProvider {
    fun provide(connection: MetadataSourceConnection): DataSource;
}