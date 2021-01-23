package ru.mydesignstudio.database.metadata.extractor.extract.result

data class TriggerModel(
        val triggerSchemaName: String,
        val triggerName: String,
        val triggerType: String,
        val triggeringEvent: String,
        val schemaName: String,
        val objectName: String,
        val objectType: String,
        val status: String,
        val script: String)