package ru.mydesignstudio.database.metadata.extractor.parameters.reader

import ru.mydesignstudio.database.metadata.extractor.parameters.Parameters

interface ParametersReader {
    fun read(): Parameters
}