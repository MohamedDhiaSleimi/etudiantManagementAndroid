package com.isetr.menufragapp.data

import androidx.room.*

/**
 * Modèle de données pour un étudiant.
 * Utilise 'data class' pour obtenir automatiquement des méthodes utiles comme equals() et copy().
 */
@Entity(tableName = "etudiant_table")
data class Etudiant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mail: String,
    val classe: String
)