package ru.mydesignstudio.database.metadata.extractor.connection

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource

@Component
class DataSourceProviderImpl : DataSourceProvider {
    private val cache = ConcurrentHashMap<MetadataSourceConnection, DataSource>()

    override fun provide(connection: MetadataSourceConnection): DataSource {
        return cache.computeIfAbsent(connection) { connection ->
            val config = HikariConfig()
            config.jdbcUrl = connection.url
            config.username = connection.username
            config.password = connection.password
            config.addDataSourceProperty("cachePrepStmts", "true")
            config.addDataSourceProperty("prepStmtCacheSize", "250")
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
            HikariDataSource(config)
        }
    }
}