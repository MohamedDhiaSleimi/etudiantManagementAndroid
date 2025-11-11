package com.isetr.menufragapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EtudiantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(etudiant: Etudiant)

    @Update
    suspend fun update(etudiant: Etudiant)

    @Delete
    suspend fun delete(etudiant: Etudiant)

    @Query("SELECT * FROM etudiant_table ORDER BY identifiant ASC")
    fun getAllEtudiants(): LiveData<List<Etudiant>>

    @Query("SELECT * FROM etudiant_table WHERE classe LIKE '%' || :classe || '%' ORDER BY identifiant ASC")
    fun searchByClasse(classe: String): LiveData<List<Etudiant>>

    @Query("DELETE FROM etudiant_table")
    suspend fun deleteAll()
}