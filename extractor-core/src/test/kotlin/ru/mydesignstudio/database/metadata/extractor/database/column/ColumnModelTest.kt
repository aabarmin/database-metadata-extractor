package ru.mydesignstudio.database.metadata.extractor.database.column

import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.math.RandomUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import ru.mydesignstudio.database.metadata.extractor.extract.result.ColumnModel
import java.util.*

internal class ColumnModelTest {
    @Test
    fun columnModel_addUniqueByColumnId() {
        val models: MutableSet<ColumnModel> = HashSet()
        models.add(createColumnModel(1))
        models.add(createColumnModel(1))
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(models) },
                Executable { org.assertj.core.api.Assertions.assertThat(models).hasSize(1) }
        )
    }

    private fun createColumnModel(columnId: Int): ColumnModel {
        val model = ColumnModel(
                schemaName = RandomStringUtils.randomAlphabetic(10),
                tableName = RandomStringUtils.randomAlphabetic(10),
                columnName = RandomStringUtils.randomAlphabetic(10),
                columnId = columnId,
                dataType = RandomStringUtils.randomAlphabetic(10),
                dataTypeExt = RandomStringUtils.randomAlphabetic(10),
                dataLength = RandomUtils.nextInt(),
                dataPrecision = RandomUtils.nextInt(),
                dataScale = RandomUtils.nextInt(),
                nullable = RandomStringUtils.randomAlphabetic(1),
                defaultDefinition = RandomStringUtils.randomAlphabetic(10),
                primaryKey = RandomStringUtils.randomAlphabetic(10),
                foreignKey = RandomStringUtils.randomAlphabetic(10),
                uniqueKey = RandomStringUtils.randomAlphabetic(10),
                checkConstraint = RandomStringUtils.randomAlphabetic(10),
                comments = RandomStringUtils.random(10)
        )
        return model
    }
}