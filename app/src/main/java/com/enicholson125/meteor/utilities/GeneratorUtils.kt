package com.enicholson125.meteor.utilities

import kotlin.random.Random

val namesList = listOf<String>(
    "Persephone",
    "Penelope",
    "Geoffrey",
    "Caligula",
    "Athena",
    "Brand",
    "Bethany",
    "Prometheus",
    "Percival",
    "Igraine",
    "Bedivere",
    "Rhyndon",
    "Orlith",
    "Theseus",
    "Ignatius",
)

val idCharPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

object GeneratorUtils {
    fun getRandomName(): String {
        return namesList.get(Random.nextInt(0, namesList.size))
    }

    fun getRandomID(length: Int): String {
        return (1..length)
            .map { i -> Random.nextInt(0, idCharPool.size) }
            .map(idCharPool::get)
            .joinToString("")
    }
}
