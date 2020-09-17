package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "adopted_animals",
    foreignKeys = [
        ForeignKey(entity = Species::class, parentColumns = ["id"], childColumns = ["animal_id"])
    ],
    indices = [Index("animal_id")],
)
data class AdoptedAnimal(
    @ColumnInfo(name = "animal_id") val speciesID: String,

    @ColumnInfo(name = "animal_name") val animalName: String?,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var adoptedID: Long = 0
}
