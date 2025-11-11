package com.isetr.menufragapp.ui.liste

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.ItemEtudiantBinding

class EtudiantAdapter(
    private val etudiants: MutableList<Etudiant>,
    private val onItemClicked: (Etudiant) -> Unit,
    private val onItemLongClicked: (Etudiant) -> Unit,
    private val onItemUpdate: (Etudiant) -> Unit
) : RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val binding = ItemEtudiantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EtudiantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val etudiant = etudiants[position]
        holder.bind(etudiant)

        // Click simple pour voir les détails
        holder.itemView.setOnClickListener {
            onItemClicked(etudiant)
        }

        // Click long pour supprimer
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(etudiant)
            true
        }

        // Click sur le bouton de mise à jour
        holder.binding.buttonUpdate.setOnClickListener {
            onItemUpdate(etudiant)
        }
    }

    override fun getItemCount() = etudiants.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Etudiant>) {
        this.etudiants.clear()
        this.etudiants.addAll(newList)
        notifyDataSetChanged()
    }

    inner class EtudiantViewHolder(val binding: ItemEtudiantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(etudiant: Etudiant) {
            binding.textViewMail.text = " ${etudiant.email}"
            binding.textViewClasse.text = "Classe : ${etudiant.classe}"
        }
    }
}