package ru.mydesignstudio.database.metadata.extractor.connection

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource

@Component
class JdbcTemplateProviderImpl : JdbcTemplateProvider {
    private val cache = ConcurrentHashMap<MetadataSourceConnection, JdbcTemplate>()

    override fun provide(connection: MetadataSourceConnection): JdbcTemplate {
        return cache.computeIfAbsent(connection) { params ->
            createJdbcTemplate(createDataSource(params))
        }
    }

    fun createDataSource(connection: MetadataSourceConnection): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = connection.url
        config.username = connection.username
        config.password = connection.password
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        return HikariDataSource(config)
    }

    fun createJdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}