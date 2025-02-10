package sk.ai.net.tools.cli.gguf

import kotlinx.io.Source
import sk.ai.net.gguf.GGUFReader
import sk.ai.net.gguf.GGUFValueType
import sk.ai.net.nn.reflection.table.table
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

fun summary(source: Source) {
    val reader = GGUFReader(source)
    // List all key-value pairs in a columned format
    println("Key-Value Pairs:")
    val maxKeyLength = reader.fields.keys.maxOf { it.length }
    for ((key, field) in reader.fields) {
        val value = if (field.types[0] == GGUFValueType.STRING && field.types.size == 1) {
            (field.parts[field.data[0]] as List<UByte>).toUByteArray().toByteArray().decodeToString()
        } else {
            field.parts[field.data[0]]
        }

        println("${key.padEnd(maxKeyLength)} : $value")
    }
    println("----")

    // List all tensors
    println("Tensors:")

    val tableStr = table {
        cellStyle {
            border = true
        }
        header {
            row {
                cell("Name")
                cell("Shape")
                cell("Size")
                cell("Quantization")
            }
        }
        for (tensor in reader.tensors) {
            val shapeStr = tensor.shape.joinToString("x")
            val sizeStr = tensor.nElements.toString()
            val quantizationStr = tensor.tensorType.name
            row {
                cell(tensor.name)
                cell(shapeStr)
                cell(sizeStr)
                cell(quantizationStr)
            }
        }
    }.toString()
    println(tableStr)
}
