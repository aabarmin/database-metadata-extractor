package ru.mydesignstudio.database.metadata.extractor.schema.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.ExtractorBase
import ru.mydesignstudio.database.metadata.extractor.connection.DataSourceProvider
import ru.mydesignstudio.database.metadata.extractor.extract.helper.QueryProvider
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.extract.result.JobModel

@Component
class JobsExtractor (
        private val dataSourceProvider: DataSourceProvider,
        private val queryProvider: QueryProvider
) : ExtractorBase() {
    fun extract(schema: MetadataSchema, connection: MetadataSourceConnection): Collection<JobModel> {
        val query = queryProvider.provide("classpath:sql/extract_jobs.sql")
        val dataSource = dataSourceProvider.provide(connection)
        return extract(dataSource, query, listOf(schema.name)) { rs ->
            JobModel(
                    schemaName = getString(rs, "schema_name"),
                    jobName = getString(rs, "job_name"),
                    jobStyle = getString(rs, "job_style"),
                    jobType = getString(rs, "job_type"),
                    jobAction = getString(rs, "job_action"),
                    startDate = getString(rs, "start_date"),
                    schedule = getString(rs, "schedule"),
                    lastStartDate = getString(rs, "last_start_date"),
                    nextRunDate = getString(rs, "next_run_date"),
                    state = getString(rs, "state")
            )
        }
    }
}