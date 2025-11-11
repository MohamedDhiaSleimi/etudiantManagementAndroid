package com.isetr.menufragapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.isetr.menufragapp.Repository.EtudiantRepository
import com.isetr.menufragapp.data.Etudiant
import kotlinx.coroutines.launch

class EtudiantViewModel(
    private val repository: EtudiantRepository
) : ViewModel() {

    val allEtudiants: LiveData<List<Etudiant>> = repository.allEtudiants

    // Étudiant sélectionné (Mutable interne, LiveData publique)
    private val _selectedEtudiant = MutableLiveData<Etudiant?>()
    val selectedEtudiant: LiveData<Etudiant?> get() = _selectedEtudiant

    // Pour la recherche
    private val _searchQuery = MutableLiveData<String>()
    val searchResults: LiveData<List<Etudiant>> = _searchQuery.switchMap { query ->
        if (query.isNullOrBlank()) {
            repository.allEtudiants
        } else {
            repository.searchByClasse(query)
        }
    }

    fun selectionnerEtudiant(etudiant: Etudiant) {
        _selectedEtudiant.value = etudiant
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun insertEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.insert(etudiant)
    }

    fun updateEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.update(etudiant)
    }

    fun deleteEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.delete(etudiant)
    }
}