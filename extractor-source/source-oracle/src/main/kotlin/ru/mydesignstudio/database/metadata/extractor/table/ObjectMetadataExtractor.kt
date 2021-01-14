package ru.mydesignstudio.database.metadata.extractor.table

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataObject
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata
import ru.mydesignstudio.database.metadata.extractor.table.extractor.*
import java.util.stream.Collectors

@Component
class ObjectMetadataExtractor (
        private val columnsExtractor: ColumnsExtractor,
        private val primaryKeyExtractor: PrimaryKeyExtractor,
        private val foreignKeyExtractor: ForeignKeyExtractor,
        private val constraintExtractor: ConstraintExtractor,
        private val referencesExtractor: ReferenceExtractor,
        private val checksExtractor: ChecksExtractor,
        private val triggerExtractor: TriggerExtractor,
        private val udfExtractor: UdfExtractor,
        private val viewsUsedExtractor: ViewUsedExtractor,
        private val viewsReferencedExtractor: ViewReferencedExtractor,
        private val typesExtractor: TypeExtractor,
        private val uniqueKeyExtractor: UniqueKeyExtractor,
        private val scriptsExtractor: ScriptExtractor
        ) {
    fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): Collection<TableMetadata> {
        return schema.objects.stream()
                .map { extract(it, schema, connection) }
                .collect(Collectors.toList())
    }

    private fun extract(metadata: MetadataObject, schema: MetadataSchema, connection: MetadataSourceConnection): TableMetadata {
        return TableMetadata(
                tableName = metadata.name,
                schemaName = schema.name,
                columns = columnsExtractor.extract(metadata, schema, connection),
                primaryKeys = primaryKeyExtractor.extract(metadata, schema, connection),
                foreignKeys = foreignKeyExtractor.extract(metadata, schema, connection),
                constraints = constraintExtractor.extract(metadata, schema, connection),
                references = referencesExtractor.extract(metadata, schema, connection),
                checks = checksExtractor.extract(metadata, schema, connection),
                triggers = triggerExtractor.extract(metadata, schema, connection),
                udfs = udfExtractor.extract(metadata, schema, connection),
                viewsUsed = viewsUsedExtractor.extract(metadata, schema, connection),
                viewsReferenced = viewsReferencedExtractor.extract(metadata, schema, connection),
                types = typesExtractor.extract(metadata, schema, connection),
                uniqueKeys = uniqueKeyExtractor.extract(metadata, schema, connection),
                scripts = scriptsExtractor.extract(metadata, schema, connection)
        )
    }
}