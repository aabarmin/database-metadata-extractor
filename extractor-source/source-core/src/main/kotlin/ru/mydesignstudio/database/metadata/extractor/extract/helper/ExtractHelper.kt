package ru.mydesignstudio.database.metadata.extractor.extract.helper

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.util.*

@Deprecated("Useless class, it's getter get rid of JdbcTemplate and use PreparedStatement instead")
class ExtractHelper (private val jdbcTemplate: JdbcTemplate) {

    fun <T> extract(query: String, params: List<String>, targetClass: Class<T>): Collection<T> {
        var queryParams = params.toTypedArray();
        val extractedItems = jdbcTemplate.query(query, queryParams, BeanPropertyRowMapper(targetClass))
        return HashSet(extractedItems)
    }

    fun <T> extract(query: String, params: List<String>, mapper: RowMapper<T>): Collection<T> {
        val queryParams = params.toTypedArray()
        val extractedItems = jdbcTemplate.query(query, mapper, queryParams)
        return extractedItems
    }
}