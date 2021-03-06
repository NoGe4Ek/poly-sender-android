package com.poly.poly_sender_android.ui.attributeProfile

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
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.NavGraphDirections
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.databinding.FragmentAttributeProfileBinding
import com.poly.poly_sender_android.databinding.FragmentStudentProfileBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.StudentAttributesAdapter
import com.poly.poly_sender_android.ui.attributeProfile.mvi.AttributeProfileNews
import com.poly.poly_sender_android.ui.attributeProfile.mvi.AttributeProfileState
import com.poly.poly_sender_android.ui.attributeProfile.mvi.AttributeProfileWish
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeFragmentDirections
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeParamFragmentDirections
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileViewModel
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileNews
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileState
import com.poly.poly_sender_android.ui.studentProfile.mvi.StudentProfileWish
import com.poly.poly_sender_android.ui.students.StudentsFragmentDirections
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class AttributeProfileFragment : Fragment(), MviView<AttributeProfileState, AttributeProfileNews> {

    @Inject
    lateinit var logger: Logger

    private val attributeProfileViewModel: AttributeProfileViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentAttributeProfileBinding? = null
    private val binding get() = _binding!!

    private val args: AttributeProfileFragmentArgs by navArgs()
    private lateinit var attribute: Attribute

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAttributeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        App.appBar = AppBar.AttributeBar
        App.mCurrentActivity.invalidateOptionsMenu()

        with(attributeProfileViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@AttributeProfileFragment)
        }

        //binding.cardViewAttributeProfile.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewAttributeProfileStudents.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewAttributeProfileDate.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewAttributeProfileSection.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewAttributeProfileType.setBackgroundResource(R.drawable.ic_card_profile_background)
        binding.cardViewAttributeProfileExpression.setBackgroundResource(R.drawable.ic_card_profile_background)

        attribute = args.attribute
        attributeProfileViewModel.obtainWish(AttributeProfileWish.SetAttribute(attribute))

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.editEvent) {
                    val creationAttributeParamFragment =
                        CreationAttributeFragmentDirections.actionGlobalCreationAttributeParamFragment(
                            attribute = attribute,
                            start = true
                        )
                    findNavController().navigate(R.id.action_global_creationAttributeParamFragment)
                    mainActivityViewModel.triggerEdit(false)
                }
                if (state.shareEvent) {
                    mainActivityViewModel.triggerShare(false)
                }
                if (state.deleteEvent) {
                    mainActivityViewModel.triggerDelete(false)
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("????????????????")
                        .setMessage("???? ??????????????, ?????? ???????????? ?????????????? ???????????????")
                        .setNeutralButton("????????????") { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setNegativeButton("??????") { dialog, which ->
                            // Respond to negative button press
                        }
                        .setPositiveButton("????") { dialog, which ->
                            attributeProfileViewModel.obtainWish(AttributeProfileWish.DeleteAttribute(attribute))
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

    override fun renderState(state: AttributeProfileState) {
        if (state.isLoading) {
            //TODO loading
        }

        if (state.attribute != null) {
            state.attribute.apply {
                binding.textViewAttributeProfileName.text = attributeName
                binding.textViewAttributeProfileSection.text = groupName
                binding.textViewAttributeProfileStudents.text = students.size.toString()
                binding.textViewAttributeProfileType.text = type
                binding.textViewAttributeProfileDate.text = created
                if (type == "expression") {
                    binding.cardViewAttributeProfileExpression.visibility = View.VISIBLE
                    binding.textViewAttributeProfileExpression.text = expression
                }
            }
        }
    }

    override fun renderNews(new: AttributeProfileNews) {
        when (new) {
            is AttributeProfileNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}