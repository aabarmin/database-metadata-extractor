package ru.mydesignstudio.database.metadata.extractor.extract.result

import org.apache.commons.lang.StringUtils

class ColumnModel(
        val schemaName: String = "",
        val tableName: String = "",
        val columnName: String = "",
        val columnId: Int = 0,
        val dataType: String = "",
        val dataTypeExt: String = "",
        val dataLength: Int = 0,
        val dataPrecision: Int = 0,
        val dataScale: Int = 0,
        var nullable: String = "",
        val defaultDefinition: String = "",
        val primaryKey: String = "",
        val foreign_key: String = "",
        val uniqueKey: String = "",
        val checkConstraint: String = "",
        val comments: String = ""
) {
    // TODO, looks incorrect, needs to be checked
    fun isNullable(): Boolean {
        return StringUtils.equalsIgnoreCase("Y", nullable)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ColumnModel

        if (columnId != other.columnId) return false

        return true
    }

    override fun hashCode(): Int {
        return columnId
    }


}