package com.poly.poly_sender_android.ui.attributes

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
import com.poly.poly_sender_android.databinding.FragmentAttributesBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesNews
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesState
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AttributesFragment : Fragment(), MviView<AttributesState, AttributesNews> {

    @Inject
    lateinit var logger: Logger

    private val userListViewModel: AttributesViewModel by viewModels()

    private var _binding: FragmentAttributesBinding? = null
    private val binding get() = _binding!!

    lateinit var attributesRecycler: RecyclerView
    lateinit var attributesAdapter: AttributesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAttributesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        attributesRecycler = binding.attributeList
        attributesAdapter = AttributesAdapter(onEditClicked = {}, onDeleteClicked = {}, onShareClicked = {}) //TODO
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        with(userListViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@AttributesFragment)
        }

        userListViewModel.obtainWish(AttributesWish.Refresh(searchParam = SearchParam())) //TODO empty param

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
                userListViewModel.obtainWish(AttributesWish.Refresh(searchParam = SearchParam())) //TODO
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: AttributesState) {
        if (state.isLoading) {
            //TODO
        }

        if (state.attributes.isNotEmpty()) {
            attributesAdapter.submitList(state.attributes)
        }
    }

    override fun renderNews(new: AttributesNews) {
        when (new) {
            is AttributesNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}