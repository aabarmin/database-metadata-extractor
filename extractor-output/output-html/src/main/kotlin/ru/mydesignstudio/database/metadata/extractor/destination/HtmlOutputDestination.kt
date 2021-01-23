package ru.mydesignstudio.database.metadata.extractor.destination

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata

@Component
class HtmlOutputDestination : OutputDestination {
    override fun output(metadata: DatabaseMetadata) {
        TODO("Not yet implemented")
    }

    override fun getDestinationType(): String {
        return "HTML"
    }
}