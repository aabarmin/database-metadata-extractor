package ru.mydesignstudio.database.metadata.extractor.output

import java.nio.file.Path
import java.util.*

data class Output(val title: String,
                  val filePath: Path,
                  val labels: Set<Label>)