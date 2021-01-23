package ru.mydesignstudio.database.metadata.extractor.extract.helper

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection

@Deprecated("Use DataSourceProvider instead")
interface ExtractHelperProvider {
    fun provide(connection: MetadataSourceConnection): ExtractHelper;
}