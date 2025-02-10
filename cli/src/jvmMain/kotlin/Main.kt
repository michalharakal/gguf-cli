@file:JvmName("Main")

import kotlinx.io.asSource
import kotlinx.io.buffered
import sk.ai.net.tools.cli.gguf.summary
import java.io.File
import kotlin.system.exitProcess


fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Usage: java -jar gguf-cli.jar <GGUF file>\n")
        exitProcess(1)
    }

    val filePath = args[0]
    val file = File(filePath)

    if (!file.exists()) {
        println("Error: The file at '$filePath' does not exist.")
        exitProcess(1)
    }

    // Open the file as an InputStream and process it
    try {
        file.inputStream().use { inputStream ->
            summary(inputStream.asSource().buffered())

        }
    } catch (e: Exception) {
        println("Error reading file stream: ${e.message}")
        exitProcess(1)
    }
}
