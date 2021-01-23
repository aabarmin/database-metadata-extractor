package ru.mydesignstudio.database.metadata.extractor

import java.sql.Connection
import java.sql.ResultSet
import javax.sql.DataSource

abstract class ExtractorBase {
    fun getString(rs: ResultSet, column: String): String {
        return rs.getString(column) ?: ""
    }

    fun getInt(rs: ResultSet, column: String): Int {
        return rs.getInt(column) ?: 0
    }

    fun <T> extract(dataSource: DataSource, query: String, params: List<*>, converter: (ResultSet) -> T): Collection<T> {
        dataSource.connection.use { connection ->
            return extract(connection, query, params, converter)
        }
    }

    fun <T> extract(connection: Connection, query: String, params: List<*>, converter: (ResultSet) -> T): Collection<T> {
        val result = ArrayList<T>()
        connection.prepareStatement(query).use { statement ->
            params.forEachIndexed { index, value ->
                if (value is String) {
                    statement.setString(index + 1, value)
                } else {
                    throw UnsupportedOperationException("Unsupported value type ")
                }
            }
            statement.executeQuery().use { rs ->
                while (rs.next()) {
                    result.add(converter.invoke(rs))
                }
            }
        }
        return result
    }
}