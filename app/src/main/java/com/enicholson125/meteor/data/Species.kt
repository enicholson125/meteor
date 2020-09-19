package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "animal_types",
)
data class Species(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "animal_name") val name: String,

    @ColumnInfo(name = "animal_description") val description: String,

    @ColumnInfo(name = "animal_image") val image: String,
) {
}
