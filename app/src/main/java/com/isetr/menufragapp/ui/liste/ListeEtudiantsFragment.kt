package com.isetr.menufragapp.ui.liste

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.isetr.menufragapp.R
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.FragmentListeEtudiantsBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

@Suppress("DEPRECATION")
class ListeEtudiantsFragment : Fragment() {
    private var _binding: FragmentListeEtudiantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var etudiantAdapter: EtudiantAdapter
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListeEtudiantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillRecyclerView()

        // Observation de l'état de la liste dans le ViewModel
        etudiantViewModel.allEtudiants.observe(viewLifecycleOwner) { nouvelleListe ->
            etudiantAdapter.updateList(nouvelleListe)
        }

        // Observation des résultats de recherche
        etudiantViewModel.searchResults.observe(viewLifecycleOwner) { searchList ->
            etudiantAdapter.updateList(searchList)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                etudiantViewModel.setSearchQuery(newText ?: "")
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun fillRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewEtudiants
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        etudiantAdapter = EtudiantAdapter(
            etudiants = mutableListOf(),
            onItemClicked = { etudiant ->
                // Navigation vers le fragment détail
                etudiantViewModel.selectionnerEtudiant(etudiant)
                findNavController().navigate(R.id.action_nav_liste_etudiants_to_nav_detail_etudiant)
            },
            onItemLongClicked = { etudiant ->
                // Suppression avec confirmation
                showDeleteConfirmationDialog(etudiant)
            },
            onItemUpdate = { etudiant ->
                // Mise à jour avec dialogue
                showUpdateDialog(etudiant)
            }
        )
        recyclerView.adapter = etudiantAdapter
    }

    private fun showDeleteConfirmationDialog(etudiant: Etudiant) {
        AlertDialog.Builder(requireContext())
            .setTitle("Supprimer l'étudiant")
            .setMessage("Voulez-vous vraiment supprimer ${etudiant.email} ?")
            .setPositiveButton("Oui") { _, _ ->
                etudiantViewModel.deleteEtudiant(etudiant)
                Toast.makeText(requireContext(), "Étudiant supprimé", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Non", null)
            .show()
    }

    private fun showUpdateDialog(etudiant: Etudiant) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_details, null)

        val editId = dialogView.findViewById<TextInputEditText>(R.id.edit_text_edit_id)
        val editMail = dialogView.findViewById<TextInputEditText>(R.id.edit_text_edit_mail)
        val editClasse = dialogView.findViewById<TextInputEditText>(R.id.edit_text_edit_classe)

        // Pré-remplir les champs
        editId.setText(etudiant.identifiant)
        editMail.setText(etudiant.email)
        editClasse.setText(etudiant.classe)

        AlertDialog.Builder(requireContext())
            .setTitle("Modifier l'étudiant")
            .setView(dialogView)
            .setPositiveButton("Enregistrer") { _, _ ->
                val newId = editId.text.toString().trim()
                val newMail = editMail.text.toString().trim()
                val newClasse = editClasse.text.toString().trim()

                if (newMail.isNotBlank() && newClasse.isNotBlank()) {
                    val updatedEtudiant = etudiant.copy(
                        identifiant = newId,
                        email = newMail,
                        classe = newClasse
                    )
                    etudiantViewModel.updateEtudiant(updatedEtudiant)
                    Toast.makeText(requireContext(), "Étudiant mis à jour", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}