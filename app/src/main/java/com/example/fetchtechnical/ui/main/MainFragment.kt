package com.example.fetchtechnical.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchtechnical.ItemListAdapter
import com.example.fetchtechnical.databinding.MainFragmentBinding
import com.example.fetchtechnical.models.ItemModel
import com.example.fetchtechnical.repository.Repository

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMainViewModel()
        viewModel.getFetchHiringData()

        viewModel.formatHiringData().observe(viewLifecycleOwner) { massagedItemsList ->
            initRecyclerView(massagedItemsList)
        }
    }

    private fun initRecyclerView(massagedItemsList: List<ItemModel>) {
        val recyclerAdapter = ItemListAdapter(resources)

        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        recyclerAdapter.submitList(massagedItemsList)

    }

    private fun initMainViewModel() {
        val factory = activity?.let { MainViewModelFactory(repository, it.application, resources) }
        viewModel = factory?.let { ViewModelProvider(this, it) }?.get(MainViewModel::class.java) as MainViewModel
    }
}