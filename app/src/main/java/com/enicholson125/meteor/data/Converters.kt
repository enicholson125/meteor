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
}
