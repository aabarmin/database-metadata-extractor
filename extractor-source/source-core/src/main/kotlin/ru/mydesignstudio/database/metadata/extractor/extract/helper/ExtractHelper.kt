package ru.mydesignstudio.database.metadata.extractor.extract.helper

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*

class ExtractHelper (private val jdbcTemplate: JdbcTemplate) {
    fun <T> extract(query: String, params: List<String>, targetClass: Class<T>): Collection<T> {
        var queryParams = params.toTypedArray();
        val extractedItems = jdbcTemplate.query(query, queryParams, BeanPropertyRowMapper(targetClass))
        return HashSet(extractedItems)
    }
}