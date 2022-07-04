package com.poly.poly_sender_android.ui.filterProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.databinding.FragmentAttributeProfileBinding
import com.poly.poly_sender_android.databinding.FragmentFilterProfileBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.attributeProfile.AttributeProfileViewModel
import com.poly.poly_sender_android.ui.attributeProfile.mvi.AttributeProfileNews
import com.poly.poly_sender_android.ui.attributeProfile.mvi.AttributeProfileState
import com.poly.poly_sender_android.ui.attributeProfile.mvi.AttributeProfileWish
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeParamFragmentDirections
import com.poly.poly_sender_android.ui.filterProfile.mvi.FilterProfileNews
import com.poly.poly_sender_android.ui.filterProfile.mvi.FilterProfileState
import com.poly.poly_sender_android.ui.filterProfile.mvi.FilterProfileWish
import com.poly.poly_sender_android.ui.filters.creationFilter.CreationFilterParamFragmentDirections
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class FilterProfileFragment : Fragment(), MviView<FilterProfileState, FilterProfileNews> {

    @Inject
    lateinit var logger: Logger

    private val filterProfileViewModel: FilterProfileViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentFilterProfileBinding? = null
    private val binding get() = _binding!!

    private val args: FilterProfileFragmentArgs by navArgs()
    private lateinit var filter: Filter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.AttributeBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(filterProfileViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@FilterProfileFragment)
        }

        //binding.cardViewAttributeProfile.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewFilterProfileDate.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewFilterProfileEmail.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewFilterProfileMode.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewFilterProfileStudents.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewFilterProfileType.setBackgroundResource(R.drawable.ic_card_profile_background)

        filter = args.filter
        filterProfileViewModel.obtainWish(FilterProfileWish.SetFilter(filter))

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.editEvent) {
                    val creationFilterParamFragment =
                        CreationFilterParamFragmentDirections.actionGlobalCreationFilterParamFragment(
                            filter = filter,
                            start = true
                        )
                    findNavController().navigate(creationFilterParamFragment)
                    mainActivityViewModel.triggerEdit(false)
                }
                if (state.shareEvent) {
                    mainActivityViewModel.triggerShare(false)
                }
                if (state.deleteEvent) {
                    mainActivityViewModel.triggerDelete(false)
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Удаление")
                        .setMessage("Вы уверены, что хотите удалить фильтр?")
                        .setNeutralButton("Отмена") { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setNegativeButton("Нет") { dialog, which ->
                            // Respond to negative button press
                        }
                        .setPositiveButton("Да") { dialog, which ->
                            filterProfileViewModel.obtainWish(FilterProfileWish.DeleteFilter(filter))
                        }
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: FilterProfileState) {
        if (state.isLoading) {
            //TODO
        }

        if (state.filter != null) {
            state.filter.apply {
                binding.textViewFilterProfileName.text = filterName
                binding.textViewFilterProfileEmail.text = mail
                binding.textViewFilterProfileStudents.text = students.size.toString()
                binding.textViewFilterProfileType.text = type
                binding.textViewFilterProfileDate.text = created
                binding.textViewFilterProfileMode.text = mode
            }
        }
    }

    override fun renderNews(new: FilterProfileNews) {
        when (new) {
            is FilterProfileNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}