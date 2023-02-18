package com.bignerdranch.android.criminalintentrecap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bignerdranch.android.criminalintentrecap.databinding.ListItemCrimeBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CrimeHolder(private val binding: ListItemCrimeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val dateFormat = SimpleDateFormat("EEEE-dd-MMMM-yyyy", Locale.UK)
    fun bind(crime: Crime, onCrimeClicked: (id: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = dateFormat.format(crime.date)

        binding.root.setOnClickListener {
            onCrimeClicked(crime.id)
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


}

class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onCrimeClicked: (id: UUID) -> Unit
) :
    RecyclerView.Adapter<CrimeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        return CrimeHolder(binding)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.bind(crime, onCrimeClicked)
    }

    override fun getItemCount(): Int {
        return crimes.size
    }
}