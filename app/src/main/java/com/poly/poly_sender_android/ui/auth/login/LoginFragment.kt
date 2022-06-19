package com.poly.poly_sender_android.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.string
import com.poly.testwaveaccess.databinding.FragmentUserDetailsBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.UserListAdapter
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginNews
import com.poly.poly_sender_android.ui.auth.login.mvi.LoginState
import com.poly.poly_sender_android.ui.auth.mvi.UserDetailsWish
import com.poly.poly_sender_android.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), MviView<LoginState, LoginNews> {

    @Inject
    lateinit var logger: Logger

    private val userDetailsViewModel: LoginViewModel by viewModels()

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var friendListRecycler: RecyclerView
    lateinit var friendListAdapter: UserListAdapter

    private val args: UserDetailsFragmentArgs by navArgs()
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        friendListRecycler = binding.friendList
        friendListAdapter = UserListAdapter { user ->
            if (user.isActive == "true") {
                val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToUserDetailsFragment(
                    userId = user.id
                )
                view.findNavController().navigate(action)
            } else {
                Snackbar.make((binding.snack as View), "User is inactive at this moment", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        friendListRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        friendListRecycler.adapter = friendListAdapter

        with(userDetailsViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@LoginFragment)
        }

        userId = args.userId
        userDetailsViewModel.obtainWish(UserDetailsWish.GetUserDetails(userId))

        binding.textviewEmail.setOnClickListener {
            userDetailsViewModel.obtainWish(UserDetailsWish.ExternalEmail(binding.textviewEmail.string()))
        }
        binding.textviewPhone.setOnClickListener {
            userDetailsViewModel.obtainWish(UserDetailsWish.ExternalCall(binding.textviewPhone.string()))
        }
        binding.textviewLatitudeLongitude.setOnClickListener {
            val laLo = binding.textviewLatitudeLongitude.string().split(",")
            userDetailsViewModel.obtainWish(UserDetailsWish.ExternalMap(laLo[0], laLo[1]))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: LoginState) {
        state.user?.let { user ->
            val avatar = "ic_account_avatar_profile_user_"
            val context = binding.imageviewProfile.context
            val id: Int = context.resources.getIdentifier(
                avatar + (1..10).random().toString(), "drawable", context.packageName
            )
            binding.imageviewProfile.setImageResource(id)
            binding.textviewName.text = user.name
            binding.textviewAge.text = user.age
            binding.textviewCompany.text = user.company
            binding.textviewEmail.text = user.email
            binding.textviewPhone.text = user.phone
            binding.textviewAddress.text = user.address
            binding.textviewAbout.text = user.about
            binding.imageviewEyeColor.setImageResource(user.eyeColor.id)
            binding.imageviewFavoriteFruit.setImageResource(user.favoriteFruit.id)
            binding.textviewRegistered.text = Util.formatDate(user.registered)
            binding.textviewLatitudeLongitude.text = "${user.latitude}, ${user.longitude}"

            if (state.friends.isNotEmpty()) {
                friendListAdapter.submitList(state.friends)
            }
        }
    }

    override fun renderNews(new: LoginNews) {
        when (new) {
            is LoginNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}