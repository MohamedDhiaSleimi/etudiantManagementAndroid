package com.isetr.menufragapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.menufragapp.databinding.FragmentHomeBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = binding.textHome
        textView.text = "Bienvenue Home Fragment"

        // If you want to observe LiveData from your ViewModel, uncomment and adapt this:
        // etudiantViewModel.allEtudiants.observe(viewLifecycleOwner) { etudiants ->
        //     textView.text = "Nombre d'étudiants : ${etudiants.size}"
        // }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
