package ru.mydesignstudio.database.metadata.extractor.registry

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.destination.OutputDestination
import java.lang.RuntimeException
import javax.annotation.PostConstruct

@Component
class DestinationRegistryImpl constructor(
        private val context: ApplicationContext
) : DestinationRegistry {
    private val registry: MutableMap<String, OutputDestination> = HashMap()

    @PostConstruct
    fun init() {
        val beans = context.getBeansOfType(OutputDestination::class.java)
        for (entry in beans.entries) {
            register(entry.value)
        }
    }

    override fun register(destination: OutputDestination) {
        if (hasDestination(destination.getDestinationType())) {
            throw RuntimeException("Destination for type ${destination.getDestinationType()} has already " +
                    "been registered")
        }
        registry[destination.getDestinationType()] = destination
    }

    override fun hasDestination(destination: String): Boolean {
        return registry.containsKey(destination)
    }

    override fun getDestination(destination: String): OutputDestination {
        if (!hasDestination(destination)) {
            throw RuntimeException("There is no destination for type ${destination}")
        }
        return registry[destination]!!
    }
}