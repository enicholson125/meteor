gpackage com.google.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "adopted_animals",
    foreignKeys = [
        ForeignKey(entity = AnimalType::class, parentColumns = ["id"], childColumns = ["animal_id"])
    ],
)
data class AdoptedAnimal(
    @ColumnInfo(name = "animal_id") val animalTypeID: String,

    @ColumnInfo(name = "animal_name") val animalName: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var adoptedID: Long = 0
}
