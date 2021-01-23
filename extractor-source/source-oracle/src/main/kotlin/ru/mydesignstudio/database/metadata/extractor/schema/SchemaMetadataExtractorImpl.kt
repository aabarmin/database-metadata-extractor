package ru.mydesignstudio.database.metadata.extractor.schema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.schema.extractor.ArgumentsExtractor
import ru.mydesignstudio.database.metadata.extractor.schema.extractor.JobsExtractor
import ru.mydesignstudio.database.metadata.extractor.schema.extractor.ProceduresExtractor
import ru.mydesignstudio.database.metadata.extractor.table.ObjectMetadataExtractor

@Component
class SchemaMetadataExtractorImpl @Autowired constructor(
        private val proceduresExtractor: ProceduresExtractor,
        private val argumentsExtractor: ArgumentsExtractor,
        private val jobsExtractor: JobsExtractor,
        private val objectMetadataExtractor: ObjectMetadataExtractor
) : SchemaMetadataExtractor {
    override fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): DatabaseMetadata {
        return DatabaseMetadata(
                schemaName = schema.name,
                procedures = proceduresExtractor.extract(schema, connection),
                arguments = argumentsExtractor.extract(schema, connection),
                jobs = jobsExtractor.extract(schema, connection),
                tables = objectMetadataExtractor.extract(schema, connection)
        )
    }
}