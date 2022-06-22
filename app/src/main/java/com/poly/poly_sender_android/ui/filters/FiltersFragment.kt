package com.poly.poly_sender_android.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentFiltersBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.FiltersAdapter
import com.poly.poly_sender_android.ui.filters.mvi.FiltersNews
import com.poly.poly_sender_android.ui.filters.mvi.FiltersState
import com.poly.poly_sender_android.ui.filters.mvi.FiltersWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FiltersFragment : Fragment(),
    MviView<FiltersState, FiltersNews> { // TODO animate floating buttons

    @Inject
    lateinit var logger: Logger

    private val filtersViewModel: FiltersViewModel by viewModels()

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    lateinit var filtersRecycler: RecyclerView
    lateinit var filtersAdapter: FiltersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        filtersRecycler = binding.filterList
        filtersAdapter = FiltersAdapter(onItemClicked = {},
            onEditClicked = {},
            onDeleteClicked = {},
            onShareClicked = {}) //TODO
        filtersRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        filtersRecycler.adapter = filtersAdapter

        with(filtersViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@FiltersFragment)
        }

        filtersViewModel.obtainWish(FiltersWish.Refresh(filtersSearchParam = FiltersSearchParam())) //TODO empty param

        binding.buttonFilter.setOnClickListener {
            //TODO
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return false
            }

            fun callSearch(query: String?) {
                filtersViewModel.obtainWish(FiltersWish.Refresh(filtersSearchParam = FiltersSearchParam())) //TODO
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: FiltersState) {
        if (state.isLoading) {
            //TODO
        }

        if (state.filters.isNotEmpty()) {
            filtersAdapter.submitList(state.filters)
        }
    }

    override fun renderNews(new: FiltersNews) {
        when (new) {
            is FiltersNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}