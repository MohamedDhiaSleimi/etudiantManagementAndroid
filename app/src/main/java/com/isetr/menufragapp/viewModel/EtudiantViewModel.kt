package com.isetr.menufragapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun selectionnerEtudiant(etudiant: Etudiant) {
        _selectedEtudiant.value = etudiant
    }

    fun insertEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.insert(etudiant)
    }

    fun deleteEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.delete(etudiant)
    }
}
