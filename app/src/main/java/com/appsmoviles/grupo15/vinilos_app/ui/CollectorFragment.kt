package com.appsmoviles.grupo15.vinilos_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsmoviles.grupo15.vinilos_app.R
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

        setupRecyclerView()
        setupSwipeToRefresh()
        setupObservers()

        collectorViewModel.fetchCollectors()
    }

    private fun setupRecyclerView() {
        adapter = CollectorAdapter(listOf()) { collector ->
            val bundle = Bundle().apply { putInt("collectorId", collector.id) }
            findNavController().navigate(R.id.action_collectorFragment_to_collectorDetailFragment, bundle)
        }
        binding.collectorsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.collectorsRecyclerView.adapter = adapter
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            collectorViewModel.fetchCollectors()
        }
    }

    private fun setupObservers() {
        collectorViewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            adapter.updateCollectors(collectors)
            binding.swipeRefreshLayout.isRefreshing = false // Detén el SwipeRefreshLayout
        }

        collectorViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        collectorViewModel.networkErrorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                collectorViewModel.resetNetworkErrorMessage()
            }
        }
    }
}
