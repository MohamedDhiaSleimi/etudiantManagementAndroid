package com.isetr.menufragapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Etudiant::class], version = 1, exportSchema = false)
abstract class EtudiantDatabase : RoomDatabase() {
    abstract fun etudiantDao() : EtudiantDao
    companion object{
        @Volatile
        private var INSTANCE: EtudiantDatabase? = null
        fun getDatabase(context: Context): EtudiantDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EtudiantDatabase::class.java,
                    "etudiant_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}