package ru.mydesignstudio.database.metadata.extractor.extract.result

class TableMetadata(
        val tableName: String,
        val schemaName: String,
        val columns: Collection<ColumnModel>,
        val primaryKeys: Collection<PrimaryKeyModel>,
        val foreignKeys: Collection<ForeignKeyModel>,
        val constraints: Collection<ConstraintModel>,
        val references: Collection<ReferenceModel>,
        val checks: Collection<CheckModel>,
        val triggers: Collection<TriggerModel>,
        val udfs: Collection<UdfModel>,
        val viewsUsed: Collection<ViewUsedModel>,
        val viewsReferenced: Collection<ViewReferencedModel>,
        val types: Collection<TypeModel>,
        val uniqueKeys: Collection<UniqueKeyModel>,
        val scripts: Collection<ScriptModel>)