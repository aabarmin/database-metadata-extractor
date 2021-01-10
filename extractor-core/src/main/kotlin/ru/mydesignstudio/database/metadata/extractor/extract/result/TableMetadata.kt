package ru.mydesignstudio.database.metadata.extractor.extract.result

class TableMetadata(
        var tableName: String,
        var schemaName: String,
        var columns: Collection<ColumnModel>,
        var primaryKeys: Collection<PrimaryKeyModel>,
        var foreignKeys: Collection<ForeignKeyModel>,
        var constraints: Collection<ConstraintModel>,
        var references: Collection<ReferenceModel>,
        var checks: Collection<CheckModel>,
        var triggers: Collection<TriggerModel>,
        var udfs: Collection<UdfModel>,
        var viewsUsed: Collection<ViewUsedModel>,
        var viewsReferenced: Collection<ViewReferencedModel>,
        var types: Collection<TypeModel>,
        var uniqueKeys: Collection<UniqueKeyModel>,
        var scripts: Collection<ScriptModel>)