package com.isetr.menufragapp.ui.ajout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.FragmentAjoutEtudiantBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class AjoutEtudiantFragment : Fragment() {

    private var _binding: FragmentAjoutEtudiantBinding? = null
    private val binding get() = _binding!!

    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjoutEtudiantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.boutonAjouter.setOnClickListener {
            ajouterEtudiant()
        }
    }

    private fun ajouterEtudiant() {
        val email = binding.editTextMail.text.toString().trim()
        val classe = binding.editTextClasse.text.toString().trim()
        val identifiant = binding.editTextId.text.toString().trim()

        if (email.isNotBlank() && classe.isNotBlank()) {
            val nouvelEtudiant = Etudiant(
                id = 0, // auto-généré par Room
                identifiant = identifiant, email = email, classe = classe
            )

            etudiantViewModel.insertEtudiant(nouvelEtudiant)
            Toast.makeText(requireContext(), "Étudiant ajouté", Toast.LENGTH_SHORT).show()

            binding.editTextClasse.text?.clear()
            binding.editTextMail.text?.clear()
            binding.editTextId.text?.clear()
            binding.inputLayoutId.error = null
        } else {
            Toast.makeText(
                requireContext(), "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
