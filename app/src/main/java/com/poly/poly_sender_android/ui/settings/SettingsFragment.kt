package com.poly.poly_sender_android.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentSettingsBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.settings.mvi.SettingsNews
import com.poly.poly_sender_android.ui.settings.mvi.SettingsState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(), MviView<SettingsState, SettingsNews> {

    @Inject
    lateinit var logger: Logger

    private val userDetailsViewModel: SettingsViewModel by viewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(userDetailsViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@SettingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: SettingsState) {

    }

    override fun renderNews(new: SettingsNews) {
        when (new) {
            is SettingsNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}