package ru.mydesignstudio.database.metadata.extractor.extract.result

class DatabaseMetadata(
        val schemaName: String,
        val procedures: Collection<ProcedureModel>,
        val arguments: Collection<ArgumentModel>,
        val tables: Collection<TableMetadata>,
        val jobs: Collection<JobModel>)