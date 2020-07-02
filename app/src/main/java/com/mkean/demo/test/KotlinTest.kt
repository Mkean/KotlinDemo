package com.mkean.demo.test

fun main() {

    val test = KotlinTest(7.0,8.0)

    println("max of 0 and 42 is ${maxOf(0, 42)}")

    println("--------------------------\n")

    println(test.printProduct("6", "7"))
    println(test.printProduct("a", "7"))
    println(test.printProduct("a", "b"))

    println("--------------------------\n")

    println(test.describe(7.0))
    println(test.describe(1))
    println(test.describe("Hello"))
    println(test.describe(1000L))
    println(test.describe(1))
    println(test.describe("other"))

    println("--------------------------\n")

    val items = listOf<String>("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }

    println("--------------------------\n")

    for (i in 4 downTo 1) {
        println(i)
    }
}

class KotlinTest(var height: Double,
                 var width: Double) {
    fun describe(obj: Any): String =
            when (obj) {
                height -> "height"
                1 -> "One"
                "Hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "Not a String"
                else -> "Unknown"
            }

    fun maxOf(a: Int, b: Int) = if (a > b) a else b

    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)

        // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
        if (x != null && y != null) {
            // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
            println(x * y)
        } else {
            println("'$arg1' or '$arg2' is not a number")
        }
    }
}