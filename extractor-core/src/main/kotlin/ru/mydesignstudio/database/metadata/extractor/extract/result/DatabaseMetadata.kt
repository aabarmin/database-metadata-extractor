package ru.mydesignstudio.database.metadata.extractor.extract.result

class DatabaseMetadata(
        var schemaName: String,
        var procedures: Collection<ProcedureModel>,
        var arguments: Collection<ArgumentModel>,
        var jobs: Collection<JobModel>)