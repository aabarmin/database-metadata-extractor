package ru.mydesignstudio.database.metadata.extractor.extract.result

class ForeignKeyModel(
        val foreignKey: String = "",
        val constraintType: String = "",
        val schemaName: String = "",
        val constraintName: String = "",
        val tableName: String = "",
        val columns: String = "",
        val rOwner: String = "",
        val rConstraintName: String = ""
) {
}