package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.grupo15.vinilos_app.databinding.CollectorFragmentBinding
import com.appsmoviles.grupo15.vinilos_app.ui.adapters.CollectorAdapter
import com.appsmoviles.grupo15.vinilos_app.viewmodels.CollectorViewModel

class CollectorFragment : Fragment() {

    private val collectorViewModel: CollectorViewModel by viewModels()
    private lateinit var binding: CollectorFragmentBinding
    private lateinit var adapter: CollectorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CollectorFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CollectorAdapter(listOf()){}
        binding.collectorsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.collectorsRecyclerView.adapter = adapter

        collectorViewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            adapter.updateCollectors(collectors)
        }

        collectorViewModel.fetchCollectors()
    }
}
