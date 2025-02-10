import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import sk.ai.net.tools.cli.gguf.summary
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Usage: cli gguf-cli.jar <GGUF file>\n")
        exitProcess(1)
    }

    val filePath = args[0]
    val file = Path(filePath)

    try {
        summary(SystemFileSystem.source(file).buffered())
    } catch (e: Exception) {
        println("Error reading file stream: ${e.message}")
        exitProcess(1)
    }
}