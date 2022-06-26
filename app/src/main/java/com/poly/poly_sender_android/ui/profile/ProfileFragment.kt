package com.poly.poly_sender_android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentProfileBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.profile.mvi.ProfileNews
import com.poly.poly_sender_android.ui.profile.mvi.ProfileState
import com.poly.poly_sender_android.ui.profile.mvi.ProfileWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(), MviView<ProfileState, ProfileNews> {

    @Inject
    lateinit var logger: Logger

    private val userDetailsViewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        with(userDetailsViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@ProfileFragment)
        }

        binding.cardViewProfile.setBackgroundResource(R.drawable.ic_card_profile_background)

        userDetailsViewModel.obtainWish(ProfileWish.FetchUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: ProfileState) {
        if (state.isLoading) {

        }

        if (state.user != null) {
            binding.textViewSquareAvatarText.text =
                state.user.fullName.split(" ").joinToString(separator = "") { w -> w[0].toString() }.dropLast(1)
            binding.textViewProfileName.text = state.user.fullName
            binding.textViewProfileEmail.text = state.user.email
            binding.textViewProfileRole.text = state.user.roles.joinToString(separator = ", ")
        }
    }

    override fun renderNews(new: ProfileNews) {
        when (new) {
            is ProfileNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}