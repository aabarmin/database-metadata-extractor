package ru.mydesignstudio.database.metadata.extractor.extract.parameters.reader

import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters

interface ParametersReader {
    fun read(): ExtractParameters
}