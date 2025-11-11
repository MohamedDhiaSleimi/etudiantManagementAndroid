package com.isetr.menufragapp.Repository

import androidx.lifecycle.LiveData
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.data.EtudiantDao

class EtudiantRepository(
    private val etudiantDao: EtudiantDao
) {
    val allEtudiants: LiveData<List<Etudiant>> = etudiantDao.getAllEtudiants()

    suspend fun insert(etudiant: Etudiant) {
        etudiantDao.Insert(etudiant)
    }

    suspend fun update(etudiant: Etudiant) {
        etudiantDao.Update(etudiant)
    }

    suspend fun delete(etudiant: Etudiant) {
        etudiantDao.Delete(etudiant)
    }

    suspend fun deleteAll() {
        etudiantDao.deleteAll()
    }

    fun searchByClasse(classe: String): LiveData<List<Etudiant>> {
        return etudiantDao.searchByClasse(classe)
    }
}