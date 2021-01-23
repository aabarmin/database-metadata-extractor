package ru.mydesignstudio.database.metadata.extractor.extract.result

data class TypeModel(val owner: String,
                     val objectName: String,
                     val objectType: String,
                     val comments: String) {

    companion object {
        @JvmStatic
        fun create(objectType: String): TypeModel {
            return TypeModel(
                    "",
                    "",
                    objectType,
                    ""
            )
        }
    }
}