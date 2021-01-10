package ru.mydesignstudio.database.metadata.extractor.extract.result

class TriggerModel(
        var triggerSchemaName: String,
        var triggerName: String,
        var triggerType: String,
        var triggeringEvent: String,
        var schemaName: String,
        var objectName: String,
        var objectType: String,
        var status: String,
        var script: String)