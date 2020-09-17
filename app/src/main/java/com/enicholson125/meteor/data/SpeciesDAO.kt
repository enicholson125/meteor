package com.enicholson125.meteor.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * The Data Access Object for the [Species] class.
 */
@Dao
interface SpeciesDAO {
    @Query("SELECT * FROM animal_types WHERE id = :id")
    fun getTypeByID(id: String): LiveData<Species>
}
