package com.poly.poly_sender_android.ui.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.databinding.FragmentStudentsAttributingBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.AttributesAdapter
import com.poly.poly_sender_android.ui.students.mvi.StudentsNews
import com.poly.poly_sender_android.ui.students.mvi.StudentsState
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StudentsAttributingFragment : Fragment(),
    MviView<StudentsState, StudentsNews> {

    @Inject
    lateinit var logger: Logger

    private val studentsSharedViewModel: StudentsSharedViewModel by activityViewModels()

    private var _binding: FragmentStudentsAttributingBinding? = null
    private val binding get() = _binding!!

    lateinit var attributesRecycler: RecyclerView
    lateinit var attributesAdapter: AttributesAdapter
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentStudentsAttributingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        attributesRecycler = binding.attributeList
        attributesAdapter = AttributesAdapter(
            onItemClicked = { attribute, card ->
                if (studentsSharedViewModel.nmState.searchSelectedAttributes.contains(attribute)) {
                    attributesAdapter.setSelectedAttributes(studentsSharedViewModel.nmState.searchSelectedAttributes - attribute)
                    studentsSharedViewModel.obtainWish(StudentsWish.DismissAttribute(attribute))
                } else {
                    attributesAdapter.setSelectedAttributes(studentsSharedViewModel.nmState.searchSelectedAttributes + attribute)
                    studentsSharedViewModel.obtainWish(StudentsWish.SelectAttribute(attribute))
                }
                card.isChecked = !card.isChecked
            },
            onItemLongClicked = { attribute ->
                //TODO navigate to attribute profile or add swipable card
            }) //TODO add attribute to selected list in state & set attribute.isChecked to true OR VISE VERSA
        attributesAdapter.setSelectedAttributes(studentsSharedViewModel.nmState.searchSelectedAttributes)
        attributesRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        attributesRecycler.adapter = attributesAdapter

        with(studentsSharedViewModel) {
            bind(
                viewLifecycleOwner.lifecycleScope,
                this@StudentsAttributingFragment
            )
        }

        studentsSharedViewModel.obtainWish(StudentsWish.RefreshSections)

        studentsSharedViewModel.obtainWish(
            StudentsWish.RefreshSearchingAttributesBySelectedSection(
                studentsSharedViewModel.nmState.searchSelectedSection
            )
        )
        binding.menuSection.editText?.setText(studentsSharedViewModel.nmState.searchSelectedSection?.sectionName ?: "Выберите раздел")

        binding.buttonApply.setOnClickListener {
            val studentsFragment =
                StudentsAttributingFragmentDirections.actionStudentsAttributingFragmentToStudentsFragment()
            findNavController().navigate(studentsFragment)
        }
        binding.buttonClear.setOnClickListener {
            studentsSharedViewModel.obtainWish(StudentsWish.ClearSearchParam)
        }
        binding.menuSectionAuto.setOnItemClickListener { parent, view, position, id ->
            if (adapter.getItem(position) == "Выберите раздел") {
                studentsSharedViewModel.obtainWish(StudentsWish.RefreshSearchingAttributesBySelectedSection(null))
                studentsSharedViewModel.obtainWish(StudentsWish.RefreshSelectedSection(null))
            } else {
                val selectedSection = studentsSharedViewModel.nmState.searchSections.find{ section -> section.sectionName ==  adapter.getItem(position)}
                studentsSharedViewModel.obtainWish(StudentsWish.RefreshSearchingAttributesBySelectedSection(selectedSection))
                studentsSharedViewModel.obtainWish(StudentsWish.RefreshSelectedSection(selectedSection))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: StudentsState) {
        adapter = ArrayAdapter(requireContext(), R.layout.list_item)
        adapter.add("Выберите раздел")
        adapter.addAll(state.searchSections.map { it.sectionName })
        (binding.menuSection.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        attributesAdapter.submitList(state.searchAttributes.toList())
    }

    override fun renderNews(new: StudentsNews) {
        when (new) {
            is StudentsNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

