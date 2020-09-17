package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "animal_types",
)
data class Species(
    @PrimaryKey @ColumnInfo(name = "id") val animalID: String,

    @ColumnInfo(name = "animal_name") val animalName: String,

    @ColumnInfo(name = "animal_description") val animalDescription: String,

    @ColumnInfo(name = "animal_image") val animalImage: String,
) {
}
