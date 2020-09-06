package com.enicholson125.meteor.data

import androidx.room.TypeConverter

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter fun snippetTypeToString(snippetType: SnippetType): String =
        snippetType.toString()
    @TypeConverter fun stringToSnippetType(value: String): SnippetType =
        SnippetType.valueOf(value)

    @TypeConverter fun colonSeparatedStringToList(value: String): List<String> =
        value.split(":").map { it.trim() }
    @TypeConverter fun listToCommaSeparatedString(value: List<String>): String =
        value.joinToString(separator = ":")
}
