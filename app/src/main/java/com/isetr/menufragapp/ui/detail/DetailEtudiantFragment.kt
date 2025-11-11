package com.isetr.menufragapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.menufragapp.databinding.FragmentDetailEtudiantBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class DetailEtudiantFragment : Fragment() {

    private var _binding: FragmentDetailEtudiantBinding? = null
    private val binding get() = _binding!!
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailEtudiantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observer l'étudiant sélectionné
        etudiantViewModel.selectedEtudiant.observe(viewLifecycleOwner) { etudiant ->
            etudiant?.let {
                // Afficher les détails de l'étudiant
                binding.textViewIdentifiant.text = "Identifiant : ${it.identifiant}"
                binding.textViewEmail.text = "Email : ${it.email}"
                binding.textViewClasse.text = "Classe : ${it.classe}"
                binding.textViewId.text = "ID : ${it.id}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}