package com.poly.poly_sender_android.ui.attributes.creationAttribute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeParamBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeParamFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeParamBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationAttributeParamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(creationAttributeSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationAttributeParamFragment)
        }

        binding.buttonNext.setOnClickListener {
            when {
                binding.textFieldAttributeName.editText == null -> binding.textFieldAttributeName.error =
                    "This field can't be empty" //TODO export to resource and make constants error
                binding.menuSection.editText == null -> binding.menuSection.error =
                    "This field can't be empty" //TODO export to resource and make constants error
                else -> {
                    creationAttributeSharedViewModel.obtainWish(
                        CreationAttributeWish.UpdateSharedStorageByParam(
                            binding.textFieldAttributeName.editText!!.string(),
                            binding.menuSection.editText!!.string()
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationAttributeState) {
        binding.textFieldAttributeName.editText?.setText(state.selectedName)
        binding.menuSection.editText?.setText(state.selectedSection)
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

