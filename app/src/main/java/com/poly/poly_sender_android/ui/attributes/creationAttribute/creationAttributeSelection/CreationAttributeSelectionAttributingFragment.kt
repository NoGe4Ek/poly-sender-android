package com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeSelectionAttributingBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeSelectionAttributingFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeSelectionAttributingBinding? = null
    private val binding get() = _binding!!

    lateinit var attributesRecycler: RecyclerView
    lateinit var attributesAdapter: AttributesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentCreationAttributeSelectionAttributingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        attributesRecycler = binding.attributeList
        attributesAdapter = AttributesAdapter(onItemClicked = {attribute, card ->  }, onItemLongClicked = {}) //TODO add attribute to selected list in state & set attribute.isChecked to true OR VISE VERSA
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        with(creationAttributeSharedViewModel) {
            bind(
                viewLifecycleOwner.lifecycleScope,
                this@CreationAttributeSelectionAttributingFragment
            )
        }

        binding.buttonApply.setOnClickListener {

            creationAttributeSharedViewModel.apply {
                obtainWish(
                    CreationAttributeWish.UpdateSharedStorageBySelectionAttributing(
                        nmState.searchAttributes, nmState.searchSelectedAttributes, nmState.searchSelectedSection
                    )
                )
            }
            //TODO emit refresh students with searchParam on main screen
            //TODO navigate to Attributes
        }
        binding.buttonClear.setOnClickListener {
            creationAttributeSharedViewModel.obtainWish(CreationAttributeWish.ClearSearchParam)
            //TODO navigate to Attributes
        }
        //TODO add onChangeSection listener with UpdateSharedStorageBySelectionAttributing
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationAttributeState) {
        binding.menuSection.editText?.setText(state.searchSelectedSection)
        attributesAdapter.submitList(state.searchAttributes)
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

