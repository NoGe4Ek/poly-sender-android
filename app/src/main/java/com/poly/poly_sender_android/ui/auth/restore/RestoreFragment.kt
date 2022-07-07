package com.poly.poly_sender_android.ui.auth.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.poly_sender_android.databinding.FragmentRestoreBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.auth.restore.mvi.RestoreNews
import com.poly.poly_sender_android.ui.auth.restore.mvi.RestoreState
import com.poly.poly_sender_android.ui.auth.restore.mvi.RestoreWish
import com.poly.poly_sender_android.util.MessageConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestoreFragment : Fragment(), MviView<RestoreState, RestoreNews> {

    @Inject
    lateinit var logger: Logger

    private val restoreViewModel: RestoreViewModel by viewModels()

    private var _binding: FragmentRestoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRestoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(restoreViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@RestoreFragment)
        }

        binding.buttonRestore.setOnClickListener {
            if (binding.textFieldEmail.editText == null) {
                binding.textFieldEmail.error = MessageConstants.ERROR_EMPTY_FILL
            } else {
                restoreViewModel.obtainWish(RestoreWish.Restore(binding.textFieldEmail.editText!!.string()))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: RestoreState) {
        if (state.isLoading) {
            //TODO loading
        }
    }

    override fun renderNews(new: RestoreNews) {
        when (new) {
            is RestoreNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}