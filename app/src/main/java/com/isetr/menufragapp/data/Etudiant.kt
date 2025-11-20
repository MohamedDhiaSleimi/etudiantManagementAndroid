package com.isetr.menufragapp.data

import androidx.room.*

/**
 * Modèle de données pour un étudiant.
 * Utilise 'data class' pour obtenir automatiquement des méthodes utiles comme equals() et copy().
 */
@Entity(tableName = "etudiant_table")
data class Etudiant(
    @PrimaryKey()
    val cin: Int,
    val nom: String,
    val prenom: String,
    val mail: String,
    val password: String,
    val classe: String
)

