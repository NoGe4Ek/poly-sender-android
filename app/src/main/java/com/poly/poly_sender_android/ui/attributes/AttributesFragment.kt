package com.poly.poly_sender_android.ui.attributes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentAttributesBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesNews
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesState
import com.poly.poly_sender_android.ui.attributes.mvi.AttributesWish
import com.poly.poly_sender_android.util.AppAnimations
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AttributesFragment : Fragment(),
    MviView<AttributesState, AttributesNews> { //TODO animation floating buttons

    @Inject
    lateinit var logger: Logger

    private val attributesViewModel: AttributesViewModel by viewModels()

    private var _binding: FragmentAttributesBinding? = null
    private val binding get() = _binding!!

    lateinit var attributesRecycler: RecyclerView
    lateinit var attributesAdapter: AttributesAdapter

    private var clicked = false
    private lateinit var appAnimations: AppAnimations


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

        appAnimations = AppAnimations(requireContext())
        attributesRecycler = binding.attributeList
        attributesAdapter = AttributesAdapter(onItemClicked = {},
            onEditClicked = {},
            onDeleteClicked = {},
            onShareClicked = {}) //TODO
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        with(attributesViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@AttributesFragment)
        }

        attributesViewModel.obtainWish(AttributesWish.Refresh(attributesSearchParam = AttributesSearchParam())) //TODO empty param

        binding.buttonFilter.setOnClickListener {
            //TODO
        }

        binding.floatingButtonAdd.setOnClickListener {
            clicked = appAnimations.onExpandedFloatingButtonClicked(clicked, binding.floatingButtonAdd, binding.floatingButtonAddSection, binding.floatingButtonAddAttribute)
        }

        binding.floatingButtonAddSection.setOnClickListener {

        }

        binding.floatingButtonAddAttribute.setOnClickListener {

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
                attributesViewModel.obtainWish(AttributesWish.Refresh(attributesSearchParam = AttributesSearchParam())) //TODO
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