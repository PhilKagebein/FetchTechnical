package com.example.fetchtechnical.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fetchtechnical.databinding.MainFragmentBinding
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

        viewModel.hiringData.observe(viewLifecycleOwner) { itemList ->
            println(itemList)
        }
    }

    private fun initMainViewModel() {
        val factory = activity?.let { MainViewModelFactory(repository, it.application, resources) }
        viewModel = factory?.let { ViewModelProvider(this, it) }?.get(MainViewModel::class.java) as MainViewModel
    }
}