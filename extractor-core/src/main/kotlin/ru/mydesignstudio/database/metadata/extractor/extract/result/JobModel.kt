package ru.mydesignstudio.database.metadata.extractor.extract.result

data class JobModel(
        var schemaName: String,
        var jobName: String,
        var jobStyle: String,
        var jobType: String,
        var jobAction: String,
        var startDate: String,
        var schedule: String,
        var lastStartDate: String,
        var nextRunDate: String,
        var state: String
)