package com.bignerdranch.android.criminalintentrecap

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintentrecap.databinding.FragmentCirmeListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class CrimeListFragment : Fragment() {

    private var _binding: FragmentCirmeListBinding? = null
    private val binding
        get() = checkNotNull(_binding)
        {
            "no binding instance available"
        }
    private val crimeLitViewModel: CrimeLitViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCirmeListBinding.inflate(layoutInflater, container, false)
        binding.crimeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                crimeLitViewModel.crimes.collect { crimes ->
                    if (crimes.isEmpty()) {
                        binding.crimeRecyclerView.visibility = View.GONE
                        binding.crimeListEmptyWarning.visibility = View.VISIBLE
                        binding.crimeListEmptyWarning.setOnClickListener {
                            showCrime()
                        }
                    } else {
                        binding.crimeListEmptyWarning.visibility = View.GONE
                        binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes) { crimeId ->
                            findNavController()
                                .navigate(CrimeListFragmentDirections.showCrimeDetail(crimeId))
                        }
                    }

                }
            }
        }
        addMenu()


    }

    private fun addMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_crime_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_crime -> {
                        showCrime()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.STARTED)


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun showCrime() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newCrime = Crime(
                id = UUID.randomUUID(),
                title = "",
                date = Date(),
                isSolved = false
            )
            crimeLitViewModel.addCrime(newCrime)
            findNavController().navigate(CrimeListFragmentDirections.showCrimeDetail(newCrime.id))
        }

    }

}